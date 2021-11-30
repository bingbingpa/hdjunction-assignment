package com.hdjunction.domain.patient;

import com.hdjunction.domain.code.Code;
import com.hdjunction.domain.patient.dto.request.CreatePatientRequest;
import com.hdjunction.domain.patient.dto.request.SearchPatientRequest;
import com.hdjunction.domain.patient.dto.request.UpdatePatientRequest;
import com.hdjunction.domain.patient.dto.response.PatientPageResponse;
import com.hdjunction.domain.patient.dto.response.PatientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class PatientService {

    private final PatientRepository patientRepository;

    @Transactional(readOnly = true)
    public PatientResponse findById(Long id) {
        Patient patient = findPatient(id);
        return new PatientResponse(patient);
    }

    @Transactional(readOnly = true)
    public Page<PatientPageResponse> search(SearchPatientRequest request, Pageable pageable) {
        return patientRepository.search(request.getType(), request.getValue(), pageable);
    }

    public Long save(CreatePatientRequest dto) {
        Long maxPatientIdByHospitalId = patientRepository.findMaxPatientIdByHospitalId(dto.getHospitalId());
        Patient entity = dto.toEntity();
        entity.generateRegistrationNumber(String.valueOf(maxPatientIdByHospitalId));
        Patient patient = patientRepository.save(entity);
        return patient.getId();
    }

    public void update(Long id, UpdatePatientRequest dto) {
        Patient patient = findPatient(id);
        patient.update(
                dto.getName(),
                Code.findByCode(dto.getGender()),
                Birthday.from(dto.getBirthDay()),
                Phone.from(dto.getPhone())
        );
    }

    public void delete(Long id) {
        patientRepository.deleteById(id);
    }

    private Patient findPatient(Long id) {
        return patientRepository.findById(id).orElseThrow(() -> new NotFoundPatientException(id));
    }
}


