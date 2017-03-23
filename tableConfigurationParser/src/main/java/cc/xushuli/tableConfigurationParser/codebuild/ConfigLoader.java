package cc.xushuli.tableConfigurationParser.codebuild;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import cc.xushuli.tableConfigurationParser.entity.ColumnConfiger;
import cc.xushuli.tableConfigurationParser.entity.MajorSectionConfiger;
import cc.xushuli.tableConfigurationParser.entity.MatchConfiger;
import cc.xushuli.tableConfigurationParser.entity.SpecItemConfiger;
import cc.xushuli.tableConfigurationParser.entity.TableConfiger;

public class ConfigLoader {
	private Document doc;
	
	public ConfigLoader(String xml) {
		this.doc = Jsoup.parse(xml,"", Parser.xmlParser());
	}
	
	private static ArrayList<MatchConfiger> makeMatches(Elements matchNodes){
		ArrayList<MatchConfiger> matches = new ArrayList<MatchConfiger>();
		if(!matchNodes.isEmpty()){
			for (Element matchNode : matchNodes) {
				MatchConfiger match = new MatchConfiger();
				match.setMatchTo(matchNode.attr("matchTo"));
				match.setPattern(matchNode.attr("pattern"));
				matches.add(match);
			}
		}
		return matches;
	}
	
	private static ArrayList<ColumnConfiger> makeColumns(Elements columnNodes){
		ArrayList<ColumnConfiger> columns = new ArrayList<ColumnConfiger>();
		for (Element columnNode : columnNodes) {
			ColumnConfiger column = new ColumnConfiger();
			column.setColIndex(columnNode.attr("colIndex"));
			column.setFilters(columnNode.attr("filters"));
			column.setId(columnNode.attr("id"));
			column.setDefaultValue(columnNode.attr("defaultValue"));
			column.setDefaultTo(columnNode.attr("defaultTo"));
			ArrayList<MatchConfiger> matches = makeMatches(columnNode.select("match"));
			column.setMatches(matches);
			columns.add(column);
		}
		return columns;
	}
	
	private static MajorSectionConfiger makeMajorSection(Document doc){
		MajorSectionConfiger majorSection = new MajorSectionConfiger();
		Elements majorSectionNode = doc.select("majorSection");
		Elements columnNodes = majorSectionNode.select("columns").select("column");
		majorSection.setStartIndex(majorSectionNode.attr("startIndex"));
		majorSection.setColumnProcessers(makeColumns(columnNodes));
		return majorSection;
	}
	private static List<SpecItemConfiger> makeSpecItems(Document doc) {
		Elements itemNodes = doc.select("specItems").select("specItem");
		List<SpecItemConfiger> specItems = new ArrayList<SpecItemConfiger>();
		for (Element itemNode : itemNodes) {
			SpecItemConfiger specItem = new SpecItemConfiger();
			specItem.setColIndex(itemNode.attr("colIndex"));
			specItem.setRowIndex(itemNode.attr("rowIndex"));
			specItem.setId(itemNode.attr("id"));
			specItem.setMatches(makeMatches(itemNode.select("match")));
			specItem.setFilters(itemNode.attr("filters"));
			specItems.add(specItem);
		}
		return specItems;
	}
	
	private static TableConfiger makeTable(Document doc){
		Elements tableNode = doc.select("table");
		TableConfiger table = new TableConfiger();
		table.setEntity(tableNode.attr("entity"));
		table.setXlsFile(tableNode.attr("xlsFile"));
		table.setSpecItems(makeSpecItems(doc));
		table.setMajorSection(makeMajorSection(doc));
		return table;
	}


	public TableConfiger loadTable(){
		return makeTable(doc);
	}
	
	public static TableConfiger loadTable(String xml){
		Document doc = Jsoup.parse(xml);
		return makeTable(doc);
	}
	
	public static TableConfiger loadTable(File xmlFile){
		String xml = "";
		try {
			 xml = readXML(xmlFile.getAbsolutePath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return loadTable(xml);
		
	}
	public static String readXML(String xmlPath) throws IOException{
		File xmlFile = new File(xmlPath);
		StringBuffer sb = new StringBuffer();
		BufferedReader br = new BufferedReader(new FileReader(xmlFile));
		String xml;
		while((xml=br.readLine())!=null){
			sb.append(xml);
		}
		return sb.toString();
	}
	
	public static void main(String[] args) throws IOException {
		String xmlPath = "D:\\workspace\\MIS\\src\\com\\guige\\oim\\parser\\config\\prototype\\demo.xml";
		String xml = readXML(xmlPath);
		ConfigLoader c = new ConfigLoader(xml);
		TableConfiger t = c.loadTable();
		System.out.print(t.getMajorSection().getColumnProcessers().get(0).getFilters());
	}
}
