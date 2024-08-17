package com.microservices.accounts.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Schema(
        name = "Customer",
        description = "Schema to hold Customer and Account information"
)
public class CustomerRequestDTO {

    @NotEmpty(message = "Name can not be null or empty")
    @Size(min=5, max=50, message = "The length of the name must be between 5 and 50 characters")
    @Schema(
            description = "Name of the customer", example = " Bytes"
    )
    private String name;

    @NotEmpty(message ="Please provide email")
    @Email(message="Email enter a valid address")
    @Schema(
            description = "Email address of the customer", example = "kuldeep@gmail.com"
    )
    private String email;

    @Pattern(regexp = "(^[0-9]{10})", message = "Mobile number must be 10 digits")
    @Schema(
            description = "Mobile Number of the customer", example = "7536064444"
    )
    private String mobileNumber;

    @Schema(
            description = "Account details of the Customer"
    )
    private AccountsRequestDTO accountsDTO;
}
