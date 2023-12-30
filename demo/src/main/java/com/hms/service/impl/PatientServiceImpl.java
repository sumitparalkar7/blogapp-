package com.hms.service.impl;

import com.hms.entity.Appointment;
import com.hms.entity.Billing;
import com.hms.entity.MedicalHistory;
import com.hms.entity.Patient;
import com.hms.payload.PatientDTO;
import com.hms.repository.AppointmentRepository;
import com.hms.repository.BillingRepository;
import com.hms.repository.MedicalHistoryRepository;
import com.hms.repository.PatientRepository;
import com.hms.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;
    private final MedicalHistoryRepository medicalHistoryRepository;
    private final AppointmentRepository appointmentRepository;
    private final BillingRepository billingRepository;
    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository,MedicalHistoryRepository
                          medicalHistoryRepository,AppointmentRepository appointmentRepository,
                          BillingRepository billingRepository){
        this.patientRepository = patientRepository;
        this.medicalHistoryRepository = medicalHistoryRepository;
        this.appointmentRepository = appointmentRepository;
        this.billingRepository = billingRepository;
    }

    public Patient createPatient(PatientDTO patientDTO) {
        Patient patient = new Patient();
        patient.setName(patientDTO.getName());
        patient.setDateOfBirth(patientDTO.getDateOfBirth());
        patient.setGender(patientDTO.getGender());
        patient = patientRepository.save(patient);

        MedicalHistory medicalHistory = new MedicalHistory();
        medicalHistory.setPatient(patient);
        medicalHistory.setAllergies(patientDTO.getMedicalHistory().getAllergies());
        medicalHistory.setPreviousIllnesses(patientDTO.getMedicalHistory().getPreviousIllness());
        medicalHistory.setCurrentMedications(patientDTO.getMedicalHistory().getCurrentMedications());
        medicalHistoryRepository.save(medicalHistory);

        Patient finalPatient = patient;
        patientDTO.getAppointment().forEach(appointmentDTO -> {
            Appointment appointment = new Appointment();
            appointment.setPatient(finalPatient);
            appointment.setAppointmentDate(appointmentDTO.getAppointmentDate());
            appointment.setReasonForVisit(appointmentDTO.getReasonForVisit());
            appointmentRepository.save(appointment);
        });

        Billing billing = new Billing();
        billing.setPatient(patient);
        billing.setInsuranceProvider(patientDTO.getBilling().getInsuranceProvider());
        billing.setPolicyNumber(patientDTO.getBilling().getPolicyNumber());
        billing.setTotalAmount(patientDTO.getBilling().getTotalAmount());
        billingRepository.save(billing);

        return finalPatient;


    }
}
