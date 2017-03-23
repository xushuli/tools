package cc.xushuli.tableConfigurationParser.prototype;

import cc.xushuli.tableConfigurationParser.util.StringUtil;

public class MethodMeta {
	private String stringMethod;
	private String methodName;
	private String params;
	private String[] paramsArr;

	public MethodMeta(String stringMethod) {
		this.stringMethod = stringMethod;
		setMethodName(StringUtil.find(stringMethod, "\\w+\\("));
		setMethodName(StringUtil.substring(getMethodName(), 0, -1));
		params = StringUtil.findEmbracedBy(stringMethod, "\\(", "\\)");
		String[] paramsArr = params.split(",");
		
		for (int i = 0; i < paramsArr.length; i++) {
			paramsArr[i] = StringUtil.findEmbracedBy(paramsArr[i],"'","'");
		}
		
		this.paramsArr = paramsArr;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String[] getParamsArr() {
		return paramsArr;
	}

	public void setParamsArr(String[] paramsArr) {
		this.paramsArr = paramsArr;
	}

}
