package com.ja0ck5.cloud.model;

public class BaseView {
    protected int code;
	protected String msg;
	protected int result;
	protected Object data;

	public BaseView() {
		this.result = 1;
	}

	public BaseView(Object data) {
		this();
		this.data = data;
	}

	public BaseView(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	public BaseView(int code, String msg, int result, Object data) {
		this.code = code;
		this.msg = msg;
		this.result = result;
		this.data = data;
	}

	/**
	 * @return the {@link #code}
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the {@link #code} to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return the {@link #msg}
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg
	 *            the {@link #msg} to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @return the {@link #result}
	 */
	public int getResult() {
		return result;
	}

	/**
	 * @return the {@link #data}
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @param data
	 *            the {@link #data} to set
	 */
	public void setData(Object data) {
		this.data = data;
	}

}