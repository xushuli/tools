package cc.xushuli.tableConfigurationParser.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	public static String prepend(String colValue, String str) {
		return str + str;
	}

	public static String append(String colValue, String str) {
		return colValue + str;
	}

	public static String replace(String colValue, String REGX, String replace) {
		Matcher m = Pattern.compile(REGX).matcher(colValue);
		return m.replaceAll(replace);
	}
	public static String substring(String colValue,String start){
		return substring(colValue, Integer.valueOf(start));
	}
	public static String substring(String colValue, int start) {
		if(start<0){
			start +=colValue.length();
		}
		return colValue.substring(start);
	}

	public static String substring(String colValue,String start,String end){
		return substring(colValue, Integer.valueOf(start),Integer.valueOf(end));
	}
	public static String substring(String colValue, int start, int end) {
		if (end < 0) {
			end = colValue.length() + end;
		}
		if(start<0){
			start = colValue.length()+start;
		}
		
		return colValue.substring(start, end);
	}

	public static String find(String colValue,String REGX){
		Matcher m = Pattern.compile(REGX).matcher(colValue);
		if(m.find()){
			return colValue.substring(m.start(), m.end());
		}
		return null;
	}
	
	public static String findEmbracedBy(String colValue, String left,String right){
		String REGX = left+".*?"+right;
		if(REGX.isEmpty()){
			REGX = left+".*?"+right;
		}
		
		Matcher m = Pattern.compile(REGX).matcher(colValue);
		if (m.find()) {
//			System.out.println("findEmbracedBy:"+REGX+" "+m.start()+" "+m.end());
			return colValue.substring(m.start()+1,m.end()-1);
		}else{
			return null;
		}
	}
	
	public static void main(String[] args) {
		String s = findEmbracedBy("(2012年6月6日)", "\\(", "\\)");
		String result = find(s,"年\\d月");
		result = substring(result, 1,-1);
		System.out.println(result);
	}
}
