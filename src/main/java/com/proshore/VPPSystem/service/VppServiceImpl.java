package com.proshore.VPPSystem.service;

import com.proshore.VPPSystem.converter.Converter;
import com.proshore.VPPSystem.dto.PowerCellDTO;
import com.proshore.VPPSystem.entity.PowerCell;
import com.proshore.VPPSystem.repository.VppRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
public class VppServiceImpl implements VppService {
    private VppRepository vppRepository;
    private Converter converter;
    PowerCell powerCell = new PowerCell();
    private static final Integer THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

    private static Logger logger = LoggerFactory.getLogger(VppServiceImpl.class);

    @Autowired
    public VppServiceImpl(VppRepository vppRepository, Converter converter) {
        this.vppRepository = vppRepository;
        this.converter = converter;
    }

    @Override
    public void savePowerCell(List<PowerCellDTO> powerCellDTOList) {

        try {
            List<Future<?>> futuresList = new ArrayList<>();
            for (PowerCellDTO powerCellDTO : powerCellDTOList) {
                futuresList.add(executorService.submit(() -> {
                    powerCell = converter.dtoToEntityConverter(powerCellDTO);
                    vppRepository.save(powerCell);
                    logger.info("Data Saved {} Successfully with ID:" + powerCell.getId());
                }));
            }

            for (Future<?> f : futuresList) {
                f.get();
                logger.info("Currently Running Thread {} :" + Thread.currentThread().getName());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            executorService.shutdown();
        }
    }

    @Override
    public List<PowerCellDTO> findPowerCellByPostCodeRange(String startRange, String endRange) {
        try {
            Integer startRangeData = Integer.parseInt(startRange);
            Integer endRangeData = Integer.parseInt(endRange);
            List<PowerCell> powerCellList = vppRepository.findByPostcodeBetweenRange(startRangeData, endRangeData);
            System.out.println(powerCellList);
            List<PowerCellDTO> powerCellDTOList = powerCellList.stream().map(converter::entityToDTOConverter).collect(Collectors.toList());
            return powerCellDTOList;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid post code range :", e);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to fetch power cell list :", e);
        }
    }

    @Override
    public List<PowerCellDTO> findByPostCodeAndCapacityRange(String startRange, String endRange, double mincapacity, double maxcapacity) {

        try {
            Integer startRangeData = Integer.parseInt(startRange);
            Integer endRangeData = Integer.parseInt(endRange);
            List<PowerCell> powerCellList = vppRepository.findByPostCodeAndCapacityRange(startRangeData, endRangeData, mincapacity, maxcapacity);
            List<PowerCellDTO> powerCellDTOList = powerCellList.stream().map(converter::entityToDTOConverter).collect(Collectors.toList());
            return powerCellDTOList;

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid postcode range: startRange and endRange must be numeric.",e);
        }

        catch (Exception e) {
            throw new RuntimeException("Runtime Exception has occured ::"+e);
        }

    }
}
