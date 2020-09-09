package com.giacom.pi.light;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    private static final long TEN_MINUTES = 10;
    private static final long TWO_MINUTES = 2;
    private final LightService lightService;

    public IndexController(LightService lightService) {
        this.lightService = lightService;
    }

    @GetMapping
    public String makeTheLightOn(Model model) {
        LocalDateTime dt = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        FormDTO dto = new FormDTO();
        dto.setStartDate(dt.plusMinutes(TWO_MINUTES));
        dto.setEndDate(dt.plusMinutes(TEN_MINUTES));
        dto.setSchedules(lightService.getMemory());
        model.addAttribute("dto", dto);
        return "index";
    }

}
