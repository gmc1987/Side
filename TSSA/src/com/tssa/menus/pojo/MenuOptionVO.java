/**
 * 
 */
package com.tssa.menus.pojo;

import java.io.Serializable;

import com.tssa.options.pojo.Option;

/**
 * @author gmc
 * @see 菜单权限显示对象
 */
@SuppressWarnings("serial")
public class MenuOptionVO implements Serializable {

	private String mid;
	
	private String menuName;
	
	private boolean menuHasSelect = false;
	
	/**
	 * 新增
	 */
	private Option addOption;
	
	private boolean addOptionHasSelect = false;
	/**
	 * 修改
	 */
	private Option modOption;
	
	private boolean modOptionHasSelect = false;
	
	/**
	 * 删除
	 */
	private Option delOption;
	
	private boolean delOptionHasSelect = false;
	
	/**
	 * 查询
	 */
	private Option findOption;
	
	private boolean findOptionHasSelect = false;
	
	/**
	 * 审核
	 */
	private Option verificationOption;
	
	private boolean verificationOptionHasSelect = false;
	
	/**
	 * 导入
	 */
	private Option inductionOption;
	
	private boolean inductionOptionHasSelect = false;

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public Option getAddOption() {
		return addOption;
	}

	public void setAddOption(Option addOption) {
		this.addOption = addOption;
	}

	public Option getModOption() {
		return modOption;
	}

	public void setModOption(Option modOption) {
		this.modOption = modOption;
	}

	public Option getDelOption() {
		return delOption;
	}

	public void setDelOption(Option delOption) {
		this.delOption = delOption;
	}

	public Option getFindOption() {
		return findOption;
	}

	public void setFindOption(Option findOption) {
		this.findOption = findOption;
	}

	public Option getVerificationOption() {
		return verificationOption;
	}

	public void setVerificationOption(Option verificationOption) {
		this.verificationOption = verificationOption;
	}

	public Option getInductionOption() {
		return inductionOption;
	}

	public void setInductionOption(Option inductionOption) {
		this.inductionOption = inductionOption;
	}

	public boolean isMenuHasSelect() {
		return menuHasSelect;
	}

	public void setMenuHasSelect(boolean menuHasSelect) {
		this.menuHasSelect = menuHasSelect;
	}

	public boolean isAddOptionHasSelect() {
		return addOptionHasSelect;
	}

	public void setAddOptionHasSelect(boolean addOptionHasSelect) {
		this.addOptionHasSelect = addOptionHasSelect;
	}

	public boolean isModOptionHasSelect() {
		return modOptionHasSelect;
	}

	public void setModOptionHasSelect(boolean modOptionHasSelect) {
		this.modOptionHasSelect = modOptionHasSelect;
	}

	public boolean isDelOptionHasSelect() {
		return delOptionHasSelect;
	}

	public void setDelOptionHasSelect(boolean delOptionHasSelect) {
		this.delOptionHasSelect = delOptionHasSelect;
	}

	public boolean isFindOptionHasSelect() {
		return findOptionHasSelect;
	}

	public void setFindOptionHasSelect(boolean findOptionHasSelect) {
		this.findOptionHasSelect = findOptionHasSelect;
	}

	public boolean isVerificationOptionHasSelect() {
		return verificationOptionHasSelect;
	}

	public void setVerificationOptionHasSelect(boolean verificationOptionHasSelect) {
		this.verificationOptionHasSelect = verificationOptionHasSelect;
	}

	public boolean isInductionOptionHasSelect() {
		return inductionOptionHasSelect;
	}

	public void setInductionOptionHasSelect(boolean inductionOptionHasSelect) {
		this.inductionOptionHasSelect = inductionOptionHasSelect;
	}

}
