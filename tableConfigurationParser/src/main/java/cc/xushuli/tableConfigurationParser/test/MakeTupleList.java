package cc.xushuli.tableConfigurationParser.test;

import java.io.IOException;

import cc.xushuli.tableConfigurationParser.codebuild.ConfigLoader;
import cc.xushuli.tableConfigurationParser.codebuild.TupleListPreProcess;

public class MakeTupleList {
	public static void main(String[] args) throws IOException {
		// 获取估值表解析配置文件的文本字符串
		String xmlPath = "D:\\workspace\\tableConfigurationParser\\parserConfig\\alipay.xml";
		String xml = ConfigLoader.readXML(xmlPath);

		TupleListPreProcess tupleListPreProcess = new TupleListPreProcess(xml);
		tupleListPreProcess.scanMajorSection();
		int t =0;
	}
}
