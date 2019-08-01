package idv.constx.demo.common.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import idv.constx.demo.base.dao.BaseDao;
import idv.constx.demo.common.entity.Doc;



/**
 * 文件档案数据库操作
 * @author const.x
 * @since  2017-11-13 16:52:09
 */

public interface DocDao  extends BaseDao {
	
	
	
	                                         
            
	/**
	 * 保存文件档案
	 * 
	 * @param docs 实体内容
	 *
	 */
	void save(Doc doc);

	Doc findByCacheKey(@Param(value = "key") String key);
	
	Doc findByRelKey(@Param(value = "rel") String rel);

	List<Doc> findByNotRelAndOutTime();

	void deleteByCacheKey(@Param(value = "keys") String ... keys);

	Doc findByFileMD5(@Param(value = "md5") String md5);
    
   


	

}
