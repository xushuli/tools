package cc.xushuli.tableConfigurationParser.prototype;

import java.math.BigDecimal;

/**
 * 估值表
 * 
 * @author wh
 *
 */
public class ValuationTable {

	private String dataDt; // 数据日期
	private String orgID; // 机构代码
	private String prodCd;// 产品代码
	private String cntrPty;// 交易对手代码
	private String itemCd;// 科目代码
	private String itemNm;// 科目名称
	private String ccyCd;// 货币代码
	private BigDecimal volume;// 数量
	private BigDecimal pricec;// 成本单价
	private BigDecimal cost;// 成本
	private BigDecimal costToVnp;// 成本占净值比例
	private BigDecimal pricep;// 市价
	private BigDecimal valp;// 市值
	private BigDecimal valToVnp;// 市值占净值比例
	private String ext1;// 扩展字段1
	private String ext2;// 扩展字段2
	private BigDecimal ext3;// 扩展字段3
	private String ext4;// 扩展字段4
	private String ext5;// 扩展字段5
	private String ext6;// 扩展字段6
	private String srcCd;// 数据源代码 1-余额表；2-估值表；3-交易明细表；

	public String getDataDt() {
		return dataDt;
	}

	public void setDataDt(String dataDt) {
		this.dataDt = dataDt;
	}

	public String getOrgID() {
		return orgID;
	}

	public void setOrgID(String orgID) {
		this.orgID = orgID;
	}

	public String getProdCd() {
		return prodCd;
	}

	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}

	public String getCntrPty() {
		return cntrPty;
	}

	public void setCntrPty(String cntrPty) {
		this.cntrPty = cntrPty;
	}

	public String getItemCd() {
		return itemCd;
	}

	public void setItemCd(String itemCd) {
		this.itemCd = itemCd;
	}

	public String getItemNm() {
		return itemNm;
	}

	public void setItemNm(String itemNm) {
		this.itemNm = itemNm;
	}

	public String getCcyCd() {
		return ccyCd;
	}

	public void setCcyCd(String ccyCd) {
		this.ccyCd = ccyCd;
	}

	public BigDecimal getVolume() {
		return volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public void setVolume(String volume) {
		this.volume = new BigDecimal(volume);
	}

	public BigDecimal getPricec() {
		return pricec;
	}

	public void setPricec(BigDecimal pricec) {
		this.pricec = pricec;
	}
	public void setPricec(String pricec) {
		this.pricec = new BigDecimal(pricec);
	}
	
	private void setPric(String pricec) {
		this.pricec = new BigDecimal(pricec);
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public void setCost(String cost) {
		this.cost = new BigDecimal(cost);
	}

	public BigDecimal getCostToVnp() {
		return costToVnp;
	}

	public void setCostToVnp(BigDecimal costToVnp) {
		this.costToVnp = costToVnp;
	}

	public void setCostToVnp(String costToVnp) {
		this.costToVnp = new BigDecimal(costToVnp);
	}

	public BigDecimal getPricep() {
		return pricep;
	}

	public void setPricep(BigDecimal pricep) {
		this.pricep = pricep;
	}

	public void setPricep(String pricep) {
		this.pricep = new BigDecimal(pricep);
	}

	public BigDecimal getValp() {
		return valp;
	}

	public void setValp(BigDecimal valp) {
		this.valp = valp;
	}

	public void setValp(String valp) {
		this.valp = new BigDecimal(valp);
	}

	public BigDecimal getValToVnp() {
		return valToVnp;
	}

	public void setValToVnp(BigDecimal valToVnp) {
		this.valToVnp = valToVnp;
	}

	public void setValToVnp(String valToVnp) {
		this.valToVnp = new BigDecimal(valToVnp);
	}

	public String getExt1() {
		return ext1;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	public String getExt2() {
		return ext2;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	public BigDecimal getExt3() {
		return ext3;
	}

	public void setExt3(BigDecimal ext3) {
		this.ext3 = ext3;
	}

	public void setExt3(String ext3) {
		this.ext3 = new BigDecimal(ext3);
	}

	public String getSrcCd() {
		return srcCd;
	}

	public void setSrcCd(String srcCd) {
		this.srcCd = srcCd;
	}

	public String getExt4() {
		return ext4;
	}

	public void setExt4(String ext4) {
		this.ext4 = ext4;
	}

	public String getExt5() {
		return ext5;
	}

	public void setExt5(String ext5) {
		this.ext5 = ext5;
	}

	public String getExt6() {
		return ext6;
	}

	public void setExt6(String ext6) {
		this.ext6 = ext6;
	}

}
