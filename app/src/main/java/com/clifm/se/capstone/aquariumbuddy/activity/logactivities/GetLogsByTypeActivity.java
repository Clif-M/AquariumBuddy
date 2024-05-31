package com.clifm.se.capstone.aquariumbuddy.activity.logactivities;

import com.clifm.se.capstone.aquariumbuddy.activity.requests.logrequest.GetLogsByTypeRequest;
import com.clifm.se.capstone.aquariumbuddy.activity.results.logresults.GetLogsByTypeResult;
import com.clifm.se.capstone.aquariumbuddy.activity.results.logresults.GetLogsByTypeResult;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.LogDao;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Log;
import com.clifm.se.capstone.aquariumbuddy.exceptions.LogNotFoundException;

import javax.inject.Inject;
import java.util.List;

/**
 * Implementation of the GetLogActivity for the Log endpoint.
 *
 * This API allows the customer to interact with Log objects in the database.
 */
public class GetLogsByTypeActivity {
    private final LogDao logDao;

    /**
     * Instantiates a new GetLogActivity object.
     *
     * @param logDao LogDao to access the log table.
     */
    @Inject
    public GetLogsByTypeActivity(LogDao logDao) {
        this.logDao = logDao;
    }

    /**
     * This method handles the incoming request by retrieving a list of log from the database, if it exists.
     * <p>
     * It then returns the list of log.
     * <p>
     * If the user has no log does not exist on the database, this method will propagate a LogNotFoundException.
     *
     * @param getLogRequest request object containing the userEmail and logId
     * @return GetLogResult result object containing the API defined
     * {@link Log}
     */

    public GetLogsByTypeResult handleRequest(final GetLogsByTypeRequest getLogRequest) {
        List<Log> logList = logDao.getLogsByType(getLogRequest.getTankId(), getLogRequest.getType());
        if (logList.isEmpty()) {
            throw new LogNotFoundException("No logs were found for this type");
        }
        return GetLogsByTypeResult.builder()
                .withLog(logList)
                .build();
    }
}
