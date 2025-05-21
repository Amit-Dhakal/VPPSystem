package com.proshore.VPPSystem.converter;

import com.proshore.VPPSystem.dto.PowerCellDTO;
import com.proshore.VPPSystem.entity.PowerCell;
import org.springframework.stereotype.Component;

@Component
public class Converter {

    // This method implements conversion of entity data to DTO data
    public PowerCellDTO entityToDTOConverter(PowerCell powerCell){
        PowerCellDTO powerCellDTO=new PowerCellDTO();
        powerCellDTO.setCapacity(powerCell.getCapacity());
        powerCellDTO.setName(powerCell.getName());
        powerCellDTO.setPostcode(powerCell.getPostcode());
        return powerCellDTO;
    }

    //This method implements conversion of DTO data to entity data
    public PowerCell dtoToEntityConverter(PowerCellDTO powerCellDTO){
        PowerCell powerCell=new PowerCell();
        powerCell.setCapacity(powerCellDTO.getCapacity());
        powerCell.setName(powerCellDTO.getName());
        powerCell.setPostcode(powerCellDTO.getPostcode());
        return powerCell;
    }
}
