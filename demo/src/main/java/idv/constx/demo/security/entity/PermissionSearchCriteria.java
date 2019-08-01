package idv.constx.demo.security.entity;

import idv.constx.demo.base.entity.IDEntity;
import idv.constx.demo.base.entity.IMapperEntity;

public class PermissionSearchCriteria extends IDEntity implements  IMapperEntity{
	
	private Long roleId;
	
	private String permission;
	
	private Long parentId;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	@Override
	public String getShowName() {
		return permission;
	}
	
}
