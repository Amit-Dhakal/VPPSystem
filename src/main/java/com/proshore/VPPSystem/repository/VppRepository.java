package com.proshore.VPPSystem.repository;

import com.proshore.VPPSystem.entity.PowerCell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 *  Repository for PowerCell entity operations
 */
@Repository
public interface VppRepository extends JpaRepository<PowerCell,Integer> {

    @Query(value = "SELECT * FROM power_cell p WHERE CAST(p.POST_CODE AS SIGNED) BETWEEN :start AND :end", nativeQuery = true)
    public List<PowerCell> findByPostcodeBetweenRange(@Param("start") Integer startRange, @Param("end") Integer endRange);

    @Query(value = "SELECT * FROM power_cell p " + "WHERE CAST(p.POST_CODE AS SIGNED) BETWEEN :start and :end " + "and CELL_CAPACITY>=:mincapacity and CELL_CAPACITY<=:maxcapacity", nativeQuery = true)
    public List<PowerCell> findByPostCodeAndCapacityRange(@Param("start") Integer startRange, @Param("end") Integer endRange,@Param("mincapacity") double mincapacity,@Param("maxcapacity") double maxcapacity);
}
