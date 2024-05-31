package com.nashss.se.capstone.aquariumbuddy.activity.log;

import com.clifm.se.capstone.aquariumbuddy.activity.logactivities.DeleteLogActivity;
import com.clifm.se.capstone.aquariumbuddy.activity.requests.logrequest.DeleteLogRequest;
import com.clifm.se.capstone.aquariumbuddy.activity.results.logresults.DeleteLogResult;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.LogDao;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class DeleteLogActivityTest {
    @Mock
    private LogDao logDao;
    private DeleteLogActivity activity;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        activity = new DeleteLogActivity(logDao);
    }

    @Test
    public void handleRequest_goodRequest_callsProjectDaoDeleteMethod() {
        // GIVEN
        String userEmail = "userEmail";
        String logId = "logId";
        DeleteLogRequest request = DeleteLogRequest.builder()
                .withUserEmail(userEmail)
                .withLogId(logId)
                .build();

        // WHEN
        DeleteLogResult result = activity.handleRequest(request);

        // THEN
        verify(logDao).deleteLog(userEmail, logId);
    }
}
