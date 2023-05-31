package accounts.web;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import rewards.internal.restaurant.RestaurantRepository;

@Component
public class RestaurantHealthCheck implements HealthIndicator {
	
	private final RestaurantRepository restaurantRepository;
	
	public RestaurantHealthCheck(RestaurantRepository restaurantRepository) {
		this.restaurantRepository = restaurantRepository;
	}
	
	@Override
	public Health health() {
		if (this.restaurantRepository.getRestaurantCount()
		                             .equals(0L)) {
			return Health.down()
			             .withDetail("count", 0)
			             .build();
		}
		return Health.up()
		             .build();
	}
}
