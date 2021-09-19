import controllers.HealthCheckResource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HealthCheckResourceTest
{
    private static final String HEALTHY = "Up & Running";

    @Test
    void isHealthy() {
        // Arrange
        var healthCheckResource = new HealthCheckResource();

        // Act
        String healthy = healthCheckResource.healthy();

        // Assert
        assertEquals(HEALTHY, healthy);
    }
}
