package cc.xushuli.tableConfigurationParser.entity;

import java.util.ArrayList;
import java.util.List;

public class SpecItemConfiger extends ColumnConfiger{
	private int rowIndex;
	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	public void setRowIndex(String attr) {
		this.setRowIndex(Integer.valueOf(attr));
	}

}
