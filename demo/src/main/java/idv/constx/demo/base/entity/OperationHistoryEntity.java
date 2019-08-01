package idv.constx.demo.base.entity;

import java.io.Serializable;

import idv.constx.demo.security.entity.User;

public class OperationHistoryEntity implements Serializable,IMapperEntity{
	
	private static final long serialVersionUID = -4999629345142597092L;
	/**最后操作时间*/
	private String operattime = null;
	/**审批状态*/
	private Integer status = null;
	/**最后操作人*/
	private User operator = null;
	/**备注*/
	private String note = null;
	/**操作对象ID*/
	private Long target = null;
	
	/** 
	 * 获取最后操作时间 
	 * 
	 * @return String
	 */ 
	public String getOperattime() {
	  return  this.operattime; 
	} 
	/** 
	 * 设置最后操作时间 
	 * 
	 * @param String
	 */ 
	public void setOperattime(String value) {
	  this.operattime = value; 
	} 
	/** 
	 * 获取审批状态 
	 * 
	 * @return Integer
	 */ 
	public Integer getStatus() {
	  return  this.status; 
	} 
	/** 
	 * 设置审批状态 
	 * 
	 * @param Integer
	 */ 
	public void setStatus(Integer value) {
	  this.status = value; 
	} 
	/** 
	 * 获取最后操作人 
	 * 
	 * @return User
	 */ 
	public User getOperator() {
	  return  this.operator; 
	} 
	/** 
	 * 设置最后操作人 
	 * 
	 * @param User
	 */ 
	public void setOperator(User value) {
	  this.operator = value; 
	} 
	/** 
	 * 设置最后操作人 
	 * 
	 * @param id VIP会员审批记录ID值
	 */ 
	public void setOperatorByID(Long id) { 
	  User value = new User(); 
	  value.setId(id); 
	  this.operator = value; 
	} 
	/** 
	 * 获取备注 
	 * 
	 * @return String
	 */ 
	public String getNote() {
	  return  this.note; 
	} 
	/** 
	 * 设置备注 
	 * 
	 * @param String
	 */ 
	public void setNote(String value) {
	  this.note = value; 
	} 
	
	
	/**
	 * 操作对象ID
	 * @return
	 */
	public Long getTarget() {
		return target;
	}
	/**
	 * 操作对象ID
	 * @param target
	 */
	public void setTarget(Long target) {
		this.target = target;
	}



	/**  字段名:最后操作时间 */
	public static final String FIELD_OPERATTIME = "operattime";
	/**  字段名:审批状态 */
	public static final String FIELD_STATUS = "status";
	/**  字段名:最后操作人 */
	public static final String FIELD_OPERATOR = "operator";
	/**  字段名:备注 */
	public static final String FIELD_NOTE = "note";
	/**  字段名:VIP会员 */
	public static final String FIELD_TARGET = "target";
	
}
