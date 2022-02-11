package com.ricky.savelogdiff.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ricky.savelogdiff.domain.EVENT_STATE;
import com.ricky.savelogdiff.domain.LogAlert;
import com.ricky.savelogdiff.domain.LogEvent;
import com.ricky.savelogdiff.repository.AlertRepository;
import com.ricky.savelogdiff.service.SaveLogDiffService;

@Service
public class SaveLogDiffServiceImpl implements SaveLogDiffService{
   
	private static final Logger LOGGER = LoggerFactory.getLogger(SaveLogDiffServiceImpl.class);

    @Autowired
    private AlertRepository alertRepository;

	@Override
	public int calculateLogDiff(String... args) {
		int totalAlertCount = 0;
		try {
			if (args.length >= 1) {
				String path = args[0];
				File file = new File(path);
				if(file.exists()) {
					ObjectMapper mapper = new ObjectMapper();
					Map<String, LogEvent> events = new HashMap<>();
					Map<String, LogAlert> alerts = new HashMap<>();
					try(LineIterator it = FileUtils.lineIterator(file, "UTF-8")) {
					    while (it.hasNext()) {
					    	String line = it.nextLine();
					    	LogEvent logEvent = mapper.readValue(line, LogEvent.class);
					    	if (events.containsKey(logEvent.getId())) {
					    		LOGGER.debug("Second event : "+line);
					    		long diff = 0l;
					    		LogEvent oldLogEvent = events.get(logEvent.getId());
					    		if(EVENT_STATE.STARTED.equals(oldLogEvent.getState())) {
					    			diff = logEvent.getTimestamp() - oldLogEvent.getTimestamp();
					    		} else {
					    			diff = oldLogEvent.getTimestamp() - logEvent.getTimestamp();
					    		}
					    		LogAlert alert = new LogAlert(logEvent, diff);
					    		if(diff>4l) {
					    			alert.setAlert(Boolean.TRUE);
					    		}
					    		alerts.put(logEvent.getId(), alert);
					    		totalAlertCount++;
					    	} else {
					    		LOGGER.debug("First event : "+line);
					    		events.put(logEvent.getId(), logEvent);
					    	}
					    	if (alerts.size() > 100) {
					    		saveAlertData(alerts);
			                }
					    }
					    if(alerts.size()>0) {
					    	saveAlertData(alerts);
					    }
					} 
				} else {
					LOGGER.error("Could not find any valid log file : "+ path);
				}
			} else {
				LOGGER.error("Please provide valid input");
			}
		} catch(Exception e) {
			LOGGER.error("Exception occured in calculateLogDiff :"+e);
		}
		return totalAlertCount;
	}

	private void saveAlertData(Map<String, LogAlert> alerts) {
		LOGGER.info("Saving data. Count : "+ alerts.size());
		alertRepository.saveAll(alerts.values());
		alerts.clear();
	}
}
