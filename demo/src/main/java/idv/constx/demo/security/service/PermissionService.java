package idv.constx.demo.security.service;

import java.util.List;

import idv.constx.demo.security.entity.PermissionSearchCriteria;

public interface PermissionService {
	
	List<String> getPermissionListByRole(PermissionSearchCriteria criteria);
}
