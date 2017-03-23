package cc.xushuli.tableConfigurationParser.util;

import java.lang.reflect.InvocationTargetException;

import cc.xushuli.tableConfigurationParser.prototype.MethodMeta;



public class ReflectUtil {
	static String makeSetMethodName(String id) {
		return "set" + (id.charAt(0) + "").toUpperCase() + id.substring(1);
	}

	static String makeGetMethodName(String id) {
		return "get" + (id.charAt(0) + "").toUpperCase() + id.substring(1);
	}
	public static Class[] initParamTypes(int length,Class clazz){
		Class[] paramTypes = new Class[length];
		for (int i = 0; i < length; i++) {
			paramTypes[i] = clazz;
		}
		return paramTypes;
	}
	/**
	 * 
	 * @param id
	 * @param value
	 *            属性值，必须是String，所以entity必须有String参数的set方法
	 * @param clazz
	 * @param obj
	 */
	public static void setMethod(String id, String value, Class<?> clazz,
			Object obj) {
		String methodName = makeSetMethodName(id);
		try {
			clazz.getMethod(methodName, new Class[] { String.class }).invoke(
					obj, value);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			System.out.printf("-----------param is '%s'------------\n", value);
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Object getMethod(String id, Class<?> clazz, Object obj) {
		Object result = null;
		String methodName = makeGetMethodName(id);
		try {
			result = clazz.getMethod(methodName, new Class[] {}).invoke(obj,
					new Object[0]);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			System.out.println(methodName);
			e.printStackTrace();
		}
		return result;
	}

	public static String invokeStringUtilMethod(String stringMethod,String colValue)
			throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		MethodMeta m = new MethodMeta(stringMethod);
		String[] params = new String[m.getParamsArr().length+1];
		params[0] = colValue;
		for (int i = 1; i < params.length; i++) {
			params[i] = m.getParamsArr()[i-1];
		}
		return (String)StringUtil.class.getMethod(m.getMethodName(),initParamTypes(params.length,String.class)).invoke(null,params);

	}

	public static void main(String[] args) throws SecurityException,
			NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		
		String result = invokeStringUtilMethod("replace('123','456')", "123456");
		System.out.println(result);
	}
}
