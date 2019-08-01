package idv.constx.demo.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import idv.constx.demo.security.dao.PermissionDao;
import idv.constx.demo.security.entity.PermissionSearchCriteria;
import idv.constx.demo.security.service.PermissionService;

@Service
@Transactional(readOnly=true)
public class PermissionServiceImpl implements PermissionService {
	@Autowired
	private PermissionDao permissionDao;
	
	@Override
	public List<String> getPermissionListByRole(PermissionSearchCriteria criteria) {
		return permissionDao.findPermissionListByRole(criteria);
	}

}
