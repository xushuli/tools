package cc.xushuli.tableConfigurationParser.entity;

import java.util.ArrayList;
import java.util.HashMap;

public class MajorSectionConfiger {
	private Integer startIndex = 0;
	private ArrayList<ColumnConfiger> ColumnProcessers;

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public ArrayList<ColumnConfiger> getColumnProcessers() {
		return ColumnProcessers;
	}

	public void setColumnProcessers(ArrayList<ColumnConfiger> arrayList) {
		ColumnProcessers = arrayList;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getStartIndex()+"";
	}

	public void setStartIndex(String attr) {
		setStartIndex(Integer.parseInt(attr));
		
	}
	
	public ColumnConfiger getColumnById(String id){
		
		for (ColumnConfiger columnConfiger : ColumnProcessers) {
			if(columnConfiger.getId().toUpperCase().equals(id.toUpperCase())){
				return columnConfiger;
			}
		}
		return null;
	}
}
