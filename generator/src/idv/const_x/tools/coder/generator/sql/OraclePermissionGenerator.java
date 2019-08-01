package idv.const_x.tools.coder.generator.sql;

import java.io.File;

import idv.const_x.tools.coder.generator.Context;


public class OraclePermissionGenerator {

	public String generator(Context context) {

		
		String  insert = "INSERT INTO security_module ( id,description, name, priority, url, parent_id, sn, type,creater,createtime,modifyer,modifytime) VALUES (";
		
		StringBuilder builder = new StringBuilder();
		builder.append("#插入业务权限\n");
		
		
		builder.append("delete from security_module  where parent_id in \n")
		.append(" ( select t2.id from security_module t2 where t2.sn = '").append(context.getComponent().toLowerCase()).append("' ) ;\n");
		builder.append("delete from security_module  where sn = '").append(context.getComponent().toLowerCase()).append("';\n");
		
		
		String pid = "( select t2.id from security_module t2 where t2.type = '0' )"; 
		builder.append(this.createInsert(insert,context.getModuleAlias(), context.getModuleAlias(), "#",1, pid, context.getModule().toLowerCase(), "1"));
		
		pid = "( select t2.id from security_module t2 where t2.sn = '"  +context.getModule().toLowerCase()+"' )"; 
		builder.append(this.createInsert(insert,"查看", context.getModuleAlias()+"查看操作" , "#" ,1, pid, "view", "2"));
		builder.append(this.createInsert(insert,context.getComponentAlias(), context.getComponentAlias(), "/" + context.getComponent().toLowerCase()+"/index",1, pid, context.getComponent().toLowerCase(), "1"));
		
		pid = "( select t2.id from security_module t2 where t2.sn = '"  +context.getComponent().toLowerCase()+"' and  parent_id <> 1 )"; 
		builder.append(this.createInsert(insert,"查看", context.getComponentAlias()+"查看操作" , "#" ,1, pid, "view", "2"));
		builder.append(this.createInsert(insert,"新增",  context.getComponentAlias()+"新增操作" , "#" ,2, pid, "save", "2"));
		builder.append(this.createInsert(insert,"修改",  context.getComponentAlias()+"修改操作" , "#" ,3, pid, "edit", "2"));
		builder.append(this.createInsert(insert,"删除",  context.getComponentAlias()+"删除操作" , "#" ,4, pid, "delete", "2"));
		if(context.hasExport()){
		   builder.append(this.createInsert(insert,"导出",  context.getComponentAlias()+"导出操作" , "#" ,5, pid, "export", "2"));
		}
		if(context.isNeedAudit()){
			builder.append(this.createInsert(insert,"提交/收回",  context.getComponentAlias()+"提交/收回操作" , "#" ,6, pid, "submit", "2"));
			builder.append(this.createInsert(insert,"审批/驳回",  context.getComponentAlias()+"审批/驳回操作" , "#" ,7, pid, "audit", "2"));
		}
		
		builder.append(" \n");
		String filepath = context.getSqlBasepath() + File.separator + context.getModule().toLowerCase() +  File.separator + "permission.sql";
		System.out.println("权限语句已生成至：" + filepath + "  请在开发库中执行,并请确认 组件" + context.getComponentAlias() +" 上级模块 （parent_id）不为空");
		
		builder.append("select * from security_module  where sn = '").append(context.getModule().toLowerCase()).append("';\n");
		builder.append("select * from security_module t1 where sn = '").append(context.getComponent().toLowerCase()).append("' or  parent_id in \n")
		.append(" ( select t2.id from security_module t2 where t2.sn = '").append(context.getComponent().toLowerCase()).append("' ) ;\n\n\n");


		
		return builder.toString();
	}
	
	
	private String createInsert(String insert,String alias, String descript,String url,int index,String parentId,String sn,String type){
		StringBuilder builder = new StringBuilder();
		builder.append(insert);
		builder.append("SEQ_SECURITY_MODULE.nextval,");
		append(builder,descript);
		append(builder,alias);
		append(builder,String.valueOf(index));
		append(builder,url);
		append(builder,parentId);
		append(builder,sn);
		append(builder,type);
		append(builder,"1");
		builder.append("sysdate,");
		append(builder,"1");
		builder.append("sysdate,");
		builder.deleteCharAt(builder.length() - 1);
		builder.append("); \n");
		return builder.toString();
	}
	
	private void append(StringBuilder builder,String value){
		if(value.startsWith("(")){
			builder.append(value).append(",");
		}else{
			builder.append("'").append(value).append("',");
		}
		
	}

}
