package com.proshore.VPPSystem.service;

import com.proshore.VPPSystem.converter.Converter;
import com.proshore.VPPSystem.dto.PowerCellDTO;
import com.proshore.VPPSystem.dto.PowerCellResponse;
import com.proshore.VPPSystem.entity.PowerCell;
import com.proshore.VPPSystem.repository.VppRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

@Service
public class PowerCellAnalalyticServiceImpl implements PowerCellAnalalyticService{

    @Autowired
    private VppRepository vppRepository;

    @Autowired
    Converter converter;

    Logger logger= LoggerFactory.getLogger(PowerCellAnalalyticServiceImpl.class);

    @Override
    public PowerCellResponse getStatistics(String startRange, String endRange) {
        PowerCellResponse powerCellResponse=new PowerCellResponse();
        Integer startRangeData=Integer.parseInt(startRange);
        Integer endRangeData=Integer.parseInt(endRange);
        List<PowerCell> powerCellList= vppRepository.findByPostcodeBetweenRange(startRangeData,endRangeData);
        List<PowerCellDTO> powerCellListDTO=powerCellList.stream().map(converter::entityToDTOConverter).collect(Collectors.toList());

        double totalCapacities=powerCellListDTO.stream().mapToDouble(PowerCellDTO::getCapacity).sum();
        OptionalDouble averageCapacities=powerCellListDTO.stream().mapToDouble(PowerCellDTO::getCapacity).average();

        logger.info("TOTAL CAPACITIES :{ }"+totalCapacities);
        logger.info("AVERAGE CAPACITIES :{ }"+averageCapacities);

        powerCellResponse.setTotalCapacities(totalCapacities);
        powerCellResponse.setAverageCapacities(averageCapacities.getAsDouble());

        return powerCellResponse;
    }

}
