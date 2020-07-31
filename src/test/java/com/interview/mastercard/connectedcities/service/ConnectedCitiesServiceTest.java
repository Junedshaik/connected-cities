package com.interview.mastercard.connectedcities.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConnectedCitiesServiceTest {

    @Autowired
    ConnectedCityService connectedCityService;

    @Autowired
    ResourceLoader resourceLoader ;

    @Value("classpath:city.txt")
    Resource routesFile;

    @Test
    public void testCityTextFile_RouteExists() {
        try (Stream<String> routeStream = Files.lines(Paths.get(routesFile.getURI()))) {
            String expected_value = "Boston, New York";

            assertTrue(routeStream
                    .filter(route -> route.contentEquals(expected_value))
                    .findFirst().isPresent());

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Test
    public void testCityTextFile_exceptionHandled_gracefully() {
        connectedCityService.routesFile = resourceLoader.getResource("classpath:data.txt");
        connectedCityService.mapCityToItsNeighbours();
    }

    @Test
    public void testCityTextFile_routeDoesNotExist() {
        try (Stream<String> routeStream = Files.lines(Paths.get(routesFile.getURI()))) {
            String nonExisting = "Bostonnn, New York";

            assertFalse(routeStream
                    .filter(route -> route.contentEquals(nonExisting))
                    .findFirst().isPresent());

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Test
    public void testRouteExistsOrNotWhenInvalid_no() {
        String yesOrNo;
        yesOrNo = connectedCityService.routeExistsOrNot("Bostonn", "philly");
        assertNotNull(yesOrNo);
        assertEquals(yesOrNo, "no");
    }

    @Test
    public void testRouteExistsOrNot_yes() {
        String yesOrNo;
        yesOrNo = connectedCityService.routeExistsOrNot("Boston", "Philadelphia");
        assertNotNull(yesOrNo);
        assertEquals(yesOrNo, "yes");
    }

    @Test
    public void testRouteExistsOrNot_no() {
        String yesOrNo;
        yesOrNo = connectedCityService.routeExistsOrNot("Boston", "Albany");
        assertNotNull(yesOrNo);
        assertEquals(yesOrNo, "no");
    }

}
