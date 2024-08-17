package com.microservices.accounts.controllers;

import com.microservices.accounts.constants.AccountsConstants;
import com.microservices.accounts.dtos.*;
import com.microservices.accounts.services.IAccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(
        name = "CRUD REST APIs for Accounts in Bank",
        description = "CRUD REST APIs in Bank to CREATE, UPDATE, FETCH AND DELETE account details"
)
public class AccountController {

    @Autowired
    private IAccountsService iAccountsService;
    /**
     * @Value("${build.version}") -> you can extract the value from ../resouces/application.yaml file
     *
     */
    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private Environment environment;

    @Autowired
    private AccountContactInfo accountContactInfo;


//    @Autowired
//    public AccountController (IAccountsService iAccountsService){
//        this.iAccountsService = iAccountsService;
//    }

    @Operation(
            summary = "Create Account REST API",
            description = "REST API to create new Customer &  Account inside Bank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    }
    )
    @PostMapping("/account/create")
    public ResponseEntity<ResponseDTO> createAccount(@Valid @RequestBody CustomerRequestDTO customerRequestDto) {
        iAccountsService.createAccount(customerRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));

    }

    @Operation(
            summary = "Fetch Account Details REST API",
            description = "REST API to fetch Customer &  Account details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    }
    )
    @GetMapping("/account/fetch")
    public ResponseEntity<CustomerResponseDTO> fetchAccountDetails(@RequestParam
                                                                       @Pattern(regexp="(^$|[0-9]{10})",message = "AccountNumber must be 10 digits")
                                                                       String mobileNumber) {
        CustomerResponseDTO customerResponseDTO = iAccountsService.fetchAccount(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerResponseDTO);
    }

    @Operation(
            summary = "Update Account Details REST API",
            description = "REST API to update Customer &  Account details based on a account number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    }
    )
    @PutMapping("/account/update")
    public ResponseEntity<ResponseDTO> updateAccount(@Valid @RequestBody CustomerRequestDTO customerRequestDto) {
        boolean isUpdated = iAccountsService.updateAccount(customerRequestDto);
        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        }

        return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(new ResponseDTO(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_UPDATE));


    }

    @Operation(
            summary = "Delete Account & Customer Details REST API",
            description = "REST API to delete Customer &  Account details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    }
    )
    @DeleteMapping("/account/delete")
    public ResponseEntity<ResponseDTO> deleteAccountDetails(@RequestParam
                                                                @Pattern(regexp="(^$|[0-9]{10})",message = "AccountNumber must be 10 digits")
                                                                String mobileNumber) {
        boolean isDeleted = iAccountsService.deleteAccount(mobileNumber);
        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        }
        return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(new ResponseDTO(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_DELETE));
    }

    /**
     * THESE ALL THREE API's ARE TO GET VARIABLES OUTSIDE THE BUILD
     * @return
     */
    @Operation(
            summary = "build version",
            description = "REST API to get the account microservice build version"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    @GetMapping("/account/build-info")
    public ResponseEntity<String> getBuildVersion () {
        return  ResponseEntity.status(HttpStatus.OK).body(buildVersion.toString());
    }

    @Operation(
            summary = "java version",
            description = "REST API to get the account microservice java version"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    @GetMapping("/account/java-version")
    public ResponseEntity<String> getJavaVersion () {
        return  ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("JAVA_HOME"));
    }

    @Operation(
            summary = "get contact information`",
            description = "REST API to get the developer contact information"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    @GetMapping("/account/contact-info")
    public ResponseEntity<AccountContactInfo> getContactInfo () {
        return  ResponseEntity.status(HttpStatus.OK).body(accountContactInfo);
    }


}
