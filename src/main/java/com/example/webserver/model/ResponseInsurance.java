package com.example.webserver.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ResponseInsurance {

    @NotNull
    @Valid
    public HeaderData headerData;

    @NotNull
    @Valid
    public ResponseRecord responseRecord;

    @NotNull
    @Valid
    public ResponseStatus responseStatus;
    
    public static class HeaderData {

        @NotBlank(message = "Please provide messageId")
        @Size(max = 50)
        public String messageId;
    
        @NotNull
        @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
        public LocalDateTime sentDateTime;

        @NotNull
        @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
        public LocalDateTime responseDateTime;
    }

    // object type in policy array
    public static class PolicyItem {

        @NotBlank(message = "Not Null")
        @Size(min = 6, max = 6)
        public String policyNo;

        @NotBlank(message = "Please provide status")
        @Size(max = 1)
        public String status;

        @NotBlank(message = "Please provide agenID")
        @Size(min = 8, max = 8)
        public String agenID;
    }

    public static class ResponseRecord {

        public List<PolicyItem> policy;

        @NotBlank(message = "Not Null")
        @Size(min = 6, max = 6)
        public String policyNo;

        @Size(min = 2, max = 2)
        public String policyType;

        @NotBlank(message = "Not Null")
        @Size(max = 1)
        public String status;

        @NotBlank(message = "Not Null")
        @Size(min = 8, max = 8)
        public String agenID;
    }

    public static class ResponseStatus {

        @NotBlank(message = "Not Null")
        @Size(max = 1)
        public String status;

        @Size(max = 30)
        public String errorCode;

        @Size(max = 200)
        public String errorMessage;
    }

    public static ResponseInsurance validatResponseInsurance(ResponseInsurance insurance) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<ResponseInsurance>> violations = validator.validate(insurance);

        if (!violations.isEmpty()) {

            String violationMessages = violations.stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.joining(", "));

            insurance.responseStatus.status = "E";
            insurance.responseStatus.errorCode = "500";
            insurance.responseStatus.errorMessage = "The response is not properly construct: " + violationMessages;
        }

        return insurance;
    }

    public static ResponseInsurance findInsurance(RequestInsurance requestInsurance) {

        // Data Example
        ResponseInsurance insurance = new ResponseInsurance();

        insurance.headerData = new HeaderData();
        insurance.headerData.messageId = requestInsurance.headerData.messageId;
        insurance.headerData.sentDateTime = requestInsurance.headerData.sentDateTime;
        insurance.headerData.responseDateTime = LocalDateTime.now();

        insurance.responseRecord = new ResponseRecord();

        // object in policy array example
        PolicyItem policyItem = new PolicyItem();
        policyItem.policyNo = "P00031";
        policyItem.status = "A";
        policyItem.agenID = "00000005";

        List<PolicyItem> policyList = new ArrayList<>();
        policyList.add(policyItem);

        insurance.responseRecord.policy = policyList;
        insurance.responseRecord.policyNo = "P00031";
        insurance.responseRecord.policyType = "CL";
        insurance.responseRecord.status = "A";
        insurance.responseRecord.agenID = "00000005";

        insurance.responseStatus = new ResponseStatus();
        insurance.responseStatus.status = "S";
        insurance.responseStatus.errorCode = "";
        insurance.responseStatus.errorMessage = "";

        return validatResponseInsurance(insurance);
    }
}
