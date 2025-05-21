package com.proshore.VPPSystem.dto;
import lombok.Data;

// Holds analytical data like total and average power capacities.
@Data
public class PowerCellResponse {

    private double totalCapacities;
    private double averageCapacities;

    public double getTotalCapacities() {
        return totalCapacities;
    }

    public void setTotalCapacities(double totalCapacities) {
        this.totalCapacities = totalCapacities;
    }

    public double getAverageCapacities() {
        return averageCapacities;
    }

    public void setAverageCapacities(double averageCapacities) {
        this.averageCapacities = averageCapacities;
    }
}
