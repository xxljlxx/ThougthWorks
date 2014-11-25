package com.yyljlyy.thougthWork;
/**
 * 物品定义
 * @author lee
 *
 */
public class Good {
	private String content;
	private int num; 
	private boolean isImported = false; 
	private String name; 
	private double tax;
	private boolean isTaxAccept = true;
	private double price; 
	private double totalPrice; 
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public boolean isImported() {
		return isImported;
	}
	public void setImported(boolean isImported) {
		this.isImported = isImported;
	}
	public boolean isTaxAccept() {
		return isTaxAccept;
	}
	public void setTaxAccept(boolean isTaxAccept) {
		this.isTaxAccept = isTaxAccept;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price + tax;
	}
	public void setPrice(float price) {
		this.totalPrice = price;
		this.price = price;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}
	/**
	 * 获取税率
	 * @return
	 */
	public double getTax() {
		caclulate();
		return tax;
	}
	public void setTax(float tax) {
		this.tax = tax;
	}
	/**
	 * 税率计算
	 */
	public void caclulate(){
		float taxRatio = 0;
		if(isImported){
			taxRatio+=0.05;
		}
		if(isTaxAccept){
			taxRatio+=0.1;
		}
		tax += price * taxRatio;
		totalPrice += tax;
	}
	
}
