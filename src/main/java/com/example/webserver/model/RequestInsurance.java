package com.example.webserver.model;
import com.fasterxml.jackson.annotation.JsonFormat;


import java.time.LocalDateTime;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RequestInsurance {

    @NotNull
    @Valid
    public HeaderData headerData;

    @NotNull
    @Valid
    public RequestRecord requestRecord;

    public class HeaderData {

        @NotBlank(message = "Please provide messageId")
        @Size(max = 50)
        public String messageId;
    
        @NotNull
        @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
        public LocalDateTime sentDateTime;
    }
    
    public class RequestRecord {

        @NotBlank(message = "Please provide insureName")
        @Size(max = 100)    
        public String insureName;
    }
}