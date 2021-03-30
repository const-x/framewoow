package idv.const_x.yc.modify;


import idv.const_x.file.PlaceHodler;
import idv.const_x.tools.coder.CamelCaseUtils;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        String s  = Main.create("restartTime", "Date", "重启时间",null);
//        JOptionPaneExt.showTextMessageDialog("",s);
        System.out.println(s);
    }

    public static String create(String field,String type,String alias,String column){

        PlaceHodler hodler = new PlaceHodler();
        String templateFile = "";
        if(type.equalsIgnoreCase("long") ||type.equalsIgnoreCase("Integer") ||type.equalsIgnoreCase("int")   ){
            templateFile = "LongTmp";
        }else if(type.equalsIgnoreCase("date") ){
            templateFile = "DateTmp";
        }else if(type.equalsIgnoreCase("boolean") ){
            templateFile = "BooleanTmp";
        }else{
            templateFile = "StringTmp";
        }

        Map<String, String> properties = new HashMap<String, String>();
        properties.put("fieldFristUp", CamelCaseUtils.toFristUpper(field));
        properties.put("fieldFristLow", CamelCaseUtils.toFristLower(field));
        properties.put("fieldLow", field.toLowerCase());
        properties.put("type", type);
        properties.put("alias", alias);
        if(column == null || column.trim().equals("")){
            column = CamelCaseUtils.toUnderlineName(field);
        }
        properties.put("column", column);
        String path = Main.class.getResource(templateFile).toString().substring(6);
        path = "/" + path;
        return hodler.hodle(path, properties);
    }
}
