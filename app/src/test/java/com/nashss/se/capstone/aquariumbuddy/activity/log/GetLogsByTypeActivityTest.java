package com.nashss.se.capstone.aquariumbuddy.activity.log;

import com.clifm.se.capstone.aquariumbuddy.activity.logactivities.GetLogsByTypeActivity;
import com.clifm.se.capstone.aquariumbuddy.activity.requests.logrequest.GetLogsByTypeRequest;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.LogDao;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Log;
import com.clifm.se.capstone.aquariumbuddy.exceptions.LogNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

public class GetLogsByTypeActivityTest {
    @Mock
    private LogDao logDao;

    private GetLogsByTypeActivity activity;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        activity = new GetLogsByTypeActivity(logDao);
    }

    @Test
    public void handleRequest_goodRequest_callsDaoLoadMethod() {
        // GIVEN
        String tankId = "tankId";
        String flavor = "flavor";
        GetLogsByTypeRequest request = GetLogsByTypeRequest.builder()
                .withTankId(tankId)
                .withFlavor(flavor)
                .build();
        List<Log> logList = new ArrayList<>();
        logList.add(new Log());

        doReturn(logList).when(logDao).getLogsByType(request.getTankId(), request.getFlavor());

        // WHEN
        activity.handleRequest(request);

        // THEN
        verify(logDao).getLogsByType(request.getTankId(), request.getFlavor());
    }

    @Test
    public void handleRequest_noLogFound_throwsException() {
        // GIVEN
        String tankId = "tankId";
        String flavor = "flavor";
        GetLogsByTypeRequest request = GetLogsByTypeRequest.builder()
                .withTankId(tankId)
                .withFlavor(flavor)
                .build();

        doReturn(new ArrayList<>()).when(logDao).getLogsByType(request.getTankId(), request.getFlavor());

        // WHEN
        // THEN
        Assertions.assertThrows(LogNotFoundException.class, () -> activity.handleRequest(request),
                "Expected method to throw Error");
        verify(logDao).getLogsByType(request.getTankId(), request.getFlavor());
    }
}
