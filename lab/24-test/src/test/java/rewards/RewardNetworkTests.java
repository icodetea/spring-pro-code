package rewards;

import common.money.MonetaryAmount;
import config.RewardsConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;

/**
 A system test that verifies the components of the RewardNetwork application
 work together to reward for dining successfully. Uses Spring to bootstrap the
 application for use in a test environment.
 */

@SpringJUnitConfig
@ActiveProfiles({"jdbc", "local"})
public class RewardNetworkTests {
	
	
	/**
	 The object being tested.
	 */
	@Autowired
	private RewardNetwork rewardNetwork;
	
	/**
	 Need this to enable clean shutdown at the end of the application
	 */
	
	@Test
	@DisplayName("Test if reward computation and distribution works")
	public void testRewardForDining() {
		// create a new dining of 100.00 charged to credit card
		// '1234123412341234' by merchant '123457890' as test input
		Dining dining = Dining.createDining("100.00", "1234123412341234",
				"1234567890");
		
		// call the 'rewardNetwork' to test its rewardAccountFor(Dining) method
		RewardConfirmation confirmation = rewardNetwork
				.rewardAccountFor(dining);
		
		// assert the expected reward confirmation results
		assertNotNull(confirmation);
		assertNotNull(confirmation.getConfirmationNumber());
		
		// assert an account contribution was made
		AccountContribution contribution = confirmation
				.getAccountContribution();
		assertNotNull(contribution);
		
		// the contribution account number should be '123456789'
		assertEquals("123456789", contribution.getAccountNumber());
		
		// the total contribution amount should be 8.00 (8% of 100.00)
		assertEquals(MonetaryAmount.valueOf("8.00"), contribution.getAmount());
		
		// the total contribution amount should have been split into 2
		// distributions
		assertEquals(2, contribution.getDistributions()
		                            .size());
		
		// The total contribution amount should have been split into 2 distributions
		// each distribution should be 4.00 (as both have a 50% allocation).
		// The assertAll() is from JUnit 5 to group related checks together.
		assertAll("distribution of reward",
				() -> assertEquals(2, contribution.getDistributions()
				                                  .size()),
				() -> assertEquals(MonetaryAmount.valueOf("4.00"), contribution.getDistribution("Annabelle")
				                                                               .getAmount()),
				() -> assertEquals(MonetaryAmount.valueOf("4.00"), contribution.getDistribution("Corgan")
				                                                               .getAmount()));
	}
	
	@Configuration
	@Import({
			TestInfrastructureLocalConfig.class,
			TestInfrastructureJndiConfig.class,
			RewardsConfig.class})
	public static class TestConfig {
		/**
		 The bean logging post-processor from the bean lifecycle slides.
		 */
		@Bean
		public static LoggingBeanPostProcessor loggingBean() {
			return new LoggingBeanPostProcessor();
		}
	}
}
