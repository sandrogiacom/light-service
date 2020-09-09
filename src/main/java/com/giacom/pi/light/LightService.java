package com.giacom.pi.light;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

@Service
public class LightService {

    private TaskScheduler scheduler;
    private static List<String> schedules;

    public LightService(TaskScheduler scheduler) {
        this.scheduler = scheduler;
    }

    public String makeTheLightOn() {
        return executePython("light-on.py");
    }

    public String makeTheLightOff() {
        return executePython("light-off.py");
    }

    private String executePython(String name) {
        ProcessBuilder processBuilder = new ProcessBuilder("python", name);
        processBuilder.redirectErrorStream(true);
        Process process;
        List<String> results = Collections.EMPTY_LIST;
        try {
            process = processBuilder.start();
            results = readProcessOutput(process.getInputStream());
            System.out.println(results);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results.toString();
    }

    public void schedule(Date start, Date stop) {
        schedules = new ArrayList<>();
        scheduleOn(start);
        scheduleOff(stop);
    }

    private void scheduleOn(Date start) {
        scheduler.schedule(() -> makeTheLightOn()
                , start);
        schedules.add(" [ON] " + truncateDateTime(start));
    }

    public void scheduleOff(Date stop) {
        scheduler.schedule(() -> makeTheLightOff()
                , stop);
        schedules.add("[OFF] " + truncateDateTime(stop));
    }

    private String truncateDateTime(Date date) {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(date);
    }

    private List<String> readProcessOutput(InputStream inputStream) throws IOException {
        try (BufferedReader output = new BufferedReader(new InputStreamReader(inputStream))) {
            return output.lines()
                    .collect(Collectors.toList());
        }
    }

    public List<String> getMemory() {
        return schedules;
    }

}
