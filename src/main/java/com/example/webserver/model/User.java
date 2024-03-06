package com.example.webserver.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class User {
    
    @NotNull
    @NotBlank(message = "please provide name")
    public String firstname;
    
    @NotBlank(message = "please provide name")
    public String lastname;
}
