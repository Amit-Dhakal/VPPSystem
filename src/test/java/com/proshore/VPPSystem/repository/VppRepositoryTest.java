package com.proshore.VPPSystem.repository;

import com.proshore.VPPSystem.entity.PowerCell;
import net.bytebuddy.utility.dispatcher.JavaDispatcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


@DataJpaTest
@Testcontainers
//@SpringBootTest
class VppRepositoryTest {

    @Autowired
    VppRepository vppRepository;

//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        mockMvc= MockMvcBuilders.standaloneSetup(VppRepositoryTest.class).build();
//    }

    @Container
    private static final MySQLContainer mysqlContainer  = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("testcontainerdb")
            .withUsername("testuser")
            .withPassword("testpass")
            .withEnv("MYSQL_ROOT_PASSWORD","testpass");

    @DynamicPropertySource
public static void databaseProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mysqlContainer::getUsername);
        registry.add("spring.datasource.password", mysqlContainer::getPassword);
        registry.add("spring.datasource.driver-class-name", mysqlContainer::getDriverClassName);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");}


    @Test
    void findByPostcodeBetween() {
        String startRange="5000";
        String endRange="9000";

        PowerCell powerCell1=new PowerCell();
        powerCell1.setPostcode("6000");
        vppRepository.save(powerCell1);

        PowerCell powerCell2=new PowerCell();
        powerCell2.setPostcode("8000");
       vppRepository.save(powerCell2);
       List<String> expectedPostCodes=List.of("6000","8000");
//        Mockito.when(vppRepository.findByPostcodeBetween(Integer.parseInt(startRange),Integer.parseInt(endRange))).thenReturn(mockResult);
        List<PowerCell> actualResult=vppRepository.findByPostcodeBetween(Integer.parseInt(startRange),Integer.parseInt(endRange));
        List<String> actualPostCodes=actualResult.stream().map(PowerCell::getPostcode).collect(Collectors.toList());
        assertEquals(expectedPostCodes,actualPostCodes);
    }

    @Test
    void findByPostCodeAndCapacityRange() {

        String startRange="5000";
        String endRange="9000";
        double capacitymin=2000;
        double capacitymax=10000;

        PowerCell powerCell1=new PowerCell();
        powerCell1.setPostcode("6000");
        powerCell1.setCapacity(2500.0);
        vppRepository.save(powerCell1);

        PowerCell powerCell2=new PowerCell();
        powerCell2.setPostcode("8000");
        powerCell2.setCapacity(9500.0);
        vppRepository.save(powerCell2);

        List<String> expectedpostcodes=List.of("6000","8000");
        List<Double> expectedCapacities=List.of(2500.0,9500.0);

        List<PowerCell> result=vppRepository.findByPostCodeAndCapacityRange(Integer.parseInt(startRange),Integer.parseInt(endRange),capacitymin,capacitymax);

        List<String> actualPostCodes=result.stream().map(PowerCell::getPostcode).collect(Collectors.toList());
        List<Double> actualCapacities=result.stream().map(PowerCell::getCapacity).collect(Collectors.toList());

        assertEquals(expectedpostcodes,actualPostCodes);
        assertEquals(expectedCapacities,actualCapacities);

    }
}