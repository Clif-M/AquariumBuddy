package com.nashss.se.capstone.aquariumbuddy.activity.log;

import com.clifm.se.capstone.aquariumbuddy.activity.logactivities.GetLogsForTankActivity;
import com.clifm.se.capstone.aquariumbuddy.activity.requests.logrequest.GetLogsForTankRequest;
import com.clifm.se.capstone.aquariumbuddy.activity.requests.logrequest.GetSingleLogRequest;
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

public class GetLogsForTankActivityTest {
    @Mock
    private LogDao logDao;

    private GetLogsForTankActivity activity;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        activity = new GetLogsForTankActivity(logDao);
    }

    @Test
    public void handleRequest_goodRequest_callsDaoLoadMethod() {
        // GIVEN
        String tankId = "tankId";
        GetLogsForTankRequest request = GetLogsForTankRequest.builder()
                .withTankId(tankId)
                .build();
        List<Log> logList = new ArrayList<>();
        logList.add(new Log());

        doReturn(logList).when(logDao).getLogsforTank(request.getTankId());

        // WHEN
        activity.handleRequest(request);

        // THEN
        verify(logDao).getLogsforTank(request.getTankId());
    }

    @Test
    public void handleRequest_noLogFound_throwsException() {
        // GIVEN
        String tankId = "tankId";
        GetLogsForTankRequest request = GetLogsForTankRequest.builder()
                .withTankId(tankId)
                .build();
        List<Log> logList = new ArrayList<>();
        logList.add(new Log());

        doReturn(new ArrayList<>()).when(logDao).getLogsforTank(request.getTankId());

        // WHEN
        // THEN
        Assertions.assertThrows(LogNotFoundException.class, () -> activity.handleRequest(request),
                "Expected method to throw Error");
        verify(logDao).getLogsforTank(request.getTankId());
    }
}
