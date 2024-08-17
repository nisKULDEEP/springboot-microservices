package com.microservices.accounts.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@Schema(
        name = "Accounts Request Class",
        description = "Schema to hold Account information"
)

public class AccountsRequestDTO {
    @NotEmpty(message = "AccountNumber can not be a null or empty")
    @Pattern(regexp="(^$|[0-9]{10})",message = "AccountNumber must be 10 digits")
    @Schema(
            description = "Account Number of Bank account", example = "3454433243"
    )
    private Long accountNumber;

    @NotEmpty(message = "Customer Id can not be a null or empty")
    private Long customerId;

    @NotEmpty(message = "AccountType can not be a null or empty")
    @Schema(
            description = "Account type of  Bank account", example = "Savings"
    )
    private String accountType;
    @NotEmpty(message = "BranchAddress can not be a null or empty")
    @Schema(
            description = "Bank branch address", example = "123 NewYork"
    )
    private String branchAddress;
}
