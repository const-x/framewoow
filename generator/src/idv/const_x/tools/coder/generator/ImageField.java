package idv.const_x.tools.coder.generator;

import idv.const_x.jdbc.table.meta.SimpleType;
import idv.const_x.jdbc.table.meta.SimpleTypeEnum;

public class ImageField extends Field {

	private Integer width = null;

	private Integer height = null;

	private Integer maxWidth = null;

	private Integer maxHeight = null;

	private Integer size = null;
	private Integer maxSize = null;

	private boolean multiple = false;

	public ImageField(String field, String alias, Integer width,
			Integer height, Integer maxSize) {
		super(field, SimpleType.clone(SimpleTypeEnum.IMAGE), alias);
		this.width = width;
		this.height = height;
		this.maxSize = maxSize;
	}

	public ImageField(String field, String alias) {
		super(field, SimpleType.clone(SimpleTypeEnum.IMAGE), alias);
	}

	/**
	 * 是否多个文件
	 * 
	 * @return
	 * 
	 * @createDate 2015年4月9日
	 */
	public boolean isMultiple() {
		return multiple;
	}

	public void setMultiple(boolean multiple) {
		if(multiple){
			((SimpleType)this.getType()).setLength(1024);
		}
		this.multiple = multiple;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getMaxWidth() {
		return maxWidth;
	}

	public void setMaxWidth(Integer maxWidth) {
		this.maxWidth = maxWidth;
	}

	public Integer getMaxHeight() {
		return maxHeight;
	}

	public void setMaxHeight(Integer maxHeight) {
		this.maxHeight = maxHeight;
	}
	
	public Integer getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(Integer maxSize) {
		this.maxSize = maxSize;
	}

	@Override
	public void setIsCondition(boolean isCondition) {
		if (isCondition) {
			System.out.println(super.getName() + ":图片字段不支持查询条件");
		}
		super.setIsCondition(false);
	}

	@Override
	public void setSortable(boolean isSortable) {
		if (isSortable) {
			System.out.println(super.getName() + ":图片字段不支持表单排序");
		}
		super.setSortable(false);
	}

	@Override
	public void setExport(boolean export) {
		if (export) {
			System.out.println(super.getName() + ":图片字段不支持Excel导出");
		}
		super.setExport(false);
	}

}
