package cc.xushuli.tableConfigurationParser.entity;

import java.util.ArrayList;

public class ColumnConfiger {
	private String id;
	private int colIndex;
	private String filters;
	private String defaultValue;
	private String defaultTo;
	private ArrayList<MatchConfiger> matches = new ArrayList<MatchConfiger>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getColIndex() {
		return colIndex;
	}

	public void setColIndex(int colIndex) {
		this.colIndex = colIndex;
	}

	public String getFilters() {
		return filters;
	}

	public void setFilters(String filters) {
		this.filters = filters;
	}

	public ArrayList<MatchConfiger> getMatches() {
		return matches;
	}

	public void setMatches(ArrayList<MatchConfiger> matches) {
		this.matches = matches;
	}

	public void setColIndex(String attr) {
		if (attr == null || attr.isEmpty()) {
			throw new RuntimeException("colIndex 不能为空");
		}else{
			this.setColIndex(Integer.parseInt(attr));
		}
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getDefaultTo() {
		return defaultTo;
	}

	public void setDefaultTo(String defaultTo) {
		this.defaultTo = defaultTo;
	}

}