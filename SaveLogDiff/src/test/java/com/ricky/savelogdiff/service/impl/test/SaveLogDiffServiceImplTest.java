package com.ricky.savelogdiff.service.impl.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ricky.savelogdiff.domain.EVENT_STATE;
import com.ricky.savelogdiff.domain.LogEvent;
import com.ricky.savelogdiff.service.SaveLogDiffService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class SaveLogDiffServiceImplTest {
	
    @Autowired
    private SaveLogDiffService saveLogDiffServiceImpl;

	private static final SecureRandom randomNumber = new SecureRandom();


    @Test
    public void calculateLogDiff_test() {
    	List<LogEvent> events = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            String id = getRandomId();
            long timestamp = System.currentTimeMillis();
            LogEvent startEvent = new LogEvent();
            startEvent.setId(id);
            startEvent.setState(EVENT_STATE.STARTED);
            startEvent.setTimestamp(timestamp);

            LogEvent endEvent = new LogEvent();
            endEvent.setId(id);
            endEvent.setState(EVENT_STATE.FINISHED);
            endEvent.setTimestamp((long) (timestamp + Math.random() * System.nanoTime() % 10));

            events.add(startEvent);
            events.add(endEvent);
        }
        writeToFile("C:\\New folder\\abc.txt", events);
        int alertCount = saveLogDiffServiceImpl.calculateLogDiff("C:\\New folder\\abc.txt");
        assertEquals(5, alertCount);
    }
    
    private static void writeToFile(String path, List<LogEvent> events) {
        System.out.println(">>> Writing to " + path);
        File file = null;
        FileOutputStream fileOutputStream = null;
        try {
            file = new File(path);
            file.createNewFile();
            fileOutputStream = new FileOutputStream(file);
            for (LogEvent e : events) {
                fileOutputStream.write(e.toString().getBytes(StandardCharsets.UTF_8));
                fileOutputStream.write("\n".getBytes(StandardCharsets.UTF_8));
            }
            System.out.println("Size: " + Math.round(file.length()/1024.0) + "KB");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    private String getRandomId(){
		int maxRange = 2147483647;
		int startRange = 1000000000;
		/**
		 * Random number generated
		 */
		int id = randomNumber.nextInt(maxRange - startRange) + startRange;
		return String.valueOf(id);
	}
}