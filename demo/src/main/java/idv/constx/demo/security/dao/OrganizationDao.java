package idv.constx.demo.security.dao;

import java.util.List;




import org.apache.ibatis.annotations.Param;

import idv.constx.demo.security.entity.Organization;
import idv.constx.demo.util.dwz.Page;

/**
 * 
 * @author const.x Version 1.1.0
 * @since 2012-8-27 下午3:55:47
 */


public interface OrganizationDao {


	List<Organization> findByParentIdAndKeywordsPage(@Param(value="parentId") Long parentId,@Param(value="name") String name, Page page);


	List<Organization> findAllWithCache();


	void update(Organization organization);
	
	Organization get(@Param(value="id") Long id);
	
	List<Organization>  getByIds(@Param(value="ids") Long ... ids);

	Organization getByParent(@Param(value = "parentId")Long parentId);

	void save(Organization organization);

	void delete(@Param(value="id") Long id);

	List<Organization> findByParentIdPage(Page page,@Param(value="parentId") Long parentId);


	List<Organization> findByPage(@Param(value = "userId") Long userId, @Param(value = "condition") Organization condition,
            @Param(value = "order") String order, @Param(value = "page") Page page);

}
