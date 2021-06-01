package com.ian.tools.enums;

public enum AcnoTypeEnum {

	ACNO("臺幣存款帳號", new String[] { "01", "02", "03", "07" }, ""), CHECK_ACNO("支存帳號", new String[] { "01" }, ""),
	OUT_ACNO("臺幣轉出帳號", new String[] { "01", "02", "03", "07" }, "Y"),
	GOLD_ACNO("黃金存摺帳號", new String[] { "09", "07" }, ""),
	FX_OUT_ACNO("外幣轉出帳號/手續費帳號", new String[] { "01", "02", "03", "04", "05", "07" }, "Y"),
	FX_OUT_DEP_ACNO("外幣定存轉出帳號", new String[] { "04", "05", "07" }, "Y"),
	INWARD_REMITTANC("匯入匯款查詢帳號", new String[] { "01", "02", "03", "04", "05", "06", "07", "08" }, "Y"),
	PURCHASE_FUND_DETAILS("輕鬆理財自動申購基金明細", new String[] { "07" }, ""),
	BOND_BOLANCE("中央登錄債券餘額", new String[] { "08" }, ""), MANAGEMENT("輕鬆理財戶帳號", new String[] { "07" }, ""),
	COLLATER("綜合存款帳戶", new String[] { "02", "07" }, ""),
	ACCSET("常用帳戶", new String[] { "01", "02", "03", "04", "05", "06", "07", "08" });

	String description;
	String[] acnotype;
	String trflag;

	private AcnoTypeEnum(String description, String[] acnotype, String trflag) {
		this.description = description;
		this.acnotype = acnotype;
		this.trflag = trflag;
	}

	private AcnoTypeEnum(String description, String[] acnotype) {
		this.description = description;
		this.acnotype = acnotype;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String[] getAcnotype() {
		return acnotype;
	}

	public void setAcnotype(String[] acnotype) {
		this.acnotype = acnotype;
	}

	public String getTrflag() {
		return trflag;
	}

	public void setTrflag(String trflag) {
		this.trflag = trflag;
	}
}
