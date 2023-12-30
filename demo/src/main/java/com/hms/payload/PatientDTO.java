package com.hms.payload;

import com.hms.entity.Appointment;
import com.hms.entity.Billing;
import com.hms.entity.MedicalHistory;
import lombok.Data;

import java.util.List;

@Data
public class PatientDTO {
    private Long id;
    private String name;
    private String dateOfBirth;
    private String gender;
    private MedicalHistoryDTO medicalHistory;
    private List<AppointmentDTO> appointment;
    private BillingDTO billing;


}



