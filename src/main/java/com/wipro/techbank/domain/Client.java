package com.wipro.techbank.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "tb_client")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Client implements Serializable {
    private static final long serialVersionUID = 5373459523319006941L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String cpf;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String email;
}
