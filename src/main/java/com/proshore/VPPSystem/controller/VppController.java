package com.proshore.VPPSystem.controller;

import com.proshore.VPPSystem.converter.Converter;
import com.proshore.VPPSystem.dto.PowerCellDTO;
import com.proshore.VPPSystem.dto.PowerCellResponse;
import com.proshore.VPPSystem.service.PowerCellAnalalyticService;
import com.proshore.VPPSystem.service.VppService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/")
public class VppController {

    private Converter converter;
    private VppService vppService;
    private PowerCellAnalalyticService powerCellAnalalyticService;
    private static final int THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private static ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    private Logger logger = LoggerFactory.getLogger(VppController.class);


    @Autowired
    public VppController(VppService vppService, Converter converter, PowerCellAnalalyticService powerCellAnalalyticService) {
        this.vppService = vppService;
        this.converter = converter;
        this.powerCellAnalalyticService = powerCellAnalalyticService;
    }



    /**
     *
     *  Save List of Powercell Data into database
     *
     * @param powerCellDTOList input list containg powercelldata
     * @return ResponseEntity containing HTTPSTATUS created when data inserted succsefully in database
     * Throws exception if data not saved
     *
     */
    @PostMapping("/save-data")
    public ResponseEntity<?> savePowerCellData(@RequestBody @NotNull @NotEmpty@Valid List<PowerCellDTO> powerCellDTOList) {
        try {
            vppService.savePowerCell(powerCellDTOList);
            logger.info("Power Cell saved Sucessfully :{}");
            return new ResponseEntity<>(powerCellDTOList,HttpStatus.CREATED);
        } catch (RuntimeException runtimeException) {
            return new ResponseEntity<>("Runtime Exception occured while saving data :" + runtimeException.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Exception occured while saving data :" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    /**
     *
     *  Fetches powercell data by postcode ranges
     *
     * @param startRange starting postcode range of powercell data
     * @param endRange ending postcode range of powercell data
     * @return ResponseEntity containing by filtering data based on postcode ranges
     * Throws exception if data not found
     */
    @GetMapping("/fetch-data")
    public ResponseEntity<List<PowerCellDTO>> fetchPowerCellData(@RequestParam @NotBlank String startRange,@RequestParam @NotBlank String endRange) {
        try {
            List<PowerCellDTO> powerCellData = vppService.findPowerCellByPostCodeRange(startRange, endRange);
            List<PowerCellDTO> sortedpowerCellData = powerCellData.stream().sorted(Comparator.comparing(PowerCellDTO::getName)).collect(Collectors.toList());
            logger.info("PowerCell Data fetched sucessfully: { }" + sortedpowerCellData);
            return new ResponseEntity<>(sortedpowerCellData, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    /**
    *
     * Fetches data based on total power capacity and average capacity
    * @param startRange starting postcode range of powercell battery
    * @param endRange ending postcode range of powercell battery
    * @return ResponseEntity containing powercell response with analytical data
     * Throws exception if data not found
    */
    @GetMapping("/powercell/analytics")
    public ResponseEntity<PowerCellResponse> getAnalyticalData(@RequestParam @NotBlank String startRange, @RequestParam @NotBlank String endRange) {
        try {
            PowerCellResponse powerCellResponse = powerCellAnalalyticService.getStatistics(startRange,endRange);
            logger.info("Analytical Data fetched sucessfully: { }" + powerCellResponse);
            return new ResponseEntity<PowerCellResponse>(powerCellResponse, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    /**
     *  Fetches data by postcode and capacity range
     *
     * @param startRange starting postcode range of powercell battery
     * @param endRange ending postcode range of powercell battery
     * @param mincapacity  starting capacity range of powercell battery
     * @param maxcapacity ending capacity range of powercell battery
     * @return return response by filtering data based upon postcode and capacity range values
     * Throws exception if data notfound
     */
    @GetMapping("/fetch-data-by-postcode-capacity")
    public ResponseEntity<List<PowerCellDTO>> fetchPowerCellDataByPostCodeRangeAndCapacityRange(@RequestParam @NotBlank String startRange, @RequestParam @NotBlank String endRange, @RequestParam @NotNull Double mincapacity, @RequestParam @NotNull Double maxcapacity) {
        try {
            List<PowerCellDTO> powerCellData = vppService.findByPostCodeAndCapacityRange(startRange, endRange, mincapacity, maxcapacity);
            return new ResponseEntity<>(powerCellData, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

}
