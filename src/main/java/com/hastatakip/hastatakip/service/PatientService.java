package com.hastatakip.hastatakip.service;

import com.hastatakip.hastatakip.model.Patient;
import com.hastatakip.hastatakip.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }

    public Patient addPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public Patient updatePatient(Long id, Patient patientDetails) {
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isPresent()) {
            Patient updatedPatient = patient.get();
            updatedPatient.setFirstName(patientDetails.getFirstName());
            updatedPatient.setLastName(patientDetails.getLastName());
            updatedPatient.setTc(patientDetails.getTc());
            updatedPatient.setBirthDate(patientDetails.getBirthDate());
            updatedPatient.setAddress(patientDetails.getAddress());
            updatedPatient.setPhoneNumber(patientDetails.getPhoneNumber());
            return patientRepository.save(updatedPatient);
        }
        return null;
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }
}

