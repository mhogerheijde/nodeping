package net.hogerheijde.nodeping.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Parameters {
//	": {
//	  "target": "http://www.example.com/",
//	  "threshold": "5",
//	  "sens": "2"
//	},
	
	@JsonProperty
	String target;
	
	@JsonProperty
	Integer threshold;
	
	@JsonProperty
	Integer sens;
}
