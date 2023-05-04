package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
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
	
	@Bean
	public RewardNetwork rewardNetwork(JdbcTemplate jdbcTemplate) {
		return new RewardNetworkImpl(
				accountRepository(jdbcTemplate),
				restaurantRepository(jdbcTemplate),
				rewardRepository(jdbcTemplate));
	}
	
	@Bean
	public JdbcTemplate jdbcTemplate(@Autowired DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
	
	@Bean
	public AccountRepository accountRepository(JdbcTemplate jdbcTemplate) {
		JdbcAccountRepository repository = new JdbcAccountRepository(jdbcTemplate);
		return repository;
	}
	
	@Bean
	public RestaurantRepository restaurantRepository(JdbcTemplate jdbcTemplate) {
		JdbcRestaurantRepository repository = new JdbcRestaurantRepository(jdbcTemplate);
		return repository;
	}
	
	@Bean
	public RewardRepository rewardRepository(JdbcTemplate jdbcTemplate) {
		JdbcRewardRepository repository = new JdbcRewardRepository(jdbcTemplate);
		return repository;
	}
	
}
