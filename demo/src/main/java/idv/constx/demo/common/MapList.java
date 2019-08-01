package idv.constx.demo.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class MapList<K,V> {

	private Map<K,List<V>> map = new HashMap<K,List<V>>();

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
		return false;
	}

	public List<V> get(K key) {
		return map.get(key);
	}

	public V put(K key, V value) {
		if(map.containsKey(key)){
			List<V> arr = map.get(key);
			arr.add(value);
		}else{
			List<V> arr = new ArrayList<V>();
			arr.add(value);
			map.put(key, arr);
		}
		return value;
	}
	
	public Collection<V> putAll(K key, List<V> values) {
		if(map.containsKey(key)){
			List<V> arr = map.get(key);
			arr.addAll(values);
		}else{
			List<V> arr = new ArrayList<V>();
			arr.addAll(values);
			map.put(key, arr);
		}
		return values;
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

	public Set<Entry<K, List<V>>> entrySet() {
		return map.entrySet();
	}



	
	
	
	
}
