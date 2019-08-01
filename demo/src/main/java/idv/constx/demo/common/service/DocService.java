package idv.constx.demo.common.service;

import idv.constx.demo.common.entity.Doc;

import java.io.InputStream;
import java.io.OutputStream;




import org.springframework.web.multipart.MultipartFile;

/**
 * 文档管理服务
 * @author const.x
 *
 */
public interface DocService {

	/**
	 * 暂存文档 默认保存2小时
	 * @param contextPath 当前web目录 (仅当文档保存在本地时适用，其他情况可传null)
	 * @param module      所属模块
	 * @param file        文档
	 * @return  文档信息
	 */
	Doc cache(String module,MultipartFile file);
	
	/**
	 * 暂存文档 
	 * @param module      所属模块
	 * @param file        文档
	 * @param duration    暂存时间（小时）
	 * @return  文档信息
	 */
	Doc cache(String module,MultipartFile file,int duration);
	
	/**
	 * 暂存文档  默认保存2小时
	 * @param name   文件名
	 * @param size   大小
	 * @param input  流
	 * @return
	 */
	Doc cache(String name,Long size,InputStream input);
	
	/**
	 * 暂存文档 
	  * @param name   文件名
	 * @param size   大小
	 * @param input  流
	 * @param duration    暂存时间（小时）
	 * @return
	 */
	Doc cache(String name,Long size,InputStream input,int duration);
	
	/**
	 * 清除暂存
	 * @param key 暂存Key值
	 */
	void clearCache(String key);
	
	/**
	 * 根据暂存Key值获取文档信息
	 * @param key 暂存Key值
	 * @return
	 */
	Doc findByCache(String key);
	
	/**
	 * 清除所有已过期，并且不存在引用关系的暂存
	 */
	void clearOverDueCaches();
	
	
	/**
	 * 保存引用关系 
	 * @param key  暂存Key值
	 * @return 引用关系Key值
	 */
	String saveRel(String key);
	
	/**
	 * 删除存引用关系 
	 * @param relKey 引用关系Key值
	 * @see #saveRel(String)
	 * @return
	 */
	void delRel(String relKey);
	
	/**
	 * 根据引用关系获取文档信息
	 * @param relKey 引用关系Key值
	 * @see #saveRel(String)
	 * @return
	 */
	Doc findByRel(String relKey);
	
	/**
	 * 获取文档访问路径
	 * @param relKey      引用关系Key值
	 * @return
	 */
	String getUrlByCache(String key);
	
	/**
	 * 获取文档访问路径
	 * @param key  暂存Key值
	 * @return
	 */
	String getUrlByRel(String relKey);
	
	/**
	 * 获取文档访问路径
	 * @param doc      文档信息
	 * @return
	 */
	String getUrlByDoc(Doc doc);
	
	/**
	 * 根据引用关系下载文档
	 * 注意：无论成功与否，该方法都不会关闭out，需调用者自行关闭
	 * @param out    
	 * @param relKey 引用关系Key值
	 * 
	 */
	void download(final OutputStream out,String relKey);
	
	
	
	
}
