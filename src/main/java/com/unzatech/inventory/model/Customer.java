package com.unzatech.inventory.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unzatech.inventory.enums.DocumentType;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "customer")
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private DocumentType documentType;
    private String document;
    private String address;
    private String email;
    private String phone;
}
