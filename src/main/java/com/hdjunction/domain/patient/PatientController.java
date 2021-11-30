package com.hdjunction.domain.patient;

import com.hdjunction.domain.patient.dto.request.CreatePatientRequest;
import com.hdjunction.domain.patient.dto.request.SearchPatientRequest;
import com.hdjunction.domain.patient.dto.request.UpdatePatientRequest;
import com.hdjunction.domain.patient.dto.response.PatientPageResponse;
import com.hdjunction.domain.patient.dto.response.PatientResponse;
import com.hdjunction.global.response.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;

    @GetMapping("/{id}")
    public ResultResponse<PatientResponse> findById(@PathVariable Long id) {
        PatientResponse response = patientService.findById(id);
        return ResultResponse.<PatientResponse>builder()
                .payload(response)
                .status(HttpStatus.OK.value())
                .build();
    }

    @GetMapping
    public ResultResponse<Page<PatientPageResponse>> search(SearchPatientRequest request, Pageable pageable) {
        Page<PatientPageResponse> search = patientService.search(request, pageable);
        return ResultResponse.<Page<PatientPageResponse>>builder()
                .payload(search)
                .status(HttpStatus.OK.value())
                .build();
    }

    @PostMapping
    public ResultResponse<Void> save(@RequestBody CreatePatientRequest request) {
        patientService.save(request);
        return ResultResponse.<Void>builder()
                .status(HttpStatus.CREATED.value())
                .build();
    }

    @PutMapping("/{id}")
    public ResultResponse<Void> update(@PathVariable Long id, @RequestBody UpdatePatientRequest request) {
        patientService.update(id, request);
        return ResultResponse.<Void>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .build();
    }

    @DeleteMapping("/{id}")
    public ResultResponse<Void> delete(@PathVariable Long id) {
        patientService.delete(id);
        return ResultResponse.<Void>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .build();
    }
}
