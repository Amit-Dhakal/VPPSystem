package com.proshore.VPPSystem.service;

import com.proshore.VPPSystem.converter.Converter;
import com.proshore.VPPSystem.dto.PowerCellDTO;
import com.proshore.VPPSystem.entity.PowerCell;
import com.proshore.VPPSystem.repository.VppRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VppServiceImplTest {

    @InjectMocks
    VppServiceImpl vppServiceImpl;

    @Mock
    VppRepository vppRepository;

    @Mock
    Converter converter;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc= MockMvcBuilders.standaloneSetup(vppServiceImpl).build();
    }

    @Test
    void savePowerCell() {

        PowerCellDTO powerCellDTO=new PowerCellDTO();
        powerCellDTO.setName("Cannington");
        powerCellDTO.setPostcode("6107");
        powerCellDTO.setCapacity(13500);

        PowerCell powerCell = new PowerCell();
        powerCell.setName("Cannington");
        powerCell.setPostcode("6107");
        powerCell.setCapacity(13500);

        Mockito.when(converter.dtoToEntityConverter(powerCellDTO)).thenReturn(powerCell);
        Mockito.when(vppRepository.save(Mockito.any(PowerCell.class))).thenReturn(powerCell);
        vppServiceImpl.savePowerCell(List.of(powerCellDTO));
        Mockito.verify(vppRepository, Mockito.times(1)).save(powerCell);
    }

    @Test
    void findPowerCellByPostCodeRange() {
        String startRange = "6107";
        String endRange = "6733";

        PowerCell powerCell = new PowerCell();
        powerCell.setName("Cannington");
        powerCell.setPostcode("6107");
        powerCell.setCapacity(13500);

        PowerCellDTO powerCellDTO = new PowerCellDTO();
        powerCellDTO.setName("Cannington");
        powerCellDTO.setPostcode("6107");
        powerCellDTO.setCapacity(13500);

        List<PowerCell> powerCellList = List.of(powerCell);
        List<PowerCellDTO> powerCellDTOList = List.of(powerCellDTO);

        Mockito.when(vppRepository.findByPostcodeBetweenRange(Integer.parseInt(startRange),Integer.parseInt(endRange))).thenReturn(powerCellList);
        Mockito.when(converter.entityToDTOConverter(powerCell)).thenReturn(powerCellDTO);

        List<PowerCellDTO> result = vppServiceImpl.findPowerCellByPostCodeRange(startRange,endRange);

        assertEquals(powerCellDTOList.size(),result.size());
        assertEquals("Cannington",result.get(0).getName());
    }



    @Test
    void findByPostCodeAndCapacityRange() {

        String startRange="3000";
        String endRange="7000";

        double mincapacity=1000;
        double maxcapacity=2000;

        PowerCell powerCell = new PowerCell();
        powerCell.setName("Cannington");
        powerCell.setPostcode("6107");
        powerCell.setCapacity(13500);

        PowerCellDTO powerCellDTO = new PowerCellDTO();
        powerCellDTO.setName("Cannington");
        powerCellDTO.setPostcode("6107");
        powerCellDTO.setCapacity(13500);

        List<PowerCell> powerCellList=List.of(powerCell);
        List<PowerCellDTO> powerCellDTOList=List.of(powerCellDTO);

        Mockito.when(vppRepository.findByPostCodeAndCapacityRange(Integer.parseInt(startRange),Integer.parseInt(endRange),mincapacity,maxcapacity)).thenReturn(powerCellList);

        Mockito.when(converter.entityToDTOConverter(powerCell)).thenReturn(powerCellDTO);

        List<PowerCellDTO> result=vppServiceImpl.findByPostCodeAndCapacityRange(startRange,endRange,mincapacity,maxcapacity);

        assertEquals("Cannington",result.get(0).getName());
        assertEquals(powerCellDTOList.size(),result.size());
    }
}