package com.atgcorp.retail.controller;

import com.atgcorp.retail.exception.BadRequestException;
import com.atgcorp.retail.model.dto.CustomerReward;
import com.atgcorp.retail.service.RewardService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

/**
 * 
 * Reward controller
 * 
 * @author Kalyan Dass
 *
 */
@RestController
@RequestMapping("/reward")
@Slf4j
public class RewardController {

	@Autowired
	RewardService rewardService;

	/**
	 * Service will fetch customer total rewards and also monthly rewards
	 * NO-CONTENT/204 - is returned if NO data returned
	 */
	@GetMapping("/all")
	public ResponseEntity<List<CustomerReward>> getAllCustomersWithRewards() {
		log.info("In RewardController getAllCustomersWithRewards ");
		var customerRewards = rewardService.getAllRewardsPerCustomer(Optional.empty());
		if (customerRewards.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(customerRewards, HttpStatus.OK);
	}

	/**
	 * Service will fetch customer total rewards from specified previous duration and also monthly rewards
	 * 
	 * @param duration - duration required to pass 
	 * 
	 * @return NO-CONTENT/204 - is returned if NO data returned
	 * @return BAD_REquest/400 - is returned if invalid argument is passed
	 * 
	 * @exception BadRequestException - Global level exception handling
	 * @exception ResponseStatusException - In method exception handling
	 * 
	 */
	@GetMapping("/all/last/{duration}/months")
	public ResponseEntity<List<CustomerReward>> getAllCustomersWithRewardsInSpecifiedDuration(
			@PathVariable(value = "duration") String duration) {
		try {
			log.info("In RewardController getAllCustomersWithRewardsInSpecifiedDuration for {} duration", duration);
			var durationValue = Integer.valueOf(duration);
			if(durationValue < 1) {
				throw new BadRequestException("Duration provided is not valid: "+duration);
			}
			var customerRewards = rewardService.getAllRewardsPerCustomer(Optional.of(durationValue));
			if (customerRewards.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(customerRewards, HttpStatus.OK);
		} catch (NumberFormatException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please pass valid month duration: " + duration);
		}
	}
}
