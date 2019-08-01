/**
 * @author const.x
 * @createDate 2014年9月1日
 */
package idv.constx.demo.common.controller;

import idv.constx.demo.common.entity.Doc;
import idv.constx.demo.common.service.DocService;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;



/**
 * 在线编辑器图片上传
 * 
 * @author const.x
 */
@RestController
@RequestMapping("common/doc")
public class UploadController {
	
	public static final String FILEEXT = "jpg,jpeg,gif,png";// 上传图片格式
	
	@Autowired
	protected DocService docService;

	@RequestMapping(value = "/uploadimg", method = RequestMethod.POST)
	public String imgUpload(HttpServletRequest request,String param) throws IOException {
		Map<String, String> map = new HashMap<String, String>();
		String[] params = param.split(",");
		for(String str : params){
			String[] p = str.trim().split(":");
			map.put(p[0], p[1]);
		}
		Integer width = getInt(map.get("width"));
		Integer height = getInt(map.get("height"));
		Integer maxWidth = getInt(map.get("maxWidth"));
		Integer maxHeight = getInt(map.get("maxHeight"));
		Integer size = getInt(map.get("size"));
		Integer maxSize = getInt(map.get("maxSize"));
		String module =map.get("module");
		
		String message = "";
		DiskFileItemFactory fac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fac);
		upload.setHeaderEncoding("utf-8");
		List<FileItem> fileList = null;
		try {
			  if ("application/octet-stream".equals(request.getContentType())) { 
				    String dispoString = request.getHeader("Content-Disposition");
	                int iFindStart = dispoString.indexOf("name=\"")+6;
	                int iFindEnd = dispoString.indexOf("\"", iFindStart);
	                iFindStart = dispoString.indexOf("filename=\"")+10;
	                iFindEnd = dispoString.indexOf("\"", iFindStart);
	                String name = dispoString.substring(iFindStart, iFindEnd);
	                Long i = (long) request.getContentLength();
	                Doc doc = docService.cache(name, i,  request.getInputStream());
	                String url = docService.getUrlByDoc(doc);
					message = this.createMsg("", url, doc.getCacheKey(), doc.getName());
			  }else{
				  fileList = upload.parseRequest(request);
					if (fileList.size() > 0) {
						MultipartFile file;
						for (FileItem item : fileList) {
							if (!item.isFormField()) {
								file = new MultipartFileAdapter(item);
								String hj = file.getName();
								hj = hj.substring(hj.lastIndexOf("."));
								if (FILEEXT.indexOf(hj.substring(1).toLowerCase()) == -1) {
									return this.createMsg("上传的图片类型不正确，可上传的图片类型为：" + FILEEXT , "", "", "");
								}
								String res = this.validateImg(file, width, height, maxWidth, maxHeight, size,maxSize);
								if(res != null){
									return res;
								}
								Doc doc = docService.cache(module, file);
								String url = docService.getUrlByDoc(doc);
								message = this.createMsg("", url, doc.getCacheKey(), doc.getName());
							}
						}
					} else {
						message = createMsg("请选择上传的图片" , "", "", "");
					}
			  }
		} catch (Exception e) {
			message = createMsg("图片上传失败:"+e.getMessage() , "", "", "");
			e.printStackTrace();
		}
		return message;
	}
	
	private String createMsg(String err,String url,String key,String name){
		JSONObject json = new JSONObject();
		try {
			json.put("err", err);
			//适配xheditor
			json.put("msg", url);
			json.put("url", url);
			json.put("key", key);
			json.put("name", name);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json.toString();
	}
	
	private Integer getInt(String s){
		if(StringUtils.isBlank(s)){
			return null;
		}else{
			return Integer.valueOf(s);
		}
	}
	
	public String validateImg(MultipartFile file, Integer width, Integer height, Integer maxWidth, Integer maxHeight,Integer size,Integer maxSize) {
		int w = 0, h = 0;
		Long s = file.getSize()/1024;
		if (size != null && s > size) {
			return createMsg("图片大小超出限制,可上传文件的最大限制为" + size + "KB" , "", "", "");
		}
		if (maxSize != null && s > maxSize) {
			return createMsg("图片大小超出限制,可上传文件的最大限制为" + maxSize  + "KB" , "", "", "");
		}
		if(width != null || height != null || maxWidth != null || maxHeight != null){
			BufferedImage sourceImg;
			try {
				sourceImg = ImageIO.read(file.getInputStream());
				w = sourceImg.getWidth();
				h = sourceImg.getHeight();
				if(width != null && width != w){
					return createMsg("上传的图片不符合指定的宽度：" + width  , "", "", "");
				}
				if(height != null && height != h){
					return createMsg("上传的图片不符合指定的高度：" + height  , "", "", "");
				}
				if(maxWidth != null && maxWidth < w){
					return createMsg("上传的图片超出最大宽度：" + maxWidth  , "", "", "");
				}
				if(maxHeight != null && maxHeight < h){
					return createMsg("上传的图片超出最大高度：" + maxHeight  , "", "", "");
				}
			} catch (IOException e) {
				return createMsg("系统错误：" + e.getMessage()  , "", "", "");
			}
		}
		return null;
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String upload(HttpServletRequest request,String param) throws IOException {
		Map<String, String> map = new HashMap<String, String>();
		String[] params = param.split(",");
		for(String str : params){
			String[] p = str.trim().split(":");
			map.put(p[0], p[1]);
		}
		Integer maxSize = getInt(map.get("maxSize"));
		String module =map.get("module");
		
		String message = "";
		DiskFileItemFactory fac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fac);
		upload.setHeaderEncoding("utf-8");
		List<FileItem> fileList = null;
		try {
			fileList = upload.parseRequest(request);
			if (fileList.size() > 0) {
				MultipartFile file;
				for (FileItem item : fileList) {
					if (!item.isFormField()) {
						file = new MultipartFileAdapter(item);
						String hj = file.getName();
						hj = hj.substring(hj.lastIndexOf("."));
						String res = this.validateSize(file,maxSize);
						if(res != null){
							return res;
						}
						Doc doc = docService.cache(module, file);
						String url = docService.getUrlByDoc(doc);
						message = this.createMsg("", url, doc.getCacheKey(), doc.getName());
					}
				}
			} else {
				message = createMsg("请选择上传的文件" , "", "", "");
			}
		} catch (Exception e) {
			message = createMsg("文件上传失败:"+e.getMessage() , "", "", "");
			e.printStackTrace();
		}
		return message;
	}
	
	@RequestMapping(value = "/download/{relKey}", method = RequestMethod.POST)
	public void download(HttpServletRequest request,HttpServletResponse response,@PathVariable String relKey) throws IOException {
		try{
		    docService.download(response.getOutputStream(), relKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public String validateSize(MultipartFile file,Integer maxSize) {
		Long s = file.getSize()/1024;
		if (maxSize != null && s > maxSize) {
			return createMsg("文件大小超出限制,可上传文件的最大限制为" + maxSize + "KB", "", "", "");
		}
		return null;
	}
}
