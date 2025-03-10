package org.nexus.nexkartbackend.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "countries")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 45)
    private String name;

    @Column(nullable = false, length = 5)
    private String code;

    @OneToMany(mappedBy = "country" , fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<State> states;

    public Country() {

    }

    public Country(Integer id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }


    public Country(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public Country(String name) {
        this.name = name;
    }


    public Country(Integer countryId) {
        this.id = countryId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<State> getStates() {
        return states;
    }

    public void setStates(Set<State> states) {
        this.states = states;
    }

    @Override
    public String toString() {
        return "Country [id=" + id + ", name=" + name + ", code=" + code + "]";
    }



}