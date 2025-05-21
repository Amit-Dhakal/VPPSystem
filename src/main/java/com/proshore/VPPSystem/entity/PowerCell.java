package com.proshore.VPPSystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Maps the PowerCell entity to the POWER_CELL database table
@Entity
@Table(name = "POWER_CELL")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PowerCell {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "CELL_NAME")
    private String name;
    @Column(name = "POST_CODE")
    private String postcode;
    @Column(name = "CELL_CAPACITY")
    private double capacity;

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

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "PowerCell{" + "id=" + id + ", name='" + name + '\'' + ", postcode='" + postcode + '\'' + ", capacity=" + capacity + '}';
    }
}

