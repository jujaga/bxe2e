package org.oscarehr.common.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javassist.Modifier;

import javax.persistence.EmbeddedId;
import javax.persistence.Id;

import org.apache.log4j.Logger;

public class EntityModelUtils {
	private static Logger log = Logger.getLogger(EntityModelUtils.class.getName());

	public static void invokeMethodsForModelClass(Object model) {
		Method m[] = model.getClass().getDeclaredMethods();
		AccessibleObject.setAccessible(m, true);

		for (Integer i = 0; i < m.length; i++) {
			try {
				// Setters
				if(m[i].getName().startsWith("set")) {
					Object[] args = {null};
					m[i].invoke(model, args);
				}
				// Getters and Booleans
				else if(m[i].getName().startsWith("get")){
					m[i].invoke(model);
				}
			} catch (Exception e) {
				log.warn(e.toString() + " - " + m[i].getName());
			}
		}
	}

	public static Object generateTestDataForModelClass(Object model) {
		Field f[] = model.getClass().getDeclaredFields();
		AccessibleObject.setAccessible(f, true);

		for (Integer i = 0; i < f.length; i++) {
			Boolean isId = false;
			Annotation annotations[] = f[i].getAnnotations();
			for (Integer j = 0; j < annotations.length; j++) {
				if(annotations[j].annotationType() == Id.class) {
					isId = true;
				}
				if(annotations[j].annotationType() == EmbeddedId.class) {
					isId = true;
				}
			}

			if(isId)
				continue;

			Integer modifiers = f[i].getModifiers();
			if((modifiers & Modifier.STATIC) == Modifier.STATIC) {
				continue;
			}

			try {
				if(f[i].getType() == String.class) {
					f[i].set(model, f[i].getName() + ((int)(Math.random()*10000)));
				}
				else if(f[i].getType() == int.class || f[i].getType() == Integer.class) {
					f[i].set(model,(int)(Math.random()*10000));
				}
				else if(f[i].getType() == long.class || f[i].getType() == Long.class) {
					f[i].set(model,(long)(Math.random()*10000));
				}
				else if(f[i].getType() == float.class || f[i].getType() == Float.class) {
					f[i].set(model,(float)(Math.random()*100));
				}
				else if(f[i].getType() == double.class || f[i].getType() == Double.class) {
					f[i].set(model,Math.random()*100);
				}
				else if(f[i].getType() == Date.class) {
					f[i].set(model,new Date());
				}
				else if(f[i].getType() == Timestamp.class) {
					f[i].set(model,Timestamp.valueOf("2015-01-07 12:05:16"));
				}
				else if(f[i].getType() == Calendar.class) {
					f[i].set(model,Calendar.getInstance());
				}
				else if(f[i].getType() == boolean.class || f[i].getType() == Boolean.class) {
					f[i].set(model,true);
				}
				else if(f[i].getType() == byte.class || f[i].getType() == Byte.class) {
					f[i].set(model,(byte)0xAA);
				}
				else if(f[i].getType() == char.class || f[i].getType() == Character.class) {
					f[i].set(model,'A');
				}
				else if(f[i].getType() == Set.class || f[i].getType() == List.class || f[i].getType() == Map.class) {
					// Ignore
				}
				else if(f[i].getType() == char.class || f[i].getType() == BigDecimal.class) {
					BigDecimal bd = new BigDecimal(Math.random()*5000);
					f[i].set(model,bd);
				} else {
					log.warn("Can't generate test data for class type:" + f[i].getType());
				}
			} catch (Exception e) {
				log.warn(e.toString() + " - " + f[i].getName());
			}
		}

		return model;
	}
}
