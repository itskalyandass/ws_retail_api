package com.atgcorp.retail.service;

import com.atgcorp.retail.model.dto.*;
import com.atgcorp.retail.repository.CustomerRepository;
import com.atgcorp.retail.util.CustomMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * RewardService
 * 
 * @author Kalyan Dass
 *
 */
@Service
@Slf4j
public class RewardService {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	CustomMapper customMapper;

	public List<CustomerReward> getAllRewardsPerCustomer(Optional<Integer> monthDuration) {
		monthDuration.ifPresentOrElse(
				period -> log.info("In RewardService getAllRewardsPerCustomer for {} duration", period),
				() -> log.info("In RewardService getAllRewardsPerCustomer"));
		var customers = customerRepository.findAll();
		log.info("In RewardService - DB call successful");
		var customerDTOs = customMapper.toCustomerDTOS(customers);
		var customerRewards = customMapper.toCustomerRewards(customerDTOs);
		var customerRewardMap = customerRewards.stream()
				.collect(Collectors.toMap(CustomerReward::getId, Function.identity()));
		customerDTOs.stream().filter(Objects::nonNull)
				.filter(customer -> !CollectionUtils.isEmpty(customer.getPurchases()))
				.forEach(customer -> buildCustomerReward(customerRewardMap, customer, monthDuration));
		var result = new ArrayList<>(customerRewardMap.values());
		result.forEach(customerReward -> {
			if (ObjectUtils.isEmpty(customerReward.getReward())) {
				var reward = new Reward();
				reward.setTotal(0);
				customerReward.setReward(reward);
			}
		});
		return result;
	}

	private Map<Long, CustomerReward> buildCustomerReward(Map<Long, CustomerReward> customerRewardMap,
			Customer customer, Optional<Integer> monthDuration) {
		var reward = new Reward();
		reward.setTotal(0);
		reward.setRewardByMonths(calculateRewards(customer.getPurchases(), reward, monthDuration));
		customerRewardMap.get(customer.getId()).setReward(reward);
		return customerRewardMap;
	}

	private List<RewardByMonth> calculateRewards(List<Purchase> purchases, Reward reward,
			Optional<Integer> monthDuration) {

		List<Purchase> filteredPurchases = new ArrayList<Purchase>();

		monthDuration.ifPresentOrElse(period -> {
			filteredPurchases.addAll(purchases.stream()
					.filter(purchase -> purchase.getCreateTimestamp()
							.isAfter(OffsetDateTime.now().withDayOfMonth(1).minusMonths(period)))
					.collect(Collectors.toList()));
		}, () -> filteredPurchases.addAll(purchases));

		var purchasesByMonth = filteredPurchases.stream().collect(Collectors.groupingBy(
				purchase -> purchase.getCreateTimestamp().getMonth() + "-" + purchase.getCreateTimestamp().getYear(),
				Collectors.toList()));

		return purchasesByMonth.entrySet().stream()
				.map(purchaseMonthList -> calculateRewardByMonth(purchaseMonthList.getKey(),
						purchaseMonthList.getValue(), reward))
				.collect(Collectors.toList());
	}

	private RewardByMonth calculateRewardByMonth(String month, List<Purchase> purchases, Reward reward) {
		var rewardByMonth = (Integer) purchases
				.stream().map(
						purchase -> purchase.getAmount().intValue() > 50
								? (purchase.getAmount().intValue() > 100
										? (purchase.getAmount().intValue() - 50)
												+ (purchase.getAmount().intValue() - 100)
										: (purchase.getAmount().intValue() - 50))
								: 0)
				.mapToInt(Integer::intValue).sum();
		reward.setTotal(reward.getTotal() + rewardByMonth);
		return new RewardByMonth(month, rewardByMonth);
	}
}
