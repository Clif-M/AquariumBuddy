package com.clifm.se.capstone.aquariumbuddy.activity.logactivities;

import com.clifm.se.capstone.aquariumbuddy.activity.requests.logrequest.DeleteLogRequest;
import com.clifm.se.capstone.aquariumbuddy.activity.results.logresults.DeleteLogResult;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.LogDao;

import javax.inject.Inject;

/**
 * Implementation of the DeleteLogActivity for the Log endpoint.
 *
 * This API allows the customer to interact with Log objects in the database.
 */
public class DeleteLogActivity {
    private final LogDao logDao;

    /**
     * Instantiates a new DeleteLogActivity object.
     *
     * @param logDao LogDao to access the log table.
     */
    @Inject
    public DeleteLogActivity(LogDao logDao) {
        this.logDao = logDao;
    }

    /**
     * This method handles the incoming request by deleting a log from the database, if it exists.
     * <p>
     * It then returns the log.
     * <p>
     *
     * @param deleteLogRequest request object containing the userEmail and logId
     * @return DeleteLogResult result object containing the API defined
     * {@link com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Log}
     */

    public DeleteLogResult handleRequest(final DeleteLogRequest deleteLogRequest) {
        logDao.deleteLog(deleteLogRequest.getUserEmail(), deleteLogRequest.getLogId());
        return null;
    }
}
