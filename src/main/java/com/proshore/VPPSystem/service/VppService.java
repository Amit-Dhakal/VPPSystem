package com.proshore.VPPSystem.service;

import com.proshore.VPPSystem.dto.PowerCellDTO;
import java.util.List;

public interface VppService {
    public void savePowerCell(List<PowerCellDTO> powerCellDTOList);
    public List<PowerCellDTO> findPowerCellByPostCodeRange( String startRange, String endRange);
 public List<PowerCellDTO> findByPostCodeAndCapacityRange( String startRange, String endRange ,double mincapacity,double maxcapacity);
}
