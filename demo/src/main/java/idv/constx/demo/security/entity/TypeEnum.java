package idv.constx.demo.security.entity;

import idv.constx.demo.base.entity.IEnum;


 /**
 * 参数类型枚举
 */ 
  public enum TypeEnum  implements IEnum { 

 		/** 0:整数*/ 
		INT(0,"整数"),
		/** 1:小数*/ 
		DOUBLE(1,"小数"),
		/** 2:字符*/ 
		STRING(2,"字符"),
		/** 3:日期*/ 
		DATE(3,"日期"); 

		private int value;
		private String name;

		public int getValue() {
			return value;
		}

		public String getName() {
			return name;
		}

		private TypeEnum(int value, String name) {
			this.value = value;
			this.name = name;
		}

		public static TypeEnum getTypeByInt(int value){
		   TypeEnum[]  values = TypeEnum.values();
		   for(TypeEnum enumValue : values){
				if(enumValue.getValue() == value){
					return enumValue;
				}
			}
			return null;
		}

  }