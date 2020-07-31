package com.interview.mastercard.connectedcities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ConnectedCitiesApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void checkIfConnectedYes() {
		ResponseEntity<String> responseEntity = restTemplate.getForEntity("/connected?origin=New York&destination=Newark", String.class);

		assert responseEntity.getStatusCode() == (HttpStatus.OK);
		assert responseEntity.getBody().equals("yes");
	}

	@Test
	public void checkIfConnectedNo() {
		ResponseEntity<String> responseEntity = restTemplate.getForEntity("/connected?origin=New York&destination=random", String.class);

		assert responseEntity.getStatusCode() == (HttpStatus.OK);
		assert responseEntity.getBody().equals("no");
	}

}
