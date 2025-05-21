package com.proshore.VPPSystem.service;
import com.proshore.VPPSystem.dto.PowerCellResponse;

public interface PowerCellAnalalyticService {
    public PowerCellResponse getStatistics(String startRange,String endRange);
}
