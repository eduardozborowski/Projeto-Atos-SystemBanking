package br.com.atm.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long accountNumber;

    private String agencyNumber;
    private Double balance;

    @OneToOne
    private UserAtm userAtm;
}

