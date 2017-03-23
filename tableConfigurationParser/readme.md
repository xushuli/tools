#xls配置化解析器
####使用配置文件描述xls文件里多样的数据格式和字段，在对符合特定格式的字符串进行处理后，自动将每一行变成java实体，最后产生一个java实体类型的Arraylist。
####并且可以解析特定单元格，对它进行某些处理后放到收集特定点的映射表里。

##简要说明

###match节点
####可以放在column和specItme下，主要用于匹配符合特定模式的单元格，对他进行修改和其它操作。


pattern 属性：模式
matchTo 属性：符合模式要去做什么，支持函数和命令，如：giveColumnAToColumnB（如果符合某一模式把某一列columnA的值赋给列colmnB，注意：columnB的列索引不能大于ColumnA的列索引），give'value'ToColumnB（如果符合某一模式把某一个值赋给这一行的某一列），skipLine（跳过这一行不构造这一行的java实体），reverseSkip（取消跳过），find('substring')，substring('start','end')，relace('pattern','replacement')等

###column节点
####说明某列是java实体中的哪个属性，并描述需要进行哪些操作与过滤


id属性：与java实体的属性对应，id值的getter setter需要存在与java实体中。
filters 属性 可用的过滤器有number，percent，date
colInex 属性：excel列索引，从0开始
defaultValue 属性：默认值，注意需要用单引号括起'value'
defaultTo 属性：如果没有内容，则执行命令，可用的命令有next('pattern')取符合模式的下一行作为默认值

###specItem节点
####继承于column节点


rowIndex 属性：excel行索引，从0开始



##配置文件demo
	<?xml version="1.0" encoding="UTF-8"?>
	<table entity="com.guige.oim.entity.ValuationTable"
		xlsFile="C:\\Users\\Tony\\Desktop\\20161230ori\\估值表20161230.xls">
		<majorSection startIndex="5">
			<columns>
				<column id="itemCd" colIndex="0" filters="" defaultTo="next('^\d+.*')">
					<match pattern="^[^0-9^\s]" matchTo="skipLine"></match>
					<match pattern="实收资本\(金额\)|资产类合计:|资产资产净值:|单位净值日增长率:" matchTo="reverseSkip"></match> 
					<match pattern="实收资本\(金额\)|资产类合计:|资产资产净值:|单位净值日增长率:" matchTo="giveItemCdToItemNm"></match> 
					<match pattern="实收资本\(金额\)" matchTo="replace('.+','10012')"></match> 
					<match pattern='\d*[a-zA-Z]\d*' matchTo="replace('[a-zA-Z]','')"></match>
				</column>
				<column id="itemNm" colIndex="1" filters=""></column>
				<column id="volume" colIndex="2" defaultValue="0"></column>
				<column id="pricec" colIndex="3" defaultValue="0"></column>
				<column id="cost" colIndex="4" filters="number" defaultValue="0"></column>
				<column id="costToVnp" colIndex="5" filters="percent"defaultValue="0"></column>
				<column id="pricep" colIndex="7" defaultValue="0"></column>
				<column id="ext4" colIndex="10" filters="tingpai"></column>
			</columns>
		</majorSection>

		<specItems>
			<specItem id="dataDt" colIndex="0" rowIndex="2" filters="date"></specItem>
				<specItem id="abb" colIndex="0" rowIndex="1">
					<match pattern=".*" matchTo="find('__.*__')|substring('2','-2')"></match>
				</specItem>
		</specItems>

	</specItems>
	</table>

