package com.stockmarket.authentication.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CommonResponse {

	@JsonProperty(value = "status")
	private Boolean status = false;
	@JsonProperty(value = "message")
	private String message;

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
