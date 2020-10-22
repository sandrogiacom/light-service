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
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

@Service
public class LightService {

    private final TaskScheduler scheduler;
    private final ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private static List<String> schedules = new ArrayList<>();
    private List<ScheduledFuture> tasks = new ArrayList<>();

    public LightService(TaskScheduler scheduler, ThreadPoolTaskScheduler threadPoolTaskScheduler) {
        this.scheduler = scheduler;
        this.threadPoolTaskScheduler = threadPoolTaskScheduler;
        this.threadPoolTaskScheduler.setRemoveOnCancelPolicy(true);
    }

    public void schedule(Date start, Date stop) {
        schedules = new ArrayList<>();
        scheduleOn(start);
        scheduleOff(stop);
    }

    public String makeTheLightOn() {
        return executePython("light-on.py");
    }

    public String makeTheLightOff() {
        clearSchedules();
        return executePython("light-off.py");
    }

    public void cancelAllSchedule() {
        for (ScheduledFuture task : tasks) {
            task.cancel(true);
        }
        tasks.clear();
        clearSchedules();
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
            System.out.println(e.getMessage());
        }
        return results.toString();
    }

    private void scheduleOn(Date start) {
        ScheduledFuture<?> schedule = scheduler.schedule(() -> makeTheLightOn()
                , start);
        tasks.add(schedule);
        schedules.add(" [ON] " + truncateDateTime(start));
    }

    private void scheduleOff(Date stop) {
        ScheduledFuture<?> schedule = scheduler.schedule(() -> makeTheLightOff()
                , stop);
        tasks.add(schedule);
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

    public List<String> getSchedules() {
        return schedules;
    }

    private void clearSchedules() {
        schedules.clear();
    }

}
