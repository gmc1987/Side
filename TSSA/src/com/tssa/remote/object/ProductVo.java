/**
 * 
 */
package com.tssa.remote.object;

import java.io.Serializable;

/**
 * @author gmc
 * @see <P>返回手机端产品实体</P>
 * @version 1.0.0
 * Date:2014-1-4
 */
public class ProductVo extends BaseVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProductVo(){
		
	}
	
	public ProductVo(String productId, String productCode, String productName, String imagePath,
			String productTypeId, String productTypeName,  String vendorId, String vendorName, Integer totalNum, Integer residualNum,
			String salePrice, String price, String description, String specialType){
		this.productId = productId;
		this.productCode = productCode;
		this.imagePath = imagePath;
		this.productName = productName;
		this.productTypeId = productTypeId;
		this.productTypeName = productTypeName;
		this.vendorId = vendorId;
		this.vendorName = vendorName;
		this.totalNum = totalNum;
		this.residualNum = residualNum;
		this.salePrice = salePrice;
		this.price = price;
		this.description = description;
		this.imagePath = imagePath;
		this.specialType = specialType;
	}

	/*产品ID*/
	private String productId;
	
	/*产品编码*/
	private String productCode;
	
	/**
	 * 图片路径
	 */
	private String imagePath;
	
	/*产品名称*/
	private String productName;
	
	/*产品类型ID*/
	private String productTypeId;
	
	/*产品类型名称*/
	private String productTypeName;
	
	/*商户ID*/
	private String vendorId;
	
	/*商户名称*/
	private String vendorName;
	
	/*产品总数*/
	private Integer totalNum;
	
	/*产品剩余数量*/
	private Integer residualNum;
	
	/*优惠价*/
	private String salePrice;
	
	/*单价*/
	private String price;
	
	/*产品描述*/
	private String description;
	
	/**
	 * 产品特殊分类
	 */
	private String specialType;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(String productTypeId) {
		this.productTypeId = productTypeId;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public Integer getResidualNum() {
		return residualNum;
	}

	public void setResidualNum(Integer residualNum) {
		this.residualNum = residualNum;
	}

	public String getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSpecialType() {
		return specialType;
	}

	public void setSpecialType(String specialType) {
		this.specialType = specialType;
	}
}
