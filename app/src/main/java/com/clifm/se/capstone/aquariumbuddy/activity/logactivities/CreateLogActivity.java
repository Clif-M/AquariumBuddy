package com.clifm.se.capstone.aquariumbuddy.activity.logactivities;

import com.clifm.se.capstone.aquariumbuddy.activity.requests.logrequest.CreateLogRequest;
import com.clifm.se.capstone.aquariumbuddy.activity.results.logresults.CreateLogResult;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.LogDao;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Log;

import java.util.UUID;
import javax.inject.Inject;

/**
 * Implementation of the CreateLogActivity for the Log endpoint.
 *
 * This API allows the customer to interact with Log objects in the database.
 */
public class CreateLogActivity {
    private final LogDao logDao;

    /**
     * Instantiates a new CreateLogActivity object.
     *
     * @param logDao LogDao to access the log table.
     */
    @Inject
    public CreateLogActivity(LogDao logDao) {
        this.logDao = logDao;
    }

    /**
     * This method handles the incoming request by creating a log in the database.
     * <p>
     * It then returns the log.
     *
     * @param createLogRequest request object containing the userEmail and logId
     * @return GetLogResult result object containing the API defined
     * {@link Log}
     */

    public CreateLogResult handleRequest(final CreateLogRequest createLogRequest) {
        Log log = createLogRequest.getLog();
        log.setUserEmail(createLogRequest.getUserEmail());
        log.setLogId(UUID.randomUUID().toString());
        logDao.writeLog(log);
        return CreateLogResult.builder()
                .withLog(log)
                .build();
    }
}
