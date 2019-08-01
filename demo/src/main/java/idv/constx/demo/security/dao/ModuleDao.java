package idv.constx.demo.security.dao;

import java.util.List;

import idv.constx.demo.base.dao.BaseDao;

import org.apache.ibatis.annotations.Param;

import idv.constx.demo.security.entity.Module;
import idv.constx.demo.security.service.ModuleCondition;
import idv.constx.demo.util.dwz.Page;

/**
 * 业务模块数据库操作
 * @author const.x
 * @since  2016-09-27 17:50:04
 */

public interface ModuleDao  extends BaseDao {
	
	
	
	/**
	 * 根据id查询
	 * 
	 * @param id 实体ID
	 * @return List<Module>
	 *
	 */
	Module findById(@Param(value = "id") Long id);
	
	/**
	 * 根据id查询
	 * 
	 * @param ids 实体ID
	 * @return List<Module>
	 *
	 */
	List<Module> findByIds(@Param(value = "ids") Long ...ids);
	
	
	/**
	 * 根据指定条件查找
	 * 
	 * @param condition 查询条件 可为空 
	 * @param order     排序条件（不包含order by关键字） 可为空
     * @param page      分页信息  可为空
	 * @return List<Module>
	 *
	 */
	List<Module> findByPage(@Param(value = "userId") Long userId, @Param(value = "condition") ModuleCondition condition,
	                                               @Param(value = "order") String order, @Param(value = "page") Page page);
	
	/**
	 * 根据指定条件查找指定字段值
	 * 
	 * @param fields    查询字段 为空时查所有
	 * @param condition 查询条件 可为空 
	 * @param order     排序条件（不包含order by关键字） 可为空
     * @param page      分页信息  可为空
	 * @return List<Module>
	 *
	 */
	List<Module> findFieldsByPage(@Param(value = "userId") Long userId,@Param(value = "fields") String[] fields, @Param(value = "condition") Module condition,
	                                               @Param(value = "order") String order, @Param(value = "page") Page page);
	                                               
    /**
	 * 根据指定条件查找指定字段值
	 * 
	 * @param fields    查询字段 为空时查所有
	 * @param where 查询条件 可为空 
	 * @param order     排序条件（不包含order by关键字） 可为空
     * @param page      分页信息  可为空
	 * @return List<Module>
	 *
	 */
	List<Module> findFieldsByWherePage(@Param(value = "userId") Long userId,@Param(value = "fields") String[] fields, @Param(value = "where")  String where,
	                                               @Param(value = "order") String order,@Param(value = "page") Page page);
	                                               
            
	/**
	 * 保存业务模块
	 * 
	 * @param modules 实体内容
	 *
	 */
	void save(Module module);
    
        
	/**
	 * 删除业务模块
	 * 
	 * @param ids 实体ID
	 *
	 */
	void delete(@Param(value = "ids") Long ...ids);
	
	/**
	 * 逻辑删除业务模块
	 * 
	 * @param ids 实体ID
	 *
	 */
	void deleteInLogic(@Param(value = "modifyer") Long modifyer,@Param(value = "modifytime") String modifytime,@Param(value = "ids") Long ...ids);
	
	/**
	 * 删除业务模块
	 * 
	 * @param where 查询条件（不包含where关键字）
	 *
	 */
	void deleteByWhere(@Param(value = "where")  String where);
	
	/**
	 * 逻辑删除业务模块
	 * 
	 * @param where 查询条件（不包含where关键字）
	 *
	 */
	void deleteByWhereInLogic(@Param(value = "modifyer") Long modifyer,@Param(value = "modifytime") String modifytime, @Param(value = "where")  String where);
    
     /**
     * 更新业务模块
     * 
     * @param modules 实体内容
     *
	 */
	void update(Module module);
	
	List<Module> findByParentId(Page page, Long... parentId);



	

}
