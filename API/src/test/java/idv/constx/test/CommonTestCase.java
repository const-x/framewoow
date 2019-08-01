package idv.constx.test;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

public class CommonTestCase  extends AbstractTestCase {
	
	
	public static void main(String[] args) {
		try {
			String s = JOptionPane.showInputDialog("访问地址");

			int index = s.indexOf("?");
			String params = s.substring(index + 1);
			String[] array = params.split("&");
			Map<String, Object> map = new HashMap<>();
			
			for(String param : array){
				
				String[] arr = param.split("=");
//				if(arr[0].equals("appId") || arr[0].equals("bundleId") || arr[0].equals("platformSource") || arr[0].equals("pk") ){
//					continue;
//				}
				if(arr.length == 2){
					map.put(arr[0],arr[1]);
				}
			}
		//	map.put("accountid", "7");
		    post(map,null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
