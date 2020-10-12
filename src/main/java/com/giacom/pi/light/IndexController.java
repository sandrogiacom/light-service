package com.giacom.pi.light;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    private final LightService lightService;

    public IndexController(LightService lightService) {
        this.lightService = lightService;
    }

    @GetMapping
    public String makeTheLightOn(Model model) {
        LocalDate dt = LocalDate.now();
        FormDTO dto = new FormDTO();
        dto.setStartDate(dt.plusDays(1));
        dto.setEndDate(dt.plusDays(1));
        dto.setStartTime(LocalTime.of(0, 30));
        dto.setEndTime(LocalTime.of(05, 30));
        dto.setSchedules(lightService.getSchedules());
        model.addAttribute("dto", dto);
        return "index";
    }

}
