package idv.constx.demo.common;

import java.util.List;

public class ListUtils {
	public static String listToString(List<Long> lists) {
		if (lists == null || lists.size() == 0) {
			return null;
		}
		StringBuilder result = new StringBuilder();
		boolean flag = false;
		for (Long list : lists) {
			if (flag) {
				result.append(",");
			} else {
				flag = true;
			}
			result.append(list);
		}
		return result.toString();
	}
}
