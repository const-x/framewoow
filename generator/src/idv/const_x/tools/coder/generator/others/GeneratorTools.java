package idv.const_x.tools.coder.generator.others;

import java.io.File;
import java.util.List;

import idv.const_x.jdbc.table.meta.ComplexType;
import idv.const_x.jdbc.table.meta.Type;
import idv.const_x.tools.coder.VOAttributeUtils;
import idv.const_x.tools.coder.generator.Context;
import idv.const_x.tools.coder.generator.Field;
import idv.const_x.tools.coder.generator.IGenerator;

public class GeneratorTools  {
	
	
	public static void SetterAndGetterGenerator(Context context){
		List<Field> fields = context.getFields();
		for (Field data : fields) {
			Type type = data.getType();
			String field = data.getName();
			String javatype = data.getType().getJavaType();
			if(!type.isSimpleType()){
				ComplexType complex = (ComplexType)type;
				if(complex.getRelationship() != ComplexType.ONE_TO_ONE){
					javatype = "List<" +type.getJavaType() + ">";
					VOAttributeUtils.createSetterInvoke(context.getEntity().toLowerCase(),field, javatype,  data.getAlias());
			    }
			}
			System.out.print(VOAttributeUtils.createSetterInvoke(context.getEntity().toLowerCase(),field, javatype,  data.getAlias()));
		}
		
		for (Field data : fields) {
			Type type = data.getType();
			String field = data.getName();
			String javatype = data.getType().getJavaType();
			if(!type.isSimpleType()){
				ComplexType complex = (ComplexType)type;
				if(complex.getRelationship() != ComplexType.ONE_TO_ONE){
					javatype = "List<" +type.getJavaType() + ">";
					VOAttributeUtils.createGetterInvoke(field, javatype,  data.getAlias());
			    }
			}
			System.out.print(VOAttributeUtils.createGetterInvoke(field, javatype,  data.getAlias()));
		}
	}
	





	
}
