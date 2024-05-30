package com.clifm.se.capstone.aquariumbuddy.activity.logactivities;

import com.clifm.se.capstone.aquariumbuddy.activity.requests.logrequest.GetLogsForTankRequest;
import com.clifm.se.capstone.aquariumbuddy.activity.results.logresults.GetLogsForTankResult;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.LogDao;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Log;
import com.clifm.se.capstone.aquariumbuddy.exceptions.LogNotFoundException;

import java.util.List;
import javax.inject.Inject;

/**
 * Implementation of the GetLogActivity for the Log endpoint.
 *
 * This API allows the customer to interact with Log objects in the database.
 */
public class GetLogsForTankActivity {
    private final LogDao logDao;

    /**
     * Instantiates a new GetLogActivity object.
     *
     * @param logDao LogDao to access the log table.
     */
    @Inject
    public GetLogsForTankActivity(LogDao logDao) {
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

    public GetLogsForTankResult handleRequest(final GetLogsForTankRequest getLogRequest) {
        List<Log> logList = logDao.getLogsforTank(getLogRequest.getTankId());
        if (logList.isEmpty()) {
            throw new LogNotFoundException("No logs were found for tankId");
        }
        return GetLogsForTankResult.builder()
                .withLog(logList)
                .build();
    }
}
