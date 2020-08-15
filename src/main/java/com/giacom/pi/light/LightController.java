package com.giacom.pi.light;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LightController {

    private final LightService lightService;

    public LightController(LightService lightService) {
        this.lightService = lightService;
    }

    @GetMapping("/lighton")
    public String makeTheLightOn() {
        lightService.makeTheLightOn();
        return "index";
    }

    @GetMapping("/lightoff")
    public String makeTheLightOff() {
        lightService.makeTheLightOff();
        return "index";
    }

}
