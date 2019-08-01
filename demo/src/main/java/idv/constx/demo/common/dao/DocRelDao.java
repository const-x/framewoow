package idv.constx.demo.common.dao;

import java.util.List;

import idv.constx.demo.base.dao.BaseDao;
import idv.constx.demo.common.entity.DocRel;

import org.apache.ibatis.annotations.Param;

/**
 * 档案引用数据库操作
 * @author const.x
 * @since  2017-11-14 09:18:49
 */

public interface DocRelDao  extends BaseDao {
	
	
	
	/**
	 * 根据id查询
	 * 
	 * @param id 实体ID
	 * @return List<DocRel>
	 *
	 */
	DocRel findById(@Param(value = "id") Long id);
	
	/**
	 * 根据id查询
	 * 
	 * @param ids 实体ID
	 * @return List<DocRel>
	 *
	 */
	List<DocRel> findByIds(@Param(value = "ids") Long ...ids);
	
    
	/**
	 * 保存档案引用
	 * 
	 * @param docRels 实体内容
	 *
	 */
	void save(DocRel docRel);
    
        
	/**
	 * 删除档案引用
	 * 
	 * @param ids 实体ID
	 *
	 */
	void delete(@Param(value = "ids") Long ...ids);
	

	



	

}
