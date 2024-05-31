package com.clifm.se.capstone.aquariumbuddy.activity.logactivities;

import com.clifm.se.capstone.aquariumbuddy.activity.requests.logrequest.GetLogsRequest;
import com.clifm.se.capstone.aquariumbuddy.activity.results.logresults.GetLogsResult;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.LogDao;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Log;

import java.util.List;
import javax.inject.Inject;

/**
 * Implementation of the GetLogsActivity for the Log endpoint.
 *
 * This API allows the customer to interact with Log objects in the database.
 */
public class GetLogsActivity {
    private final LogDao logDao;

    /**
     * Instantiates a new GetLogActivity object.
     *
     * @param logDao LogDao to access the log table.
     */
    @Inject
    public GetLogsActivity(LogDao logDao) {
        this.logDao = logDao;
    }

    /**
     * This method handles the incoming request by retrieving a list of log from the database, if it exists.
     * <p>
     * It then returns the list of log.
     * <p>
     * If the user has no log does not exist on the database, this method will propagate a LogNotFoundException.
     *
     * @param getLogsRequest request object containing the userEmail and logId
     * @return GetLogResult result object containing the API defined
     * {@link Log}
     */

    public GetLogsResult handleRequest(final GetLogsRequest getLogsRequest) {
        List<Log> logs = logDao.getLogs(getLogsRequest.getUserEmail());
        return GetLogsResult.builder()
                .withLog(logs)
                .build();
    }
}
