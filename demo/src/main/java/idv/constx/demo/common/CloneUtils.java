package idv.constx.demo.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

public class CloneUtils {

	public static <T> T deepClone(T source) {
		if (source == null) {
			return null;
		}
		try{
			if (source instanceof Collection) {
				return (T) deepCopyCollection((Collection) source);
			}
			if (source.getClass().isArray()) {
				return (T) deepCopyArray(source);
			}
			if (source instanceof Map) {
				return (T) deepCopyMap((Map) source);
			}
			if (source instanceof Date) {
				return (T) new Date(((Date) source).getTime());
			}
			if (source instanceof Timestamp) {
				return (T) new Timestamp(((Timestamp) source).getTime());
			}
			// 基本类型直接返回原值
			if (source.getClass().isPrimitive() || source instanceof String || source instanceof Boolean
					|| Number.class.isAssignableFrom(source.getClass())) {
				return source;
			}
			if (source instanceof StringBuilder) {
				return (T) new StringBuilder(source.toString());
			}
			if (source instanceof StringBuffer) {
				return (T) new StringBuffer(source.toString());
			}
			return deepCopyBean(source);
		}catch(Exception e){
			e.printStackTrace();
		}
	    return null;
	}

	private static Object deepCopyMap(Map source) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
		  Map dest = source.getClass().newInstance();
	        for (Object o : source.entrySet())
	        {
	            Entry e = (Entry)o;
	            dest.put(deepCopyBean(e.getKey()), deepCopyBean(e.getValue()));
	        }
	        return dest;
	}

	private static Object deepCopyCollection(Collection source) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
		Collection dest = source.getClass().newInstance();
        for (Object o : source)
        {
            dest.add(deepCopyBean(o));
        }
        return dest;
	}

	private static Object deepCopyArray(Object source) throws ArrayIndexOutOfBoundsException, IllegalArgumentException, ClassNotFoundException, IOException {
		int length = Array.getLength(source);
        Object dest = Array.newInstance(source.getClass().getComponentType(), length);
        if (length == 0)
        {
            return dest;
        }
        for (int i = 0; i < length; i++)
        {
            Array.set(dest, i, deepCopyBean(Array.get(source, i)));
        }
        return dest;
	}
	
	private static  <T> T  deepCopyBean(T  source) throws IOException, ClassNotFoundException {
    		ByteArrayOutputStream bo=new ByteArrayOutputStream();    
        	ObjectOutputStream oo=new ObjectOutputStream(bo);    
			oo.writeObject(source);
			//从流里读出来    
	    	ByteArrayInputStream bi=new ByteArrayInputStream(bo.toByteArray());    
	    	ObjectInputStream oi=new ObjectInputStream(bi);  
	    	return(T)(oi.readObject());   
    }  

}
