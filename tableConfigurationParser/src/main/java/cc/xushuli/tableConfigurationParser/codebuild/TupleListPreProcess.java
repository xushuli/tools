package cc.xushuli.tableConfigurationParser.codebuild;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cc.xushuli.tableConfigurationParser.entity.ColumnConfiger;
import cc.xushuli.tableConfigurationParser.entity.MajorSectionConfiger;
import cc.xushuli.tableConfigurationParser.entity.MatchConfiger;
import cc.xushuli.tableConfigurationParser.entity.SpecItemConfiger;
import cc.xushuli.tableConfigurationParser.entity.TableConfiger;
import cc.xushuli.tableConfigurationParser.prototype.MethodMeta;
import cc.xushuli.tableConfigurationParser.prototype.TableCarrier;
import cc.xushuli.tableConfigurationParser.util.Filters;
import cc.xushuli.tableConfigurationParser.util.ReflectUtil;
import cc.xushuli.tableConfigurationParser.util.StringUtil;
import cc.xushuli.tableConfigurationParser.util.TemplateUtil;

public class TupleListPreProcess {
	private Class<?> tupleClazz = null;
	private TableCarrier tableCarrier = null;
	private TableConfiger tableConfiger = null;
	List<List<String>> sheetValues = null;
	private ArrayList<Object> tupleList = new ArrayList<Object>();
	private HashMap<String, String> specItemMap = new HashMap<String, String>();

	public TupleListPreProcess(String xml) {
		tableConfiger = ConfigLoader.loadTable(xml);
		try {
			tupleClazz = Class.forName(tableConfiger.getEntity());
			tableCarrier = new TableCarrier(tableConfiger.getXlsFile());
			sheetValues = tableCarrier.getSheetValues();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public ArrayList<Object> getTupleList() {
		return tupleList;
	}

	public HashMap<String, String> getSpecItemMap() {
		return specItemMap;
	}
	
	private Object newTuple() {
		Object tuple = null;
		try {
			tuple = tupleClazz.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tuple;
	}
	
	private void giveValueToColumn(String value, ColumnConfiger toColumn,
			ArrayList<String> tempRow) {
		tempRow.set(toColumn.getColIndex(), value);
		System.out.printf("give %s to %s in the row %d\n", value,
				toColumn.getId(), tempRow);
	}

	private void giveColumnToColumn(ColumnConfiger fromColumn,
			ColumnConfiger toColumn, ArrayList<String> tempRow) {
		String value = tempRow.get(fromColumn.getColIndex());
		giveValueToColumn(value, toColumn, tempRow);
	}
	
	
	/**
	 * 按照配置规则返回默认值，没有规则则换回原值
	 * 
	 * @param c
	 *            列配置对象
	 * @param colValue
	 *            该单元格的值
	 * @param i
	 *            行索引
	 * @param j
	 *            列索引
	 * @return
	 */
	private String processDefault(ColumnConfiger c, String colValue, int i,
			int j) {
		String tempColValue;
		int maxI = sheetValues.size();
		if (!c.getDefaultTo().isEmpty() && i + 1 < maxI) {
			if (c.getDefaultTo().matches("next\\(.*?\\)")) {
				MethodMeta methodMeta = new MethodMeta(c.getDefaultTo());
				String nextRegx = methodMeta.getParamsArr()[0];
				String nextColValue = sheetValues.get(i + 1).get(j);
				if (nextColValue.matches(nextRegx)) {
					tempColValue = nextColValue;
					System.out.println("default to next:" + tempColValue
							+ String.format("  i:%d,j:%d", i, j));
					return tempColValue;
				}

			}
		}
		if (!c.getDefaultValue().isEmpty()) {
			tempColValue = c.getDefaultValue();
			// System.out.println("default to value:"+tempColValue+
			// String.format("  i:%d,j:%d", i,j));
			return tempColValue;
		}
		return colValue;
	}

	private String processMatcher(ColumnConfiger c, boolean[] isToSkip,
			String tempColValue, ArrayList<String> tempRow) {
		String beforeMathchColValue = tempColValue;
		// 处理匹配器
		for (MatchConfiger match : c.getMatches()) {
			Matcher m = Pattern.compile(match.getPattern()).matcher(beforeMathchColValue);
			String matchTo = match.getMatchTo();

			if (m.find()) {
				if (matchTo.equals("skipLine")) {
					isToSkip[0] = true;
				} else if (matchTo.equals("reverseSkip")) {
					isToSkip[0] = false;
				} else if (matchTo.matches("(^(give).*?To.*);?.*")) {
					// 传值语法
					for (String singelGiveStmt : matchTo.split(";")) {
						String[] columnsTemp = singelGiveStmt.substring(4)
								.split("[tT]o");
						if (columnsTemp[0].matches("'.*'")) {
							// 给一列赋值
							String value = StringUtil.findEmbracedBy(
									columnsTemp[0], "'", "'");
							ColumnConfiger toColumn = tableConfiger
									.getMajorSection().getColumnById(
											columnsTemp[1]);
							giveValueToColumn(value, toColumn, tempRow);
						} else {
							// 两列直接传值
							ColumnConfiger fromColumn = tableConfiger
									.getMajorSection().getColumnById(
											columnsTemp[0]);
							ColumnConfiger toColumn = tableConfiger
									.getMajorSection().getColumnById(
											columnsTemp[1]);
							giveColumnToColumn(fromColumn, toColumn, tempRow);
						}

						// 更改临时单元格值
						tempColValue = tempRow.get(c.getColIndex());
					}
				} else if (matchTo.matches("[a-zA-z]+\\(.*")) {
					System.out
							.println(String
									.format("invoke func, origin value is:"
											+ beforeMathchColValue));
					for (String stringMethod : matchTo.split("\\|")) {
						try {
							tempColValue = ReflectUtil.invokeStringUtilMethod(
									stringMethod, tempColValue);
							System.out.println(String.format(
									"invoke:%s, result is %s:", stringMethod,
									tempColValue));
						} catch (SecurityException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (NoSuchMethodException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
		return tempColValue;
	}

	/**
	 * 处理过滤器
	 * @param filters
	 * @param isColBlank
	 * @param tempColValue
	 * @return
	 */
	private String processFilter(String filters, Boolean isColBlank,
			String tempColValue) {
		
		// 以管道命令的格式执行filter
		for (String filter : filters.split("\\|")) {
			if (filter.isEmpty() || isColBlank) {
				return tempColValue;
			}
			System.out.print("filter:" + filter + "\t\t");
			System.out.print("value:" + tempColValue + "\t\t");

			try {
				Method filterMethod = Filters.class.getMethod(
						filter + "Filter", new Class[] { String.class });
				tempColValue = (String) filterMethod.invoke(null, tempColValue);
				System.out.println("filtered value:" + tempColValue);
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return tempColValue;
	}


	public HashMap<String,String> scanSpecItems() {
		for (SpecItemConfiger specItem : tableConfiger.getSpecItems()) {
			int rowIndex = specItem.getRowIndex();
			int colIndex = specItem.getColIndex();
			
			String tempColValue = null;

			//如果行列索引都是-1则取表明，否则取那行那列
			if (colIndex == -1 && rowIndex == -1) {
				tempColValue = tableConfiger.getXlsFile();
			} else {
				tempColValue = sheetValues.get(rowIndex).get(colIndex);
			}

			//处理匹配器
			tempColValue = processMatcher(specItem, new boolean[] { false },
					tempColValue, null);
			//处理过滤器
			tempColValue = processFilter(specItem.getFilters(), false,
					tempColValue);
			//把特定单元格的值放入特定项目Map
			getSpecItemMap().put(specItem.getId(), tempColValue);
		}
		return getSpecItemMap();
	}

	public  ArrayList<Object> scanMajorSection() {
		int startRow = tableConfiger.getMajorSection().getStartIndex();
		int i = -1;
		for (List<String> row : sheetValues) {
			if (++i < startRow) {
				continue;
			}

			// 初始化tuple对象
			Object tuple = this.newTuple();
			ArrayList<String> tempRow = (ArrayList<String>) ((ArrayList<String>) row)
					.clone();

			// 循环控制变量
			int j = -1;
			boolean[] isToSkipTheRow = new boolean[] { false };
			// 开始遍历每一列
			for (ColumnConfiger c : tableConfiger.getMajorSection()
					.getColumnProcessers()) {
				j++;

				// 初始化列相关变量
				String originColValue = tempRow.get(c.getColIndex());
				String tempColValue = originColValue;
				Boolean isOriginColBlank = originColValue.isEmpty()
						|| originColValue.matches("\\s+");

				// 处理默认值,给予原始值
				if (isOriginColBlank) {
					tempColValue = processDefault(c, originColValue, i, j);
				}

				// 处理匹配器
				tempColValue = processMatcher(c, isToSkipTheRow, tempColValue,
						tempRow);

				// 如果符合skip匹配器，针对特定值跳过处理
				if (isToSkipTheRow[0]) {
					isToSkipTheRow[0] = false;
					tuple = null;
					System.out.println(String.format("skip in the col:%s",
							tempColValue));
					break;
				}

				// 处理过滤器
				tempColValue = processFilter(c.getFilters(), isOriginColBlank,
						tempColValue);

				// 给entity填充特定属性值
				ReflectUtil.setMethod(c.getId(), tempColValue, tupleClazz,
						tuple);
			}

			// 向元组列表中加入填入完成的tuple
			if (tuple != null) {
				getTupleList().add(tuple);
			}
		}
		
		return getTupleList();
	}

	
	public static void main(String[] args) {
		
	}

}
