package net.hogerheijde.nodeping.api.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Checks {
	
	@JsonProperty
	List<Check> checks;
	
	public List<Check> getChecks() {
		return checks;
	}
}
