package com.snda.mzang.tvtogether.exceptions;

public class InvalidatedServerDataException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidatedServerDataException() {
		super();
	}

	public InvalidatedServerDataException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public InvalidatedServerDataException(String detailMessage) {
		super(detailMessage);
	}

	public InvalidatedServerDataException(Throwable throwable) {
		super(throwable);
	}

}
