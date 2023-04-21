package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rewards.RewardNetwork;
import rewards.internal.RewardNetworkImpl;
import rewards.internal.account.AccountRepository;
import rewards.internal.account.JdbcAccountRepository;
import rewards.internal.restaurant.JdbcRestaurantRepository;
import rewards.internal.restaurant.RestaurantRepository;
import rewards.internal.reward.JdbcRewardRepository;
import rewards.internal.reward.RewardRepository;

import javax.sql.DataSource;

@Configuration
public class RewardsConfig {
	
	// Set this by adding a constructor.
	private final DataSource dataSource;
	
	public RewardsConfig(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Bean
	public RewardNetwork rewardNetwork() {
		return new RewardNetworkImpl(accountRepository(), restaurantRepository(), rewardRepository());
	}
	
	@Bean
	public AccountRepository accountRepository() {
		var repository = new JdbcAccountRepository();
		repository.setDataSource(this.dataSource);
		return repository;
		
	}
	
	@Bean
	public RestaurantRepository restaurantRepository() {
		var repository = new JdbcRestaurantRepository();
		repository.setDataSource(this.dataSource);
		return repository;
	}
	
	@Bean
	public RewardRepository rewardRepository() {
		var repository = new JdbcRewardRepository();
		repository.setDataSource(this.dataSource);
		return repository;
	}
}
