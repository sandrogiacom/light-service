package com.giacom.pi.light;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

@Service
public class LightService {

    private TaskScheduler scheduler;

    public LightService(TaskScheduler scheduler) {
        this.scheduler = scheduler;
    }

    public String makeTheLightOn() {
        System.out.println("LightService.makeTheLightOn");
        return executePython("light-on.py");
    }

    public String makeTheLightOff() {
        System.out.println("LightService.makeTheLightOff");
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
        System.out.println("LightService.schedule \n");
        scheduleOn(start);
        scheduleOff(stop);
    }

    private void scheduleOn(Date start) {
        System.out.println("LightService.scheduleOn " + start);
        scheduler.schedule(() -> makeTheLightOn()
                , start);
    }

    public void scheduleOff(Date stop) {
        System.out.println("LightService.scheduleOff " + stop);
        scheduler.schedule(() -> makeTheLightOff()
                , stop);
    }

    private List<String> readProcessOutput(InputStream inputStream) throws IOException {
        try (BufferedReader output = new BufferedReader(new InputStreamReader(inputStream))) {
            return output.lines()
                    .collect(Collectors.toList());
        }
    }

}
