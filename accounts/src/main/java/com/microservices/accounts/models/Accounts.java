package com.microservices.accounts.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Accounts extends BaseEntity{

    @Id
    @Column(name="account_number")
    private Long accountNumber;

    @Column(name="customer_id")
    private Long customerId;

    @Column(name="account_type")
    private String accountType;

    private String branchAddress;
}
