package idv.constx.demo.security.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import idv.constx.demo.base.dao.BaseDao;
import idv.constx.demo.base.entity.IDEntity;
import idv.constx.demo.base.service.BaseServiceImpl;
import idv.constx.demo.common.exception.ExceptionUtils;
import idv.constx.demo.common.exception.ServiceException;
import idv.constx.demo.security.dao.OrganizationDao;
import idv.constx.demo.security.entity.Organization;
import idv.constx.demo.security.entity.User;
import idv.constx.demo.security.entity.UserTypeEnum;
import idv.constx.demo.security.service.OrganizationService;
import idv.constx.demo.util.dwz.Page;

/**
 * 
 * @author const.x Version 1.1.0
 * @since 2012-8-27 下午3:56:46
 */
@Service
@Transactional
public class OrganizationServiceImpl extends BaseServiceImpl implements OrganizationService {
	@Autowired
	private OrganizationDao organizationDao;

	/**
	 * @param id
	 * @throws ServiceException
	 */
	@Transactional
	public void delete(Long id) throws ServiceException {
		if (isRoot(id)) {
			throw new ServiceException("不允许删除根组织。");
		}

		Organization organization = this.get(id);

		// 先判断是否存在子模块，如果存在子模块，则不允许删除
		if (organization.getChildren().size() > 0) {
			throw new ServiceException(organization.getName()
					+ "组织下存在子组织，不允许删除。");
		}

		organizationDao.delete(id);
		super.delInCache(Organization.class, id);
	}

	/**
	 * @param parentId
	 * @param page
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Organization> find(Long parentId, Page page) {
		return organizationDao.findByParentIdPage(page, parentId);
	}

	/**
	 * @param parentId
	 * @param name
	 * @param page
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Organization> find(Long parentId, String name, Page page) {
		return organizationDao.findByParentIdAndKeywordsPage(parentId, name,
				page);
	}

	/**
	 * 判断是否是根组织.
	 */
	private boolean isRoot(Long id) {
		return id == 1;
	}

	/**
	 * 
	 * @return
	 */
	@Transactional(readOnly = true)
	public Organization getTree() {
		List<Organization> list = organizationDao.findAllWithCache();

		List<Organization> rootList = makeTree(list);

		return rootList.get(0);
	}

	private List<Organization> makeTree(List<Organization> list) {
		List<Organization> parent = new ArrayList<Organization>();
		// get parentId = null;
		for (Organization e : list) {
			if (e.getParent() == null) {
				e.setChildren(new ArrayList<Organization>(0));
				parent.add(e);
			}
		}
		// 删除parentId = null;
		list.removeAll(parent);

		makeChildren(parent, list);

		return parent;
	}

	private void makeChildren(List<Organization> parent,
			List<Organization> children) {
		if (children.isEmpty()) {
			return;
		}

		List<Organization> tmp = new ArrayList<Organization>();
		for (Organization c1 : parent) {
			for (Organization c2 : children) {
				c2.setChildren(new ArrayList<Organization>(0));
				if (c1.getId().equals(c2.getParent().getId())) {
					c1.getChildren().add(c2);
					tmp.add(c2);
				}
			}
		}

		children.removeAll(tmp);

		makeChildren(tmp, children);
	}

	@Override
	@Transactional
	public void save(Organization organization) {
		try {
			organizationDao.save(organization);
			super.cache(organization);
		} catch (Exception e) {
			ExceptionUtils.wapperException(e);
		}
	}

	@Override
	@Transactional
	public void update(Organization organization) {
		try {
		organizationDao.update(organization);
		super.updateInCache(organization);
		} catch (Exception e) {
			ExceptionUtils.wapperException(e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Organization get(Long parentId) {
		try {
			return organizationDao.get(parentId);
		} catch (Exception e) {
			ExceptionUtils.wapperException(e);
		}
		return null;
	}

	@Override
	public List<Organization> getByCondition(Organization condition, Page page) {
		try {
			try {
			    User user = super.getCurrentUser();
			    Long accountId = null;
			    //如果是系统级用户则允许查询所有数据
				if(user != null && user.getUserType() != UserTypeEnum.SYSTEM.getValue()){
					accountId = user.getId();
				}
				return organizationDao.findByPage(accountId,condition,null, page);
			} catch (Exception e) {
			    logger.error(e.getLocalizedMessage(),e);
				ExceptionUtils.wapperException(e);
			}
		} catch (Exception e) {
			ExceptionUtils.wapperException(e);
		}
		return null;
	}

	@Override
	public List<Organization> getByIds(Long... array) {
		try {
			return organizationDao.getByIds(array);
		} catch (Exception e) {
			ExceptionUtils.wapperException(e);
		}
		return null;
	}

	@Override
	protected BaseDao getDaoByEntryType(Class<? extends IDEntity> clazz) {
		// TODO Auto-generated method stub
		return null;
	}

}
