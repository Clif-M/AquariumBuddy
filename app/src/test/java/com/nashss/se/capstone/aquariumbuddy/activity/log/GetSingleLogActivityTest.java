package com.nashss.se.capstone.aquariumbuddy.activity.log;

import com.clifm.se.capstone.aquariumbuddy.activity.logactivities.GetSingleLogActivity;
import com.clifm.se.capstone.aquariumbuddy.activity.requests.logrequest.GetSingleLogRequest;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.LogDao;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

public class GetSingleLogActivityTest {
    @Mock
    private LogDao logDao;

    private GetSingleLogActivity activity;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        activity = new GetSingleLogActivity(logDao);
    }

    @Test
    public void handleRequest_goodRequest_callsDaoLoadMethod() {
        // GIVEN
        String userEmail = "userEmail";
        String logId = "logId";
        Log log = new Log();
        log.setLogId(logId);
        log.setUserEmail(userEmail);
        GetSingleLogRequest request = GetSingleLogRequest.builder()
                .withUserEmail(userEmail)
                .withLogId(logId)
                .build();

        doReturn(log).when(logDao).getSingleLog(userEmail, logId);

        // WHEN
        activity.handleRequest(request);

        // THEN
        verify(logDao).getSingleLog(userEmail, logId);
    }
}
