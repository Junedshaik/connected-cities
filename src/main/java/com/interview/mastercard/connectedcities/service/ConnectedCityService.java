package com.interview.mastercard.connectedcities.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ConnectedCityService {

    @Value("classpath:city.txt")
    Resource routesFile;

    Map<String, Set<String>> neighbouringCitiesForCity = new HashMap<>();

    @PostConstruct
    public void mapCityToItsNeighbours() {
        try(Stream<String> routeStream = Files.lines(Paths.get(routesFile.getURI()))) {

            List<String> routes = routeStream.collect(Collectors.toList());

            for(String route : routes) {
                String [] origToDest = route.split(", ");
                String origin = origToDest[0].toLowerCase();
                String destination = origToDest[1].toLowerCase();

                addNeighbouringCities(origin, destination);
                addNeighbouringCities(destination, origin);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String routeExistsOrNot(String origin, String destination) {
        return checkIfReachable(origin.toLowerCase(), destination.toLowerCase()) ? "yes" : "no";
    }

    private boolean checkIfReachable(String origin, String destination) {
       return findAllReachableFromOrigin(origin).containsAll(Arrays.asList(origin, destination));
    }

    private Set<String> findAllReachableFromOrigin(String origin) {
        Set<String> reachableCities = new HashSet<>();
        Set<String> visited = new HashSet<>();
        Stack<String> stack = new Stack<>();

        stack.push(origin);
        visited.add(origin);
        try {	
	        while(!stack.isEmpty()) {
	            String city = stack.pop();
	            reachableCities.add(city);
	            for(String neighbouringCity: neighbouringCitiesForCity.get(city)) {
	                if(!visited.contains(neighbouringCity)) {
	                    stack.push(neighbouringCity);
	                    visited.add(neighbouringCity);
	                }
	            }
	        }
       } catch(Exception e) {
    	   return Collections.emptySet();
       }
        return reachableCities;
    }

    private void addNeighbouringCities(String origin, String destination) {
        Set<String> reachableDestinations;
        if(!neighbouringCitiesForCity.containsKey(origin)) {
            reachableDestinations = new HashSet<>();
            reachableDestinations.add(destination);
            neighbouringCitiesForCity.put(origin, reachableDestinations);
        } else {
            reachableDestinations = neighbouringCitiesForCity.get(origin);
            reachableDestinations.add(destination);
        }
    }
}
