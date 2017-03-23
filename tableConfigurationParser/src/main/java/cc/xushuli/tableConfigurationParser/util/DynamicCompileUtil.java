package cc.xushuli.tableConfigurationParser.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.ToolProvider;
import javax.tools.JavaFileObject.Kind;

class JavaFileObjectFromString extends SimpleJavaFileObject {
	/**
	 * Java源文件的内容
	 */
	final String code;

	/**
	 * @param className
	 *            类的完全引用名称，如：com.guige.core.SomeClass
	 *            不需要加.java扩展名
	 * @param code
	 * 			  Java代码字符串
	 */
	public JavaFileObjectFromString(String className, String code) {
		// uri为类的资源定位符号, 如: com/stone/generate/Hello.java
		super(URI.create(className.replaceAll("\\.", "/")
				+ Kind.SOURCE.extension), Kind.SOURCE);
		
		this.code = code;
	}

	@Override
	public CharSequence getCharContent(boolean ignoreEncodingErrors) {
		return code;
	}
}

public class DynamicCompileUtil {
	private final static File CLASSDEST = new File("build/classes/");
	private final static JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
	static URL[] urls = null;
    static URLClassLoader classLoader = null;
    
    static{
		try {
			urls = new URL[] {new URL("file:/" + CLASSDEST.getAbsolutePath())};
			classLoader = new URLClassLoader(urls);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
    
	/**
	 * 
	 * @param classFullName 类的完全引用名称，如：com.guige.core.SomeClass，不需要加.java扩展名
	 * @param code Java代码字符串
	 */
	public static void compile(String classFullName,String code){
	     JavaFileObject javaFileObject = new JavaFileObjectFromString(classFullName, code);
	        JavaCompiler.CompilationTask task = compiler.getTask(null, null, null,
	                Arrays.asList("-d", CLASSDEST.getAbsolutePath()), null,
	                Arrays.asList(javaFileObject));
	        boolean compileSuccess = task.call();
	        if (!compileSuccess) {
	            System.out.println("编译失败");
	        }
	}
	
	
	public static Class<?> loadClass(String classFullName) throws ClassNotFoundException{
		return classLoader.loadClass(classFullName);
	}
	
	public static void main(String[] args) {
		String packageName = DynamicCompileUtil.class.getPackage().getName();
		String javaName = ".HelloWorld";
		String classFullName = packageName + javaName;
	}
}
