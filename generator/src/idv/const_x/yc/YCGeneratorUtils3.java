package idv.const_x.yc;

import idv.const_x.jdbc.DBType;
import idv.const_x.jdbc.table.meta.EnumType;
import idv.const_x.jdbc.table.meta.SimpleTypeEnum;
import idv.const_x.tools.coder.generator.CodeGenerator;
import idv.const_x.tools.coder.generator.Context;
import idv.const_x.tools.coder.generator.Field;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class YCGeneratorUtils3 {

    public static void main(String args[]) throws Exception {
        //创建模块
        create();
    }


    private static void create() throws Exception {
        Context context = new Context();

        // 指定 所属模块、组件，以及对应实体
        context.setEntity("TemplateFlowNotifyTask");
        context.setEntityAlias("流程提醒延迟任务");
//		context.setComponent("member");
//		context.setComponentAlias("会员");
        context.setModule("template.flow");
        context.setModuleAlias("单据流程提醒");
        //自定义表名 如果不设置的话 默认按  模块_实体 生成表名
//		context.setTableName("member_viper");
        //表名前缀
//		context.setTableProfix("oms");

        context.addFields(getFields());

        // 是否支持数据等级
        context.setLevel(false);
        // 是否支持审批
        context.setNeedAudit(false);
        // 是否支持逻辑删
        context.setLogicDel(false);
        // 是否系统级数据（只能由系统级用户维护的数据）
        context.setSystemData(false);
        // 是否树结构类型
        context.setTree(false);

        // 需创建的文件
        context.genSqls(true);// 建库脚本，模块注册脚本
        context.genEntityCodes(true);// 实体文件和view文件
        context.genMapperAndDao(true);// mapper映射文件和dao
        context.genOtherCodes(false);// controller和service
        context.genViews(false);// jsp页面

        context.setBasepackage("com.raycloud.yc.eco.ding.app");
        context.setAuther("const.x");
        context.setDbType(DBType.MYSQL);
        FileSystemView fsv = FileSystemView.getFileSystemView();
        File com = fsv.getHomeDirectory();
        String base = com.getPath();

        		context.setBasepath("/Users/xieyalong/workspace/com/CRM/dingcrm/app-work-order-service/src/main");
//        context.setBasepath("/Users/xieyalong/Desktop");
        context.setSqlBasepath(base + "/code/sql");
        CodeGenerator.generator(context);
    }


    private static List<Field> getFields() throws Exception {
        List<Field> list = new ArrayList<Field>();
        // 字段示例
        Field field;

        field = new Field("deployId", SimpleTypeEnum.STRING_128, "预设对象ID");
        field.setIsCondition(true);
        field.setSortable(true);
        field.setNullable(false);
        list.add(field);

        field = new Field("dataId", SimpleTypeEnum.STRING_128, "对应表单数据Id");
        field.setIsCondition(true);
        field.setSortable(true);
        field.setNullable(false);
        list.add(field);

        field = new Field("templateId", SimpleTypeEnum.STRING_32, "模板ID");
        field.setIsCondition(true);
        field.setSortable(true);
        field.setNullable(false);
        list.add(field);

        field = new Field("corpId", SimpleTypeEnum.STRING_32, "企业Id");
        field.setIsCondition(true);
        field.setSortable(true);
        field.setNullable(false);
        list.add(field);

        field = new Field("nodeCode", SimpleTypeEnum.STRING, "流程节点");
        field.setIsCondition(true);
        list.add(field);

        field = new Field("baseTime", SimpleTypeEnum.DATETIME, "超时基准时间");
        field.setIsCondition(true);
        field.setNullable(false);
        list.add(field);

        field = new Field("instanceId", SimpleTypeEnum.LONG, "流程实例Id");
        field.setIsCondition(true);
        field.setNullable(false);
        list.add(field);

        // * 枚举字段
        String[] enumNames = {"BEFORE", "CURRENT", "AFTER"};
        String[] alias = {"提前", "当时", "推后"};
        String[] values = {"1", "2", "3"};
        field = new Field("notifyTimeType", new EnumType(enumNames, alias, values), "提醒方式");
        field.setIsCondition(true);
        field.setNullable(false);
        list.add(field);

        field = new Field("notifyTime", SimpleTypeEnum.DATETIME, "提醒时间");
        field.setIsCondition(true);
        field.setNullable(false);
        list.add(field);

        // * 枚举字段
        String[] enumNames2 = {"PROCESS", "DONE"};
        String[] alias2 = {"未提醒", "已提醒"};
        String[] values2 = {"1", "2"};
        field = new Field("notifyStatus", new EnumType(enumNames2, alias2, values2), "提醒状态");
        field.setIsCondition(true);
        field.setNullable(false);
        list.add(field);

        field = new Field("extendJson", SimpleTypeEnum.TEXT, "提醒配置");
        list.add(field);
        return list;
    }


}
