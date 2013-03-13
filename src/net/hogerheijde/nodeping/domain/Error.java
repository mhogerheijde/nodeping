package net.hogerheijde.nodeping.domain;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Error {

	@JsonProperty
	String error;
	
	public boolean hasError() {
		return error != null;
	}
	
	public String getError() {
		return error;
	}
}
