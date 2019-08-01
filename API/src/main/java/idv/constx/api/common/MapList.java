package idv.constx.api.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 * 
 * 
 * @param <K>
 * @param <V>
 *
 * @author const.x
 */
public class MapList<K, V> {

	private final Map<K, List<V>> map = new HashMap<K, List<V>>();

	public int size() {
		return map.size();
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		for(List<V> arr : map.values()){
			if(arr != null && arr.contains(value)){
				return true;
			}
		}
		return false;
	}

	public List<V> get(K key) {
		return map.get(key);
	}

	public V put(K key, V value) {
		if (map.containsKey(key)) {
			List<V> arr = map.get(key);
			arr.add(value);
		} else {
			List<V> arr = new ArrayList<V>();
			arr.add(value);
			map.put(key, arr);
		}
		return value;
	}

	public List<V> remove(K key) {
		return map.remove(key);
	}

	public void clear() {
		map.clear();
	}

	public Set<K> keySet() {
		return map.keySet();
	}

	public Collection<List<V>> values() {
		return map.values();
	}

}
