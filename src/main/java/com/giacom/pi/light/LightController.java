package com.giacom.pi.light;

import java.time.ZoneId;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @PostMapping(value = "/schedule")
    public String scheduleOn(FormDTO dto, @RequestParam(value = "action") String action) {
        switch (action) {
            case "schedule":
                Date dateStart = Date.from(dto.getStartDate().atZone(ZoneId.systemDefault()).toInstant());
                Date dateEnd = Date.from(dto.getEndDate().atZone(ZoneId.systemDefault()).toInstant());
                lightService.schedule(dateStart, dateEnd);
                break;
            case "cancel":
                lightService.cancelAllSchedule();
                break;
        }
        return "redirect:/";
    }

}
