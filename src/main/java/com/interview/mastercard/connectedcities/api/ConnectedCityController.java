package com.interview.mastercard.connectedcities.api;

import com.interview.mastercard.connectedcities.service.ConnectedCityService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/connected")
public class ConnectedCityController {

	private ConnectedCityService connectedCityService;

	ConnectedCityController(ConnectedCityService connectedCityService) {
		this.connectedCityService = connectedCityService;
	}

	@GetMapping
	@ApiOperation(value = "checkRouteExistsBetween", nickname = "ConnectedCities",
					notes = "Provide origin and destination cities to check if they are connected", response = String.class)
	public String checkRouteExistsBetweenCities(
			@ApiParam(name = "origin", value = "provide origin city name", required = true) @RequestParam String origin,
			@ApiParam(name = "destination", value = "provide destination city name", required = true) @RequestParam String destination) {

		return connectedCityService.routeExistsOrNot(origin, destination);
	}

}