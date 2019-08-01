package idv.constx.demo.security.entity;

import idv.constx.demo.base.entity.IDEntity;
import idv.constx.demo.base.entity.IMapperEntity;

public class Permission  extends IDEntity implements  IMapperEntity{

	private Long roleId;
	private String permission;
	
	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		super.setAttrbute("roleId", roleId);
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		super.setAttrbute("permission", permission);
	}

	@Override
	public String getShowName() {
		return permission;
	}
	
}
