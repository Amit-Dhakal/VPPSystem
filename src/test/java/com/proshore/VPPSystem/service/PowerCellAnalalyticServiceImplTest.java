package com.proshore.VPPSystem.service;

import com.proshore.VPPSystem.converter.Converter;
import com.proshore.VPPSystem.dto.PowerCellDTO;
import com.proshore.VPPSystem.dto.PowerCellResponse;
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
class PowerCellAnalalyticServiceImplTest {

    @InjectMocks
    PowerCellAnalalyticServiceImpl powerCellAnalalyticServiceImpl;

    @Mock
    VppServiceImpl vppService;

    @Mock
    Converter converter;

    MockMvc mockMvc;

    @Mock
    PowerCellResponse powerCellResponse;
    @Mock
    VppRepository vppRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc= MockMvcBuilders.standaloneSetup(vppService).build();

    }

    @Test
    void getStatistics() {

        String startRange = "2000";
        String endRange = "7000";

        PowerCell cell1 = new PowerCell();
        cell1.setCapacity(1000);

        PowerCell cell2 = new PowerCell();
        cell2.setCapacity(3000);

        PowerCellDTO celldto1 = new PowerCellDTO();
        celldto1.setCapacity(cell1.getCapacity());
        PowerCellDTO celldto2 = new PowerCellDTO();
        celldto2.setCapacity(cell2.getCapacity());

        Mockito.when(vppRepository.findByPostcodeBetweenRange(2000,7000))
                .thenReturn(List.of(cell1,cell2));

        Mockito.when(converter.entityToDTOConverter(cell1)).thenReturn(celldto1);
        Mockito.when(converter.entityToDTOConverter(cell2)).thenReturn(celldto2);

        PowerCellResponse response = powerCellAnalalyticServiceImpl.getStatistics(startRange,endRange);

        assertEquals(4000, response.getTotalCapacities());
        assertEquals(2000, response.getAverageCapacities());

    }
}