package com.clifm.se.capstone.aquariumbuddy.activity.logactivities;

import com.clifm.se.capstone.aquariumbuddy.activity.requests.logrequest.GetSingleLogRequest;
import com.clifm.se.capstone.aquariumbuddy.activity.results.logresults.GetSingleLogResult;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.LogDao;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Log;

import javax.inject.Inject;

/**
 * Implementation of the GetSingleLogActivity for the Log endpoint.
 *
 * This API allows the customer to interact with Log objects in the database.
 */
public class GetSingleLogActivity {
    private final LogDao logDao;

    /**
     * Instantiates a new GetSingleLogActivity object.
     *
     * @param logDao LogDao to access the log table.
     */
    @Inject
    public GetSingleLogActivity(LogDao logDao) {
        this.logDao = logDao;
    }

    /**
     * This method handles the incoming request by retrieving a log from the database, if it exists.
     * <p>
     * It then returns the log.
     * <p>
     * If the log does not exist on the database, this method will propagate a LogNotFoundException.
     *
     * @param getSingleLogRequest request object containing the userEmail and logId
     * @return GetLogResult result object containing the API defined
     * {@link Log}
     */

    public GetSingleLogResult handleRequest(final GetSingleLogRequest getSingleLogRequest) {
        Log log = logDao.getSingleLog(getSingleLogRequest.getUserEmail(), getSingleLogRequest.getLogId());
        return GetSingleLogResult.builder()
                .withLog(log)
                .build();
    }
}
