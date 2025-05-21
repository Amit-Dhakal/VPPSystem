package com.proshore.VPPSystem.controller;

import com.proshore.VPPSystem.converter.Converter;
import com.proshore.VPPSystem.dto.PowerCellDTO;
import com.proshore.VPPSystem.dto.PowerCellResponse;
import com.proshore.VPPSystem.service.PowerCellAnalalyticService;
import com.proshore.VPPSystem.service.VppService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class VppControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    VppController vppController;

    @Mock
    private Converter converter;

    @Mock
    private VppService vppService;

    @Mock
    private PowerCellAnalalyticService powerCellAnalalyticService;

    @Mock
    PowerCellResponse powerCellResponse;

    //to check actual value vs expected value
    //assertEquals()
    //

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(vppController).build();
    }

    @Test
    void savePowerCellData() {
        PowerCellDTO powerCellDTO = new PowerCellDTO();
        powerCellDTO.setName("Cannington");
        powerCellDTO.setCapacity(13500);
        powerCellDTO.setPostcode("6107");
        ResponseEntity<?> response = null;
        try {
            response = vppController.savePowerCellData(List.of(powerCellDTO));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCodeValue());

    }

    @Test
    void fetchPowerCellData() {

        PowerCellDTO dto1 = new PowerCellDTO();
        dto1.setName("Cannington");
        when(vppService.findPowerCellByPostCodeRange("1000", "2000")).thenReturn(Arrays.asList(dto1));
        ResponseEntity<List<PowerCellDTO>> response = vppController.fetchPowerCellData("1000", "2000");
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Cannington", response.getBody().get(0).getName());
    }


    @Test
    void getAnalyticalData() {
        String startRange = "2000";
        String endRange = "10000";
        PowerCellResponse powerCellResponse = new PowerCellResponse();
        powerCellResponse.setAverageCapacities(2000);
        powerCellResponse.setTotalCapacities(5000);
        when(powerCellAnalalyticService.getStatistics(startRange, endRange)).thenReturn(powerCellResponse);
        ResponseEntity<PowerCellResponse> response = vppController.getAnalyticalData(startRange, endRange);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2000, response.getBody().getAverageCapacities());
        assertEquals(5000, response.getBody().getTotalCapacities());
    }


    @Test
    void fetchPowerCellDataByPostCodeRangeAndCapacityRange() {

        String startRange = "2000";
        String endRange = "10000";
        double mincapacity = 2000;
        double maxcapacity = 5000;

        PowerCellDTO powerCellDTO = new PowerCellDTO();
        powerCellDTO.setCapacity(13500);
        powerCellDTO.setName("Cannington");
        powerCellDTO.setPostcode("6107");

        when(vppService.findByPostCodeAndCapacityRange(startRange, endRange, mincapacity, maxcapacity)).thenReturn(Arrays.asList(powerCellDTO));
        ResponseEntity<List<PowerCellDTO>> response = vppController.fetchPowerCellDataByPostCodeRangeAndCapacityRange(startRange, endRange, mincapacity, maxcapacity);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(13500, response.getBody().get(0).getCapacity());


    }


}