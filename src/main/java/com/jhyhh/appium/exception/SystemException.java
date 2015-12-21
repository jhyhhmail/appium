package com.jhyhh.appium.exception;

public class SystemException extends BaseException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3701902413200653935L;

	/**
	 * 构造函数 传入异常信息，设置默认的异常级别
	 */
	public SystemException(String message) {
		super(message);
	}

	/**
	 * 构造函数 传入异常信息和异常级别
	 */
	public SystemException(String message, int level) {
		super(message, level);
	}

	/**
	 * 构造函数 传入异常信息和异常类的实例，同时设置默认的异常级别
	 */
	public SystemException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 构造函数
	 */
	public SystemException() {

	}
}