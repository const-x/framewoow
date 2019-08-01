package idv.constx.demo.security.entity;

import java.util.List;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;

/**
 * 用户在模块下的权限情况
 * 该实例只用于定义角色更新时的前后台交互数据结构，不会持久化
 * 
 * @author const.x
 */
public class RoleModulePermission  extends Module{
	
	public static final transient String HAS_PERMISSION = "checked";
	

	private static final long serialVersionUID = 7830807861889114639L;
	
	
	public RoleModulePermission(Module m){
		this.setDescription(m.getDescription());
		this.setId(m.getId());
		this.setName(m.getName());
		this.setPriority(m.getPriority());
		this.setSn(m.getSn());
		this.setType(m.getType());
		this.setUrl(m.getUrl());
	}
	
	/**
	 * 角色id
	 */
	private Long role;
	/**
	 * 在该模块（业务操作）下是否有权限
	 */
	private String hasPermission;
	/**
	 * 业务操作
	 */
	private List<Module> operations = Lists.newArrayList();
	
	
	
	/**
	 * 将模块下的业务操作区分出来
	 *
	 * @author const.x
	 * @createDate 2014年8月20日
	 */
	@Override
	public void setChildren(List<Module> children) {
		 List<Module> chs = Lists.newArrayList();
		for(Module m:children){
			if(m.getType().equals(ModuleTypeEnum.OPERATION.getValue())){
				operations.add(m);
			}else{
				chs.add(m);
			}
		}
		
		super.setChildren(chs);
	}
	/**
	 * @return the operations
	 *
	 * @author const.x
	 * @createDate 2014年8月20日
	 */
	public List<Module> getOperations() {
		return operations;
	}

	/**
	 * @return the role
	 *
	 * @author const.x
	 * @createDate 2014年8月20日
	 */
	public Long getRole() {
		return role;
	}
	/**
	 * @param role the role to set
	 *
	 * @author const.x
	 * @createDate 2014年8月20日
	 */
	public void setRole(Long role) {
		this.role = role;
	}
	/**
	 * @return the hasPermission
	 *
	 * @author const.x
	 * @createDate 2014年8月20日
	 */
	public String getHasPermission() {
		return hasPermission;
	}
	/**
	 * @param hasPermission the hasPermission to set
	 *
	 * @author const.x
	 * @createDate 2014年8月20日
	 */
	public void setHasPermission(String hasPermission) {
		this.hasPermission = hasPermission;
	}

	

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.addValue(this.getId())
				.addValue(this.getName())
				.addValue(this.getParent() == null ? null:this.getParent().getName())
				.addValue(this.getChildren().size())
				.addValue(this.getOperations().size())
				.toString();
	}
	

}
