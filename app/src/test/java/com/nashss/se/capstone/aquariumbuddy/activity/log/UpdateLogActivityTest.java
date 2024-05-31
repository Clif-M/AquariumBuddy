package com.nashss.se.capstone.aquariumbuddy.activity.log;

import com.clifm.se.capstone.aquariumbuddy.activity.logactivities.UpdateLogActivity;
import com.clifm.se.capstone.aquariumbuddy.activity.requests.logrequest.UpdateLogRequest;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.LogDao;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Log;
import com.clifm.se.capstone.aquariumbuddy.exceptions.LogNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

public class UpdateLogActivityTest {
    @Mock
    private LogDao logDao;

    private UpdateLogActivity activity;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        activity = new UpdateLogActivity(logDao);
    }

    @Test
    public void handleRequest_goodRequest_returnsUpdatedLog() {
        // GIVEN
        String userEmail = "userEmail";
        String name = "logName";
        Log log = new Log();
        log.setUserEmail(userEmail);

        UpdateLogRequest request = UpdateLogRequest.builder()
                .withUserEmail(userEmail)
                .withLog(log)
                .build();

        ArgumentCaptor<Log> argumentCaptor = ArgumentCaptor.forClass(Log.class);
        doThrow(new LogNotFoundException()).when(logDao).getSingleLog(any(String.class), any(String.class));

        // WHEN
        activity.handleRequest(request);
        verify(logDao).writeLog(argumentCaptor.capture());


        // THEN
        assertEquals(userEmail, argumentCaptor.getValue().getUserEmail(), "Expected class to pass provided orgId to DAO for write");
    }
}
