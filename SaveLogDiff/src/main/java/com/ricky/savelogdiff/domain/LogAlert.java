package com.ricky.savelogdiff.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "alert")
public class LogAlert {
	
    @Id
    @JsonProperty("id")
    private String id;

    @JsonProperty("duration")
    private long duration;

    @JsonProperty("type")
    private String type;

    @JsonProperty("host")
    private String host;

    @JsonProperty("alert")
    private Boolean alert;

    public LogAlert() {
    	
    }

    public LogAlert(LogEvent event, long duration) {
        this.id = event.getId();
        this.type = event.getType();
        this.host = event.getHost();
        this.duration = duration;
        this.alert = Boolean.FALSE;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
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

    public Boolean getAlert() {
        return alert;
    }

    public void setAlert(Boolean alert) {
        this.alert = alert;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
