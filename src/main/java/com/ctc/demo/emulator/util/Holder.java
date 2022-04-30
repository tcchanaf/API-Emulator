package com.ctc.demo.emulator.util;

public class  Holder<T> {

	private T content;
	/**
	 * @return the content
	 */
	public T getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public Holder<T> setContent(T content) {
		this.content = content;
		return this;
	}

}
