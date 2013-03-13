package net.hogerheijde.nodeping.domain;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Check {

	@JsonProperty(value = "_id")
	private String id;
	
	@JsonProperty(value = "_rev")
	private String rev;
	
	@JsonProperty(value = "customer_id")
	private String customerId;
	
	@JsonProperty
	private String label;
	
	@JsonProperty
	private Integer interval;
	
	@JsonProperty
	private List<Map<String, String>> notifications;
	
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

	@JsonProperty
	private Boolean suspacct;
	
	public String getId() {
		return id;
	}

	public String getRev() {
		return rev;
	}

	public String getCustomerId() {
		return customerId;
	}

	public String getLabel() {
		return label;
	}

	public Integer getInterval() {
		return interval;
	}

	public List<Map<String, String>> getNotifications() {
		return notifications;
	}

	public Type getType() {
		return type;
	}

	public String getStatus() {
		return status;
	}

	public String getModified() {
		return modified;
	}

	public String getEnable() {
		return enable;
	}

	public Boolean isPublic() {
		return isPublic;
	}

	public Parameters getParameters() {
		return parameters;
	}

	public String getCreated() {
		return created;
	}

	public String getQueue() {
		return queue;
	}

	public String getUuid() {
		return uuid;
	}
	
	
}
