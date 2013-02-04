package com.snda.mzang.tvtogether.exceptions;

public class CommunicationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CommunicationException() {
		super();
	}

	public CommunicationException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public CommunicationException(String detailMessage) {
		super(detailMessage);
	}

	public CommunicationException(Throwable throwable) {
		super(throwable);
	}

}
