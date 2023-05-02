package com.abn.recipe.error.response;

import java.time.LocalDateTime;

public class ErrorResponse {

	private int status;
	private String error;
	private String message;
	private LocalDateTime timeStamp;

	public ErrorResponse(int status, String error, String message, LocalDateTime timeStamp) {
		super();
		this.status = status;
		this.error = error;
		this.message = message;
		this.timeStamp = timeStamp;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}

}
