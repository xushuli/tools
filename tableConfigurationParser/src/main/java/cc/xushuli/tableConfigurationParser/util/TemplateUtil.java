package cc.xushuli.tableConfigurationParser.util;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.Renderable;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

public class TemplateUtil {
	/**
	 * 模板引擎
	 */
	static VelocityEngine ve;
	static final String EXTENSION = ".vm";
	static{
		ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class",
				ClasspathResourceLoader.class.getName());

		ve.init();
	}
	
	public static String render(String templateFullName, Map<String,Object> context){
		Template t = ve.getTemplate(templateFullName.replaceAll("\\.", "/")
				+ EXTENSION,"utf-8");
		VelocityContext ctx = new VelocityContext(context);
		StringWriter sw = new StringWriter();
		t.merge(ctx, sw);
		return sw.toString();
	}
	
	public static void main(String[] args) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("msg", "nddy");
		String t = TemplateUtil.render("com.guige.core.ext.util.hellovelocity",map);
		System.out.println(t);
	}
}
