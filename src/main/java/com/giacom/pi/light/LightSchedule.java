package com.giacom.pi.light;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class LightSchedule {

    private final LightService lightService;

    public LightSchedule(LightService lightService) {
        this.lightService = lightService;
    }

    //1000*60*60*24
    @Scheduled(fixedRate = 86400000, initialDelay = 5000)
    public void executeTask() {
        System.out.println("**********  executeTask ************");
        LocalDate dt = LocalDate.now();
        FormDTO dto = new FormDTO();
        dto.setStartDate(dt.plusDays(1));
        dto.setEndDate(dt.plusDays(1));
        dto.setStartTime(LocalTime.of(0, 30));
        dto.setEndTime(LocalTime.of(05, 00));

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
    }

}
