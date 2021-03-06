package com.wipro.techbank.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_transaction")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Transaction implements Serializable {
    private static final long serialVersionUID = 6891423659802525008L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, columnDefinition = "datetime")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private Operation operation;

    @Column(nullable = false)
    private Double value;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Account account;

    @Column(nullable = false)
    private AccountType accountType;

}
