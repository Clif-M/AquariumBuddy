package com.clifm.se.capstone.aquariumbuddy.activity.logactivities;

import com.clifm.se.capstone.aquariumbuddy.activity.requests.logrequest.UpdateLogRequest;
import com.clifm.se.capstone.aquariumbuddy.activity.results.logresults.UpdateLogResult;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.LogDao;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Log;
import com.clifm.se.capstone.aquariumbuddy.exceptions.LogNotFoundException;

import javax.inject.Inject;

/**
 * Implementation of the UpdateLogActivity for the Log endpoint.
 *
 * This API allows the customer to interact with Log objects in the database.
 */
public class UpdateLogActivity {
    private final LogDao logDao;

    /**
     * Instantiates a new UpdateLogActivity object.
     *
     * @param logDao LogDao to access the log table.
     */
    @Inject
    public UpdateLogActivity(LogDao logDao) {
        this.logDao = logDao;
    }

    /**
     * This method handles the incoming request by updating a log in the database.
     * <p>
     * It then returns the log.
     *
     * @param updateLogRequest request object containing the log object
     * @return UpdateLogResult result object containing the API defined
     * {@link Log}
     */

    public UpdateLogResult handleRequest(final UpdateLogRequest updateLogRequest) {
        Log log = updateLogRequest.getLog();
        log.setUserEmail(updateLogRequest.getUserEmail());
        try {
            logDao.getSingleLog(log.getUserEmail(), log.getLogId());
        } catch (LogNotFoundException e) {
            throw new LogNotFoundException("Log can not be updated. Log does not exist in database.");
        }
        logDao.writeLog(log);
        return UpdateLogResult.builder()
                .withLog(log)
                .build();
    }
}
