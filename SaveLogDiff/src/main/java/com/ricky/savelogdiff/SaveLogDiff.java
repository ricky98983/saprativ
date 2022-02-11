package com.ricky.savelogdiff;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ricky.savelogdiff.service.SaveLogDiffService;

@SpringBootApplication
public class SaveLogDiff implements CommandLineRunner {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(SaveLogDiff.class);

    @Autowired
    private SaveLogDiffService saveLogDiffService;

    public static void main(String... args) {
        SpringApplication app = new SpringApplication(SaveLogDiff.class);
        app.run(args);
    }

    @Override
    public void run(String... args) {
    	LOGGER.info("Starting event differences calculation");
        int alertCount = saveLogDiffService.calculateLogDiff(args);
        LOGGER.info("Ending event differences calculation with count : "+ alertCount);
    }
}
