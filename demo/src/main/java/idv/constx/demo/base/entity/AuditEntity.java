package idv.constx.demo.base.entity;

/**
 * 需审批的数据
 * 
 *
 * @author const.x
 */
public abstract class AuditEntity  extends IDEntity {

	private static final long serialVersionUID = -4093289196116736249L;
	
	/**最后审批时间*/
	private String audittime = null;
	/**审批状态*/
	private Integer auditStatus = null;
	/**最后审批人*/
	private Long auditer = null;
	
	
	/** 
	 * 获取最后审批时间 
	 * 
	 * @return String
	 */ 
	public String getAudittime() {
	  return  this.audittime; 
	} 
	/** 
	 * 设置最后审批时间 
	 * 
	 * @param String
	 */ 
	public void setAudittime(String value) {
		this.setAttrbute(FIELD_AUDITTIME, value);
	} 
	/** 
	 * 获取审批状态 
	 * 
	 * @return Integer
	 */ 
	public Integer getAuditStatus() {
	  return  this.auditStatus; 
	} 
	/** 
	 * 设置审批状态 
	 * 
	 * @param Integer
	 */ 
	public void setAuditStatus(Integer value) {
		this.setAttrbute(FIELD_AUDITSTATUS, value);
	} 
	/** 
	 * 获取最后审批人 
	 * 
	 * @return Long
	 */ 
	public Long getAuditer() {
	  return  this.auditer; 
	} 
	/** 
	 * 设置最后审批人 
	 * 
	 * @param Long
	 */ 
	public void setAuditer(Long value) {
		this.setAttrbute(FIELD_AUDITER, value);
	} 
	
	
	/**  字段名:最后审批时间 */
	public static final String FIELD_AUDITTIME = "audittime";
	/**  字段名:审批状态 */
	public static final String FIELD_AUDITSTATUS = "auditStatus";
	/**  字段名:最后审批人 */
	public static final String FIELD_AUDITER = "auditer";

}
