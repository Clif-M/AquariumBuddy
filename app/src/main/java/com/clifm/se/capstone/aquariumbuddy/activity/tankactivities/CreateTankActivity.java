package com.clifm.se.capstone.aquariumbuddy.activity.tankactivities;

import com.clifm.se.capstone.aquariumbuddy.activity.requests.tankrequest.CreateTankRequest;
import com.clifm.se.capstone.aquariumbuddy.activity.results.tankresults.CreateTankResult;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.TankDao;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Tank;

import java.util.UUID;
import javax.inject.Inject;

/**
 * Implementation of the CreateTankActivity for the Tank endpoint.
 *
 * This API allows the customer to interact with Tank objects in the database.
 */
public class CreateTankActivity {
    private final TankDao tankDao;

    /**
     * Instantiates a new CreateTankActivity object.
     *
     * @param tankDao TankDao to access the tank table.
     */
    @Inject
    public CreateTankActivity(TankDao tankDao) {
        this.tankDao = tankDao;
    }

    /**
     * This method handles the incoming request by creating a tank in the database.
     * <p>
     * It then returns the tank.
     *
     * @param createTankRequest request object containing the userEmail and tankId
     * @return GetTankResult result object containing the API defined
     * {@link Tank}
     */

    public CreateTankResult handleRequest(final CreateTankRequest createTankRequest) {
        Tank tank = createTankRequest.getTank();
        tank.setUserEmail(createTankRequest.getUserEmail());
        tank.setTankId(UUID.randomUUID().toString());
        tankDao.writeTank(tank);
        return CreateTankResult.builder()
                .withTank(tank)
                .build();
    }
}
