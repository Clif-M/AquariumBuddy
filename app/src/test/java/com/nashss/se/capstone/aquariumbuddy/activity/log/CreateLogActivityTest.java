package com.nashss.se.capstone.aquariumbuddy.activity.log;

import com.clifm.se.capstone.aquariumbuddy.activity.logactivities.CreateLogActivity;
import com.clifm.se.capstone.aquariumbuddy.activity.requests.logrequest.CreateLogRequest;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.LogDao;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

public class CreateLogActivityTest {
    @Mock
    private LogDao logDao;

    private CreateLogActivity activity;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        activity = new CreateLogActivity(logDao);
    }

    @Test
    public void handleRequest_goodRequest_returnsNewOrganization() {
        // GIVEN
        String userEmail = "userEmail";
        String name = "logName";
        Log log = new Log();
        log.setUserEmail(userEmail);

        CreateLogRequest request = CreateLogRequest.builder()
                .withUserEmail(userEmail)
                .withLog(log)
                .build();

        ArgumentCaptor<Log> argumentCaptor = ArgumentCaptor.forClass(Log.class);

        // WHEN
        activity.handleRequest(request);
        verify(logDao).writeLog(argumentCaptor.capture());

        // THEN
        assertEquals(userEmail, argumentCaptor.getValue().getUserEmail(), "Expected class to pass provided orgId to DAO for write");

    }
}
