package com.unibuc.tripfinity.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@Entity(name = "document")
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "documentId")
public class Document {

    @Id
    @Column(name = "document_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int documentId;

    private String type;

    private String number;

    @ManyToOne
    @JoinColumn(name = "passenger_id", nullable = false)
//    @JsonBackReference(value = "documents")
    @JsonIgnore
    private Passenger passenger;

}
