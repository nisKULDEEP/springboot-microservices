package com.microservices.accounts.services;

import com.microservices.accounts.dtos.CustomerRequestDTO;
import com.microservices.accounts.dtos.CustomerResponseDTO;

public interface IAccountsService {
    /**
     *
     * @param customerRequestDTO object
     */
    void createAccount(CustomerRequestDTO customerRequestDTO);

    /**
     *
     * @param mobileNumber string
     * @return Account object
     */
    CustomerResponseDTO fetchAccount(String mobileNumber);

    /**
     *
     * @param customerRequestDTO CustomerRequest
     * @return boolean
     */
    boolean updateAccount(CustomerRequestDTO customerRequestDTO);

    /**
     *
     * @param mobileNumber - Input Mobile Number
     * @return boolean indicating if the delete of Account details is successful or not
     */
    boolean deleteAccount(String mobileNumber);

}
