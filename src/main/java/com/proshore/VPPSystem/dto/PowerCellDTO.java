package com.proshore.VPPSystem.dto;

public class PowerCellDTO {
    private String name;
    private String postcode;
    private double capacity;

    public String getPostcode() {
        return postcode;
    }
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "PowerCellDTO{" + ", name='" + name + '\'' + ", postcode='" + postcode + '\'' + ", capacity=" + capacity + '}';
    }


}
