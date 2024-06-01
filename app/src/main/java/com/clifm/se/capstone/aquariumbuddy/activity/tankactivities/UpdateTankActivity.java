package com.clifm.se.capstone.aquariumbuddy.activity.tankactivities;

import com.clifm.se.capstone.aquariumbuddy.activity.requests.tankrequest.UpdateTankRequest;
import com.clifm.se.capstone.aquariumbuddy.activity.results.tankresults.UpdateTankResult;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.TankDao;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Tank;
import com.clifm.se.capstone.aquariumbuddy.exceptions.TankNotFoundException;

import javax.inject.Inject;

/**
 * Implementation of the UpdateTankActivity for the Tank endpoint.
 *
 * This API allows the customer to interact with Tank objects in the database.
 */
public class UpdateTankActivity {
    private final TankDao tankDao;

    /**
     * Instantiates a new UpdateTankActivity object.
     *
     * @param tankDao TankDao to access the tank table.
     */
    @Inject
    public UpdateTankActivity(TankDao tankDao) {
        this.tankDao = tankDao;
    }

    /**
     * This method handles the incoming request by updating a tank in the database.
     * <p>
     * It then returns the tank.
     *
     * @param updateTankRequest request object containing the tank object
     * @return UpdateTankResult result object containing the API defined
     * {@link Tank}
     */

    public UpdateTankResult handleRequest(final UpdateTankRequest updateTankRequest) {
        Tank tank = updateTankRequest.getTank();
        tank.setUserEmail(updateTankRequest.getUserEmail());
        try {
            tankDao.getSingleTank(tank.getUserEmail(), tank.getTankId());
        } catch (TankNotFoundException e) {
            throw new TankNotFoundException("Tank can not be updated. Tank does not exist in database.");
        }
        tankDao.writeTank(tank);
        return UpdateTankResult.builder()
                .withTank(tank)
                .build();
    }
}
