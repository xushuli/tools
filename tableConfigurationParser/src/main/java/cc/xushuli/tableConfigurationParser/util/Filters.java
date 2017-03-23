package cc.xushuli.tableConfigurationParser.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Filters {
	public static String numberFilter(String oldString){
		return oldString.replaceAll("[, a-zA-Z]", "");
	}
	
	public static String percentFilter(String oldString){
		Double doubleValue = Double.parseDouble(oldString.replace("%", ""));
		return (doubleValue / 100) + "";
	}
	
	public static String tingpaiFilter(String oldString){
		Matcher m = Pattern.compile("停牌").matcher(oldString);
		if(m.find()){
			return "停牌_" + dateFilter(oldString);
		}else{
			return "";
		}
	}
	public static String dateFilter(String oldString){
		Matcher m = Pattern.compile("20\\d{2}[-/年]?[0-1][0-9][-/月]?[0-3][0-9][日]?").matcher(oldString);
		if(m.find()){
			String date = oldString.substring(m.start(),m.end());
			String noSepDate = date.replaceAll("[-日]", "").replaceAll("[-/年月]", "");
			return noSepDate.substring(0,4)+"-"+noSepDate.substring(4,6) + "-" + noSepDate.substring(6);
		}
		return oldString;
	}
	public static void main(String[] args) {
		String t = tingpaiFilter("【停牌】(2016-12-29)");
		System.out.println(t);
	}
}
