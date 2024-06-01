package com.clifm.se.capstone.aquariumbuddy.activity.tankactivities;

import com.clifm.se.capstone.aquariumbuddy.activity.requests.tankrequest.GetTanksRequest;
import com.clifm.se.capstone.aquariumbuddy.activity.results.tankresults.GetTanksResult;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.TankDao;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Tank;

import java.util.List;
import javax.inject.Inject;

/**
 * Implementation of the GetTanksActivity for the Tank endpoint.
 *
 * This API allows the customer to interact with Tank objects in the database.
 */
public class GetTanksActivity {
    private final TankDao tankDao;

    /**
     * Instantiates a new GetTankActivity object.
     *
     * @param tankDao TankDao to access the tank table.
     */
    @Inject
    public GetTanksActivity(TankDao tankDao) {
        this.tankDao = tankDao;
    }

    /**
     * This method handles the incoming request by retrieving a list of tank from the database, if it exists.
     * <p>
     * It then returns the list of tanks.
     * <p>
     * If the user has no tank does not exist on the database, this method will propagate a TankNotFoundException.
     *
     * @param getTanksRequest request object containing the userEmail and tankId
     * @return GetTankResult result object containing the API defined
     * {@link Tank}
     */

    public GetTanksResult handleRequest(final GetTanksRequest getTanksRequest) {
        List<Tank> tanks = tankDao.getTanks(getTanksRequest.getUserEmail());
        return GetTanksResult.builder()
                .withTank(tanks)
                .build();
    }
}
