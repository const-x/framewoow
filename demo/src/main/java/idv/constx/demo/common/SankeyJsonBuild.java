package idv.constx.demo.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * 
 * @author hpp
 *
 * 2016年10月12日
 */
public class SankeyJsonBuild {
	/**
	 * 构造json数据
	 * 
	 * @param object
	 * @return
	 * @throws JSONException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String BuildJson(List list) throws JSONException {
		JSONObject jsonobject = new JSONObject();
		JSONArray linksarray = new JSONArray();
		JSONArray nodesarray = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			SankeyData data=(SankeyData)list.get(i);
			Map m = new HashMap();
			m.put("source", data.getSource());
			m.put("target", data.getTarget());
			m.put("value", data.getValue());
			linksarray.put(m);
			Map namemap = new HashMap();
			namemap.put("name", data.getSource());
			if(isInarray(nodesarray,namemap)){
				nodesarray.put(namemap);
			}
			Map tn = new HashMap();
			tn.put("name",data.getTarget());
			if(isInarray(nodesarray,tn)){
				nodesarray.put(tn);
			}
		}
		jsonobject.put("links", linksarray);
		jsonobject.put("nodes", nodesarray);
		return jsonobject.toString();
	}
	
	@SuppressWarnings("rawtypes")
	private static boolean isInarray(JSONArray array,Map map) throws JSONException{
		for (int i = 0; i <array.length(); i++) {
			JSONObject m=(JSONObject) array.get(i);
			if(m.get("name").equals(map.get("name"))){
				return false;
			}
		}
		return true;
	}
}
