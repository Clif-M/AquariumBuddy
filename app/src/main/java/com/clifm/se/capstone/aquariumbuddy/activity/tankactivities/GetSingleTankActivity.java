package com.clifm.se.capstone.aquariumbuddy.activity.tankactivities;

import com.clifm.se.capstone.aquariumbuddy.activity.requests.tankrequest.GetSingleTankRequest;
import com.clifm.se.capstone.aquariumbuddy.activity.results.tankresults.GetSingleTankResult;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.TankDao;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Tank;

import javax.inject.Inject;

/**
 * Implementation of the GetSingleTankActivity for the Tank endpoint.
 *
 * This API allows the customer to interact with Tank objects in the database.
 */
public class GetSingleTankActivity {
    private final TankDao tankDao;

    /**
     * Instantiates a new GetSingleTankActivity object.
     *
     * @param tankDao TankDao to access the tank table.
     */
    @Inject
    public GetSingleTankActivity(TankDao tankDao) {
        this.tankDao = tankDao;
    }

    /**
     * This method handles the incoming request by retrieving a tank from the database, if it exists.
     * <p>
     * It then returns the tank.
     * <p>
     * If the tank does not exist on the database, this method will propagate a TankNotFoundException.
     *
     * @param getSingleTankRequest request object containing the userEmail and tankId
     * @return GetTankResult result object containing the API defined
     * {@link Tank}
     */

    public GetSingleTankResult handleRequest(final GetSingleTankRequest getSingleTankRequest) {
        Tank tank = tankDao.getSingleTank(getSingleTankRequest.getUserEmail(), getSingleTankRequest.getTankId());
        return GetSingleTankResult.builder()
                .withTank(tank)
                .build();
    }
}
