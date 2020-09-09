package com.giacom.pi.light;

import java.time.ZoneId;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LightController {

    private final LightService lightService;

    public LightController(LightService lightService) {
        this.lightService = lightService;
    }

    @GetMapping("/lighton")
    public String makeTheLightOn() {
        lightService.makeTheLightOn();
        return "redirect:/";
    }

    @GetMapping("/lightoff")
    public String makeTheLightOff() {
        lightService.makeTheLightOff();
        return "redirect:/";
    }

    @PostMapping("/lights")
    public String makeTheLightSchedulle(FormDTO dto) {
        Date dateStart = Date.from(dto.getStartDate().atZone(ZoneId.systemDefault()).toInstant());
        Date dateEnd = Date.from(dto.getEndDate().atZone(ZoneId.systemDefault()).toInstant());
        lightService.schedule(dateStart, dateEnd);
        return "redirect:/";
    }

}
