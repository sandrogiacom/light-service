package com.giacom.pi.light;

import java.time.LocalDateTime;
import java.util.List;

public class FormDTO {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<String> schedules;

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public List<String> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<String> schedules) {
        this.schedules = schedules;
    }
}
