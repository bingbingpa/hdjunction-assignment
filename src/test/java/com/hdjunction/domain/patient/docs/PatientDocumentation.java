package com.hdjunction.domain.patient.docs;

import com.hdjunction.domain.code.CodeGroup;
import com.hdjunction.domain.patient.PatientSearchType;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.restdocs.request.PathParametersSnippet;
import org.springframework.restdocs.request.RequestParametersSnippet;

import java.util.Arrays;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

public class PatientDocumentation {

    private PatientDocumentation() {

    }

    public static RequestFieldsSnippet createPatientRequest() {
        return relaxedRequestFields(
                fieldWithPath("hospitalId").description("병원 아이디"),
                fieldWithPath("name").description("환자명"),
                fieldWithPath("gender").description("성별코드. " + CodeGroup.GENDER.getCodeGroup()),
                fieldWithPath("dateOfBirth").description("생년월일: yyyymmdd 형태로 입력"),
                fieldWithPath("phone").description("휴대전화번호: '-' 없이 숫자로 입력")
        );
    }

    public static RequestFieldsSnippet updatePatientRequest() {
        return relaxedRequestFields(
                fieldWithPath("name").description("환자명"),
                fieldWithPath("gender").description("성별코드. " + CodeGroup.GENDER.getCodeGroup()),
                fieldWithPath("dateOfBirth").description("생년월일"),
                fieldWithPath("phone").description("휴대전화번호")
        );
    }

    public static ResponseFieldsSnippet findPatientResponse() {
        return relaxedResponseFields(
                fieldWithPath("payload.id").description("환자 아이디"),
                fieldWithPath("payload.hospitalId").description("병원 아이디"),
                fieldWithPath("payload.name").description("환자명"),
                fieldWithPath("payload.registrationNo").description("환자등록번호"),
                fieldWithPath("payload.gender").description("성별코드. " + CodeGroup.GENDER.getCodeGroup()),
                fieldWithPath("payload.dateOfBirth").description("생년월일"),
                fieldWithPath("payload.phone").description("휴대전화번호"),
                fieldWithPath("payload.visits").description("환자 방문 이력")
        );
    }

    public static ResponseFieldsSnippet searchPatientResponse() {
        return relaxedResponseFields(
                fieldWithPath("payload.content[0].id").description("환자 아이디"),
                fieldWithPath("payload.content[0].hospitalId").description("병원 아이디"),
                fieldWithPath("payload.content[0].name").description("환자명"),
                fieldWithPath("payload.content[0].registrationNo").description("환자등록번호"),
                fieldWithPath("payload.content[0].gender").description("성별코드. " + CodeGroup.GENDER.getCodeGroup()),
                fieldWithPath("payload.content[0].dateOfBirth").description("생년월일"),
                fieldWithPath("payload.content[0].phone").description("휴대전화번호"),
                fieldWithPath("payload.content[0].receptionDate").description("최근 병원 방문 일자"),
                fieldWithPath("payload.pageable").description("페이지 정보")
        );
    }

    public static RequestParametersSnippet searchPatientRequest() {
        return requestParameters(
                parameterWithName("type").description("검색 조건 타입. " + Arrays.toString(PatientSearchType.values())),
                parameterWithName("value").description("검색 값"),
                parameterWithName("page").description("페이지 번호"),
                parameterWithName("size").description("한 페이지의 게시물 건수")
        );
    }

    public static PathParametersSnippet pathParamPatientId() {
        return pathParameters(
                parameterWithName("id").description("환자 아이디")
        );
    }
}
