package cc.xushuli.tableConfigurationParser.entity;

import java.util.List;

public class TableConfiger {
	private String entity;
	private String xlsFile;
	private MajorSectionConfiger majorSection;
	private List<SpecItemConfiger> specItems;
	
	public String getEntity() {
		return entity;
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}
	public String getXlsFile() {
		return xlsFile;
	}
	public void setXlsFile(String tableCarrier) {
		this.xlsFile = tableCarrier;
	}
	public MajorSectionConfiger getMajorSection() {
		return majorSection;
	}
	public void setMajorSection(MajorSectionConfiger majorSection) {
		this.majorSection = majorSection;
	}
	public List<SpecItemConfiger> getSpecItems() {
		return specItems;
	}
	public void setSpecItems(List<SpecItemConfiger> specItems) {
		this.specItems = specItems;
	}
}
