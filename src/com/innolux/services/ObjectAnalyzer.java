package com.innolux.services;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;import javax.lang.model.type.NullType;

public class ObjectAnalyzer {
	/**
	 * Converts an object to a string representation that lists all fields.
	 * @param obj an object
	 * @return a string with the object's class name and all field names and values.
	 */
	public static String toString(Object obj) {
		ArrayList<Object> visited = new ArrayList<>();
		
		if (obj == null) return "null";
		if (visited.contains(obj)) return "...";
		visited.add(obj);
		Class<? extends Object> c1 = obj.getClass();
		if (c1 == String.class) return (String)obj;
		if (c1.isArray()){
			String r = c1.getComponentType() + "[]{";
			for (int i = 0; i < Array.getLength(obj); i++){
				if (i > 0) r+= ",";
				Object val = Array.get(obj, i);
				if (c1.getComponentType().isPrimitive()) r += val;
				else r += val.toString();
			}
			return r + "}";
		}

		String r = c1.getName();
		// inspect the fields of this class
		r += "[";
		Field[] fields = c1.getDeclaredFields();
		AccessibleObject.setAccessible(fields, true);
		// get the names and values of all fields
		for (Field f : fields) {
			if (!Modifier.isStatic(f.getModifiers())) {
				if (!r.endsWith("["))
					r += ",";
				r += f.getName() + "=";
				try {
					Object val = f.get(obj);
					if (val == null) continue;
					r += val.toString();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		r += "]";
		return r;
	}
}