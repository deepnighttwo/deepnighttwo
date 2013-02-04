package com.snda.mzang.tvtogether.exceptions;

public class InvalidatedClientDataException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidatedClientDataException() {
		super();
	}

	public InvalidatedClientDataException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public InvalidatedClientDataException(String detailMessage) {
		super(detailMessage);
	}

	public InvalidatedClientDataException(Throwable throwable) {
		super(throwable);
	}

}
