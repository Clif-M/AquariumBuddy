package com.nashss.se.capstone.aquariumbuddy.activity.log;

import com.clifm.se.capstone.aquariumbuddy.activity.logactivities.GetLogsActivity;
import com.clifm.se.capstone.aquariumbuddy.activity.requests.logrequest.GetLogsRequest;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.LogDao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

public class GetLogsActivityTest {
    @Mock
    private LogDao logDao;

    private GetLogsActivity activity;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        activity = new GetLogsActivity(logDao);
    }

    @Test
    public void handleRequest_goodRequest_callsDaoLoadMethod() {
        // GIVEN
        String userEmail = "userEmail";
        GetLogsRequest request = GetLogsRequest.builder()
                .withUserEmail(userEmail)
                .build();

        doReturn(new ArrayList<>()).when(logDao).getLogs(userEmail);

        // WHEN
        activity.handleRequest(request);

        // THEN
        verify(logDao).getLogs(userEmail);
    }
}