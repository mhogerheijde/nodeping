package net.hogerheijde.nodeping.api.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Check {

	@JsonProperty(value = "_id")
	private String id;
	
	@JsonProperty(value = "_rev")
	private String rev;
	
	@JsonProperty
	private String customer_id;
	
	@JsonProperty
	private String label;
	
	@JsonProperty
	private Integer interval;
	
	@JsonProperty
	private List<String> notifications;
	
	@JsonProperty
	private Type type;
	
	@JsonProperty
	private String status;
	
	@JsonProperty
	private String modified; // Timestamp
	
	@JsonProperty
	private String enable;
	
	@JsonProperty(value = "public")
	private Boolean isPublic;
	
	@JsonProperty
	private Parameters parameters;
	
	@JsonProperty
	private String created; //Timestamp
	
	@JsonProperty
	private String queue;
	
	@JsonProperty
	private String uuid;
	
	
}
