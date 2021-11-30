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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static com.hdjunction.domain.patient.PatientController.API_PATIENTS_PATH;

@RequiredArgsConstructor
@RestController
@RequestMapping(API_PATIENTS_PATH)
public class PatientController {

    public static final String API_PATIENTS_PATH = "/api/patients";

    private final PatientService patientService;

    @GetMapping("/{id}")
    public ResponseEntity<ResultResponse<PatientResponse>> findById(@PathVariable Long id) {
        PatientResponse response = patientService.findById(id);
        return ResponseEntity.ok().body(
                ResultResponse.<PatientResponse>builder()
                        .payload(response)
                        .status(HttpStatus.OK.value())
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<ResultResponse<Page<PatientPageResponse>>> search(@Valid SearchPatientRequest request, Pageable pageable) {
        Page<PatientPageResponse> search = patientService.search(request, pageable);
        return ResponseEntity.ok().body(
                ResultResponse.<Page<PatientPageResponse>>builder()
                        .payload(search)
                        .status(HttpStatus.OK.value())
                        .build()
        );
    }

    @PostMapping
    public ResponseEntity<ResultResponse<Void>> save(@Valid @RequestBody CreatePatientRequest request) {
        Long id = patientService.save(request);
        return ResponseEntity.created(URI.create(API_PATIENTS_PATH + id)).body(
                ResultResponse.<Void>builder()
                        .status(HttpStatus.CREATED.value())
                        .build()
        );

    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody UpdatePatientRequest request) {
        patientService.update(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        patientService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(NotFoundPatientException.class)
    public ResponseEntity<ResultResponse<Void>> notFoundGeoPolicy(Exception e) {
        return ResponseEntity.badRequest().body(
                ResultResponse.<Void>builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .errorMessage(e.getMessage())
                        .build()
        );
    }
}
