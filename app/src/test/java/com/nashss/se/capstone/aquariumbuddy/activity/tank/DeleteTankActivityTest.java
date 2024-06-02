package com.nashss.se.capstone.aquariumbuddy.activity.tank;

import com.clifm.se.capstone.aquariumbuddy.activity.tankactivities.DeleteTankActivity;
import com.clifm.se.capstone.aquariumbuddy.activity.requests.tankrequest.DeleteTankRequest;
import com.clifm.se.capstone.aquariumbuddy.activity.results.tankresults.DeleteTankResult;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.LogDao;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.TankDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class DeleteTankActivityTest {
    @Mock
    private TankDao tankDao;

    @Mock
    private LogDao logDao;
    private DeleteTankActivity activity;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        activity = new DeleteTankActivity(tankDao, logDao);
    }

    @Test
    public void handleRequest_goodRequest_callsProjectDaoDeleteMethod() {
        // GIVEN
        String userEmail = "userEmail";
        String tankId = "tankId";
        DeleteTankRequest request = DeleteTankRequest.builder()
                .withUserEmail(userEmail)
                .withTankId(tankId)
                .build();



        // WHEN
        DeleteTankResult result = activity.handleRequest(request);

        // THEN
        verify(logDao).batchDeleteLogs(tankId);
        verify(tankDao).deleteTank(userEmail, tankId);
    }
}
