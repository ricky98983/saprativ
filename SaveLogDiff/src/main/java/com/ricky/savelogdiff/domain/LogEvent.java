package com.ricky.savelogdiff.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LogEvent {
	
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("state")
    private EVENT_STATE state;

    @JsonProperty("type")
    private String type;

    @JsonProperty("host")
    private String host;

    @JsonProperty("timestamp")
    private long timestamp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EVENT_STATE getState() {
        return state;
    }

    public void setState(EVENT_STATE eventState) {
        this.state = eventState;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
