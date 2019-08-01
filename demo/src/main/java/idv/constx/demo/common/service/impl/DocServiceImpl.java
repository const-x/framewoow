package idv.constx.demo.common.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import idv.constx.demo.base.dao.BaseDao;
import idv.constx.demo.base.entity.IDEntity;
import idv.constx.demo.base.service.BaseServiceImpl;
import idv.constx.demo.common.DateUtils;
import idv.constx.demo.common.dao.DocDao;
import idv.constx.demo.common.dao.DocRelDao;
import idv.constx.demo.common.entity.Doc;
import idv.constx.demo.common.entity.DocRel;
import idv.constx.demo.common.exception.ExceptionUtils;
import idv.constx.demo.common.service.DocService;

/**
 * 简单的本地保存文档管理服务
 * @author const.x
 *
 */
@Service
@Transactional
public class DocServiceImpl extends BaseServiceImpl implements DocService {

	@Autowired
	private DocDao docDao;
	
	@Value("${contextpath}")
	private String contextPath;
	
	@Autowired
	private DocRelDao docRelDao;
	
	public static final String CACHE_KEY_PROFIX = "CACHE"; 
	
	@Override
	public Doc cache(String module, MultipartFile file) {
		return this.cache( module, file, 2);
	}

	@Override
	public Doc cache( String module, MultipartFile file,
			int duration) {
		try {
			String fileName = file.getName();
			String dir = "upload$" + module+ "$";
			InputStream in = file.getInputStream();
			return this.cache(in, fileName, dir, duration);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			ExceptionUtils.wapperException(e);
		}finally{
			try {
				file.getInputStream().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public Doc cache(String name, Long size, InputStream input) {
		return this.cache(name, size, input, 2);
	}

	@Override
	public Doc cache(String name, Long size, InputStream input, int duration) {
		try {
			String dir = "upload$unknow$";
			return this.cache(input, name, dir, duration);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			ExceptionUtils.wapperException(e);
		}finally{
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	private Doc cache(InputStream in,String fileName,String dir,int duration) throws Exception{
		String stoname =  System.currentTimeMillis()+"";
		String path = this.getClass().getResource("/").getFile();
		path = path.substring(0,path.indexOf("WEB-INF"));
		path = path + File.separator + dir.replace('$', File.separatorChar) + stoname;
		String suffix = fileName.substring(fileName.indexOf(".") + 1);
		
		File f = new File(path);
		if (!f.exists()) {
			f.getParentFile().mkdirs();
			f.createNewFile();
		}
		FileOutputStream out = new FileOutputStream(f);
		int size = 10240;
		byte[] b = new byte[size];
		while (in.read(b, 0, size) != -1) {
			out.write(b);
		}
		out.flush();
		out.close();
		
		String md5 = this.getFileMD5(new FileInputStream(f));
		//　如果文件目錄下已經有相应的文件 则返回已有文件的引用
		Doc doc = docDao.findByFileMD5(md5);
		if(doc!=null){
			doc.setCacheKey(CACHE_KEY_PROFIX + md5);
			f.delete();
		}else{
			doc = new Doc();
			doc.setName(fileName);
			doc.setMD5(md5);
			doc.setCacheKey(CACHE_KEY_PROFIX + md5);
			doc.setExpiration(DateUtils.getDateTimeAfterHours(duration,DateUtils.getNowDateTime()));
			doc.setPath(dir + stoname);
			doc.setSuffix(suffix.toUpperCase());
			super.beforeSave(doc);
			docDao.save(doc);
		}
	    return doc;
	}
	
	@Override
	public Doc findByCache(String key) {
		try {
			return docDao.findByCacheKey(key);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			ExceptionUtils.wapperException(e); 
			return null;
		}
		
	}

	@Override
	public void clearCache(String key) {
		try {
			Doc doc = docDao.findByCacheKey(key);
			if(doc!=null){
				docDao.deleteByCacheKey(doc.getCacheKey());
				File f = new File(this.contextPath + doc.getPath());
				if(f.exists()){
					f.delete();
				}
			}
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			ExceptionUtils.wapperException(e);
		}
	}

	@Override
	public void clearOverDueCaches() {
		try {
			List<Doc> docs = docDao.findByNotRelAndOutTime();
			String[] fileMD5 = new String[docs.size()];
			File[] files = new File[docs.size()];
			for(int i=0;i<docs.size();i++){
				files[i] = new File(this.contextPath + docs.get(i).getPath());
				fileMD5[i] = docs.get(i).getMD5();
			}
			docDao.deleteByCacheKey(fileMD5);
			for(File f : files)
			{
				if(f.exists()){
					f.delete();
				}
			}
			
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			ExceptionUtils.wapperException(e);
		}
	}

	@Override
	public String saveRel(String key) {
		try {
			DocRel rel = new DocRel();
			rel.setCacheKey(key);
			docRelDao.save(rel);
			return rel.getId().toString();
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			ExceptionUtils.wapperException(e);
			return null;
		}
		
	}

	@Override
	public void delRel(String relKey) {
		try {
			docRelDao.delete(Long.valueOf(relKey));
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			ExceptionUtils.wapperException(e);
		}

	}

	@Override
	public Doc findByRel(String relKey) {
		try {
			return docDao.findByRelKey(relKey);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			ExceptionUtils.wapperException(e);
			return null;
		}
		
	}

	
	
	@Override
	public String getUrlByCache(String key) {
		try {
			Doc doc = this.findByCache(key);
			return this.getUrlByDoc( doc);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			ExceptionUtils.wapperException(e);
		}
		return null;
	}

	@Override
	public String getUrlByRel(String relKey) {
		try {
			Doc doc = this.findByRel(relKey);
			return this.getUrlByDoc(doc);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			ExceptionUtils.wapperException(e);
		}
		return null;
	}

	@Override
	public String getUrlByDoc(Doc doc) {
		try {
			String path = doc.getPath().replace('$', '/');
			return contextPath+path;
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			ExceptionUtils.wapperException(e);
		}
		return null;
	}

	@Override
	public void download(OutputStream out, String relKey) {
		FileInputStream in = null;
		try {
			String path = this.getClass().getResource("/").getFile();
			Doc doc = this.findByRel(relKey);
			String dir = doc.getPath().replace('$', File.separatorChar);
			File file = new File(path + dir);
			if (!file.exists()) {
				ExceptionUtils.throwBizException("文件不存在："+ path);
			}
			int size = 10240;
			byte[] b = new byte[size];
			in = new FileInputStream(file);
			while (in.read(b, 0, size) != -1) {
				out.write(b);
			}
			out.flush();
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			ExceptionUtils.wapperException(e);
		}finally{
			if(in != null){
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
	private String getFileMD5(InputStream in) throws Exception {
		try{
			MessageDigest digest = null;
			byte buffer[] = new byte[1024];
			int len;
			digest = MessageDigest.getInstance("MD5");
			while ((len = in.read(buffer, 0, 1024)) != -1) {
				digest.update(buffer, 0, len);
			}
			
			BigInteger bigInt = new BigInteger(1, digest.digest());
			return bigInt.toString(16);
		}finally{
			in.close();
		}
	}

	@Override
	protected BaseDao getDaoByEntryType(Class<? extends IDEntity> clazz) {
		return null;
	}


}
