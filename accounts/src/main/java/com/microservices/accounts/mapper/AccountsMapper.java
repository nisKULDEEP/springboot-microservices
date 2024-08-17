package com.microservices.accounts.mapper;

import com.microservices.accounts.dtos.AccountsRequestDTO;
import com.microservices.accounts.dtos.AccountsResponseDTO;
import com.microservices.accounts.models.Accounts;

public class AccountsMapper {
    public static AccountsResponseDTO mapToAccountsResponseDto(Accounts accounts, AccountsResponseDTO accountsDto) {
        accountsDto.setAccountNumber(accounts.getAccountNumber());
        accountsDto.setAccountType(accounts.getAccountType());
        accountsDto.setBranchAddress(accounts.getBranchAddress());
        return accountsDto;
    }

    public static Accounts mapToAccounts(AccountsRequestDTO accountsDto, Accounts accounts) {
        accounts.setAccountNumber(accountsDto.getAccountNumber());
        accounts.setAccountType(accountsDto.getAccountType());
        accounts.setBranchAddress(accountsDto.getBranchAddress());
        return accounts;
    }

}
