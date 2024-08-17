package com.microservices.accounts.servicesImpl;

import com.microservices.accounts.constants.AccountsConstants;
import com.microservices.accounts.dtos.AccountsRequestDTO;
import com.microservices.accounts.dtos.AccountsResponseDTO;
import com.microservices.accounts.dtos.CustomerRequestDTO;
import com.microservices.accounts.dtos.CustomerResponseDTO;
import com.microservices.accounts.exception.CustomerAlreadyExistException;
import com.microservices.accounts.exception.ResourceNotFoundException;
import com.microservices.accounts.mapper.AccountsMapper;
import com.microservices.accounts.mapper.CustomerMapper;
import com.microservices.accounts.models.Accounts;
import com.microservices.accounts.models.Customer;
import com.microservices.accounts.repositories.AccountsRepo;
import com.microservices.accounts.repositories.CustomerRepo;
import com.microservices.accounts.services.IAccountsService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private AccountsRepo accountsRepo;
    private CustomerRepo customerRepo;

    /**
     *
     * @param customerRequestDTO object
     */
    @Override
    public void createAccount(CustomerRequestDTO customerRequestDTO) {
//        try {
            Customer customer = CustomerMapper.mapToCustomer(customerRequestDTO, new Customer());

            Optional<Customer> customerOptional = customerRepo.findByMobileNumber(customer.getMobileNumber());

            if(customerOptional.isPresent()){
                throw new CustomerAlreadyExistException("Customer already exists with the given mobile number : " + customer.getMobileNumber());
            }

//            customer.setCreatedAt(LocalDateTime.now());
//            customer.setCreatedBy("Anonymous");
            customer = customerRepo.save(customer);
            accountsRepo.save(createNewAccount(customer));
//        } catch (Exception ex) {
//
//        }


    }

    /**
     *
     * @param mobileNumber string
     * @return CustomerResponseDTO
     */
    @Override
    public CustomerResponseDTO fetchAccount(String mobileNumber) {
            Customer customer = customerRepo.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundException("Customer", "mobile number", mobileNumber) );
            Accounts accounts = accountsRepo.findByCustomerId(customer.getCustomerId()).orElseThrow(() -> new ResourceNotFoundException("Account", "mobile number", customer.getCustomerId().toString()) );
            CustomerResponseDTO customerResponseDTO = CustomerMapper.mapToCustomerDto(customer, new CustomerResponseDTO());
            AccountsResponseDTO accountsResponseDTO = AccountsMapper.mapToAccountsResponseDto(accounts, new AccountsResponseDTO());
            customerResponseDTO.setAccountsDTO(accountsResponseDTO);
            return customerResponseDTO;

    }

    /**
     * @param customerRequestDTO - CustomerDto Object
     * @return boolean indicating if the update of Account details is successful or not
     */
    @Override
    public boolean updateAccount(CustomerRequestDTO customerRequestDTO) {
        boolean isUpdated = false;
        AccountsRequestDTO accountsDto = customerRequestDTO.getAccountsDTO();
        if(accountsDto !=null ){
            Accounts accounts = accountsRepo.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
            );
            AccountsMapper.mapToAccounts(accountsDto, accounts);
            accounts = accountsRepo.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepo.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString())
            );
            CustomerMapper.mapToCustomer(customerRequestDTO,customer);
            customerRepo.save(customer);
            isUpdated = true;
        }
        return  isUpdated;
    }

    /**
     * @param mobileNumber - Input Mobile Number
     * @return boolean indicating if the delete of Account details is successful or not
     */
    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepo.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        accountsRepo.deleteByCustomerId(customer.getCustomerId());
        customerRepo.deleteById(customer.getCustomerId());
        return true;
    }

    /**
     * @param customer - Customer Object
     * @return the new account details
     */
    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccount;
    }
}
