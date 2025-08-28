package com.mock.monolithic.company.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "role")
public class Role {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

}