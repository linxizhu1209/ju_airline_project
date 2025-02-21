package org.example.paymenttest.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table()
@AllArgsConstructor
@NoArgsConstructor
public class Airport {

    @Id
    @Column(length=10)
    private String airportId;

    @Column(nullable = false, length=100)
    private String name;

    @Column(nullable = false, length=100)
    private String country;

    @Column(nullable = false, length=100)
    private String city;

}
