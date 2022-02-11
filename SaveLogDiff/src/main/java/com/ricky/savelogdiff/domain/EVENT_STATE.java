package com.ricky.savelogdiff.domain;

public enum EVENT_STATE {
	
    STARTED("STARTED"),
    FINISHED("FINISHED");

    private final String state;

    private EVENT_STATE(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
    
}
