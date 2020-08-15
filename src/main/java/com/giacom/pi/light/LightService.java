package com.giacom.pi.light;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class LightService {

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

    private List<String> readProcessOutput(InputStream inputStream) throws IOException {
        try (BufferedReader output = new BufferedReader(new InputStreamReader(inputStream))) {
            return output.lines()
                    .collect(Collectors.toList());
        }
    }

}
