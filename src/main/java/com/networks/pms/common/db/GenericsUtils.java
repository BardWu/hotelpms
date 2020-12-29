package com.networks.pms.common.db;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author echo
 *
 */
public class GenericsUtils {
	//private static Logger logger = LoggerFactory.getLogger(GenericsUtils.class);

	private GenericsUtils() {
	}

	@SuppressWarnings("rawtypes" )
	public static Class getSuperClassGenricType(Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	@SuppressWarnings("rawtypes" )
	public static Class getSuperClassGenricType(Class clazz, int index) {

		Type genType = clazz.getGenericSuperclass();
		if (!(genType instanceof ParameterizedType)) {
			//logger.warn(clazz.getSimpleName()
				//	+ "'s superclass not ParameterizedType");
			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		if (index >= params.length || index < 0) {
			//logger.warn("Index: " + index + ", Size of " + clazz.getSimpleName()
				//	+ "'s Parameterized Type: " + params.length);
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			//logger.warn(clazz.getSimpleName()
				//	+ " not set the actual class on superclass generic parameter");
			return Object.class;
		}
		return (Class) params[index];
	}

    public static boolean isAvailableLong(Long num){
        return num!=null&&num!=0L?true:false;
    }
    
	/**
	 * Get all declaredFields including Superclass fields
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("rawtypes" )
	public static List<Field> getAllFields(Class clazz) {
		List<Field> fields = new ArrayList<Field>();
		for(int i=0; i<clazz.getDeclaredFields().length; i++){
			if(!Modifier.isStatic(clazz.getDeclaredFields()[i].getModifiers())){
				fields.add(clazz.getDeclaredFields()[i]);
			}
		}
		Class classTmp = clazz;
		while(classTmp != Object.class){
			classTmp = classTmp.getSuperclass();
			for(int i=0; i<classTmp.getDeclaredFields().length; i++){
				if(!Modifier.isStatic(classTmp.getDeclaredFields()[i].getModifiers())){
					fields.add(classTmp.getDeclaredFields()[i]);
				}
			}
		}
		return fields;
	}
}
