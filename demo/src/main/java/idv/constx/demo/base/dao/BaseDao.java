package idv.constx.demo.base.dao;

import java.util.List;

import idv.constx.demo.base.entity.IDEntity;


public interface BaseDao {
   
	/**
	 * 查询最后修订时间，用于 删除，修改动作时验证版本
	 * 
	 * @param id
	 * @return
	 *
	 * @author const.x
	 * @createDate 2014年8月28日
	 */
	List<IDEntity> findLastModifyTime(List<Long> id);
	
}
