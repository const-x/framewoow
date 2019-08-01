package idv.const_x.tools.coder.generator;

import java.util.ArrayList;
import java.util.List;

import idv.const_x.tools.coder.generator.valdate.AbsValidateStyles;
import idv.const_x.tools.coder.generator.valdate.Maxlength;
import idv.const_x.tools.coder.generator.valdate.Required;
import idv.const_x.tools.coder.generator.valdate.StaticValidateFactory;
import idv.const_x.jdbc.table.meta.SimpleType;
import idv.const_x.jdbc.table.meta.SimpleTypeEnum;
import idv.const_x.jdbc.table.meta.Type;

public class Field extends idv.const_x.jdbc.table.meta.Field{
	//是否在列表界面展示
	private boolean display = true;
	//是否可编辑
	private boolean editable = true;
	//是否作为查询条件
	private boolean isCondition = false;
	//是否不可序列化
	private boolean isTransient  = false;
	//是否支持界面排序
	private boolean isSortable  = false;
	//是否支持导出
    private boolean export  = false;
	
	private List<AbsValidateStyles> validates = new ArrayList<AbsValidateStyles>();
	
	
	public Field(String field, Type type, String alias,boolean display,boolean editable) {
		super(field, type, alias);
		this.display = display;
		this.editable = editable;
		if(type.isPremiryKey()){
			super.setPrimary(true);
		}
		AbsValidateStyles validate = StaticValidateFactory.getValidate(type);
		if(validate != null){
			validates.add(validate);
		}
		validate = new Maxlength(type.getLength());	
		validates.add(validate);
	}
	
	public Field(String field, Type type, String alias) {
		super(field, type, alias);
		
		//解决后续设置类型参数后，对公共枚举值产生影响
        if(type instanceof SimpleTypeEnum){
        	type = SimpleType.clone(type);
		}
		
		if(type.isPremiryKey()){
			super.setPrimary(true);
		}
		AbsValidateStyles validate = StaticValidateFactory.getValidate(type);
		if(validate != null){
			validates.add(validate);
		}
		validate = new Maxlength(type.getLength());	
		validates.add(validate);
	}
	
	
	/** 
	 * 是否是否可编辑 
	 * 
	 * @return boolean 是否可编辑 
	 */ 
	public boolean isEditable() { 
	  return  this.editable; 
	} 
	/** 
	 * 设置是否可编辑   默认为true
	 * 
	 * @param value 是否可编辑
	 */ 
	public void setEditable(boolean value) { 
	  this.editable = value; 
	} 
	
    /**
     * 是否列表界面可见
     * 
     * @return 是否页面可见
     *
     */
	public boolean isDisplay() {
		return display;
	}

	/** 
	 * 设置是否列表界面可见  默认为true
	 * 
	 * @param value 是否页面可见
	 */ 
	public void setDisplay(boolean display) {
		if(!display){
			this.editable = false;
		}
		this.display = display;
	}
	
    /**
     * 是否作为查询条件
     * 
     * @return 是否作为查询条件
     *
     */
	public boolean isCondition() {
		return isCondition;
	}

	/** 
	 * 是否作为查询条件 默认为 false
	 * 
	 * @param value 是否作为查询条件
	 */ 
	public void setIsCondition(boolean isCondition) {
		this.isCondition = isCondition;
	}
	
	
	
	/**
	 * 是否支持界面排序
	 * @return
	 */
	public boolean isSortable() {
		return isSortable;
	}
  
	/**
	 * 是否支持界面排序 默认为 false
	 * @param isSortable
	 */
	public void setSortable(boolean isSortable) {
		this.isSortable = isSortable;
	}
	
	
	/**
	 * 是否不可序列化
	 * @return
	 */
	public boolean isTransient() {
		return isTransient;
	}
  
	/**
	 * 是否不可序列化
	 * @param isTranisant
	 */
	public void setTransient(boolean isTransient) {
		this.isTransient = isTransient;
	}
	
	public boolean isExport() {
		return export;
	}

	/**
	 * 是否可导出, 默认为 false
	 * @param export
	 */
	public void setExport(boolean export) {
		this.export = export;
	}

	@Override
	public void setNullable(boolean value) { 
	  super.setNullable(value); 
	  if(!value){
		  AbsValidateStyles validate = new Required();	
		  validates.add(validate);
	  }
	} 
	
	public void addValidate(AbsValidateStyles validate){
		validates.add(validate);
	}

	public List<AbsValidateStyles> getValidates() {
		return validates;
	}

	@Override
	public String toString() {
		return this.getAlias() + ":" + this.getName() ;
	}
	
	

}
