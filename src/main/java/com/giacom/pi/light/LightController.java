package com.giacom.pi.light;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

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
                Calendar calendarStart = Calendar.getInstance(TimeZone.getDefault());
                calendarStart.set(
                        dto.getStartDate().getYear(),
                        dto.getStartDate().getMonthValue() - 1,
                        dto.getStartDate().getDayOfMonth(),
                        dto.getStartTime().getHour(),
                        dto.getStartTime().getMinute()
                );
                Date dateStart = calendarStart.getTime();

                Calendar calendarEnd = Calendar.getInstance(TimeZone.getDefault());
                calendarEnd.set(
                        dto.getEndDate().getYear(),
                        dto.getEndDate().getMonthValue() - 1,
                        dto.getEndDate().getDayOfMonth(),
                        dto.getEndTime().getHour(),
                        dto.getEndTime().getMinute()
                );
                Date dateEnd = calendarEnd.getTime();
                lightService.schedule(dateStart, dateEnd);
                break;
            case "cancel":
                lightService.cancelAllSchedule();
                break;
        }
        return "redirect:/";
    }

}
