package com.hms.payload;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentDTO {
    private LocalDateTime appointmentDate;
    private String reasonForVisit;
}
