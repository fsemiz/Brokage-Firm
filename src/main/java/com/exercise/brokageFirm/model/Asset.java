package com.exercise.brokageFirm.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "assets")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    Long customerId;

    @Column(nullable = false)
    String assetName; //TRY is also included

    @Column(nullable = false)
    Integer size;

    @Column(nullable = false)
    Integer usableSize;
}
