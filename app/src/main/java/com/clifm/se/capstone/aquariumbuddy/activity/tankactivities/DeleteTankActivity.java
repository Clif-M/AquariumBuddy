package com.clifm.se.capstone.aquariumbuddy.activity.tankactivities;

import com.clifm.se.capstone.aquariumbuddy.activity.requests.tankrequest.DeleteTankRequest;
import com.clifm.se.capstone.aquariumbuddy.activity.results.tankresults.DeleteTankResult;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.LogDao;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.TankDao;

import javax.inject.Inject;

/**
 * Implementation of the DeleteTankActivity for the Tank endpoint.
 *
 * This API allows the customer to interact with Tank objects in the database.
 */
public class DeleteTankActivity {
    private final TankDao tankDao;

    private final LogDao logDao;

    /**
     * Instantiates a new DeleteTankActivity object.
     *
     * @param tankDao TankDao to access the tank table.
     * @param logDao LogDao to access the log table.
     */
    @Inject
    public DeleteTankActivity(TankDao tankDao, LogDao logDao) {
        this.tankDao = tankDao;
        this.logDao = logDao;
    }

    /**
     * This method handles the incoming request by deleting a tank from the database, if it exists.
     * It also deletes all associated logs
     * <p>
     *
     * <p>
     *
     * @param deleteTankRequest request object containing the userEmail and tankId
     * @return DeleteTankResult result object containing the API defined
     * {@link com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Tank}
     */

    public DeleteTankResult handleRequest(final DeleteTankRequest deleteTankRequest) {
        logDao.batchDeleteLogs(deleteTankRequest.getTankId());
        tankDao.deleteTank(deleteTankRequest.getUserEmail(), deleteTankRequest.getTankId());
        return null;
    }
}
