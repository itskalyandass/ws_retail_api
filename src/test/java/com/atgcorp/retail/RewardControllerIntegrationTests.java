package com.atgcorp.retail;

import com.atgcorp.retail.model.dto.CustomerReward;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = RetailApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
class RewardControllerIntegrationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testAllEmployees() {

		var responseEntity = this.restTemplate.exchange("http://localhost:" + port + "/ws_retail_api/reward/all",
				HttpMethod.GET, null, new ParameterizedTypeReference<List<CustomerReward>>() {
				});
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(6, responseEntity.getBody().size());
		assertEquals(1, responseEntity.getBody().get(0).getId());
		assertEquals("jack", responseEntity.getBody().get(0).getName());
		assertNotNull(responseEntity.getBody().get(0).getReward());
		assertEquals(110, responseEntity.getBody().get(0).getReward().getTotal());
		assertEquals(5, responseEntity.getBody().get(0).getReward().getRewardByMonths().size());
	}

	@Test
	public void testAllEmployeesMonthPeriod_success() {

		var responseEntity = this.restTemplate.exchange(
				"http://localhost:" + port + "/ws_retail_api/reward/all/last/1/months", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<CustomerReward>>() {
				});
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(6, responseEntity.getBody().size());
		assertEquals(1, responseEntity.getBody().get(0).getId());
		assertEquals("jack", responseEntity.getBody().get(0).getName());
		assertNotNull(responseEntity.getBody().get(0).getReward());
		assertEquals(70, responseEntity.getBody().get(0).getReward().getTotal());
		assertEquals(2, responseEntity.getBody().get(0).getReward().getRewardByMonths().size());
	}


	@Test
	public void testAllEmployeesMonthPeriod_fail_scenario_String() {
		var responseEntity = this.restTemplate.exchange(
				"http://localhost:" + port + "/ws_retail_api/reward/all/last/A/months", HttpMethod.GET, null,
				Object.class);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
}
