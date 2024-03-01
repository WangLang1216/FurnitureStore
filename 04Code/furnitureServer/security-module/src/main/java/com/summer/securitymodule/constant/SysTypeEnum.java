package com.summer.securitymodule.constant;

/**
 * 系统类型
 * @author WangLang
 */
public enum SysTypeEnum {

	/**
	 * 小程序-普通用户系统
	 */
	APPLET(0),

	/**
	 * 商家端-管理后系统
	 */
	MANAGE(1),
	;

	private final Integer value;

	public Integer value() {
		return value;
	}

	SysTypeEnum(Integer value) {
		this.value = value;
	}

}
