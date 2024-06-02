package com.nashss.se.capstone.aquariumbuddy.activity.tank;

import com.clifm.se.capstone.aquariumbuddy.activity.tankactivities.GetSingleTankActivity;
import com.clifm.se.capstone.aquariumbuddy.activity.requests.tankrequest.GetSingleTankRequest;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.TankDao;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Tank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

public class GetSingleTankActivityTest {
    @Mock
    private TankDao tankDao;

    private GetSingleTankActivity activity;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        activity = new GetSingleTankActivity(tankDao);
    }

    @Test
    public void handleRequest_goodRequest_callsDaoLoadMethod() {
        // GIVEN
        String userEmail = "userEmail";
        String tankId = "tankId";
        Tank tank = new Tank();
        tank.setTankId(tankId);
        tank.setUserEmail(userEmail);
        GetSingleTankRequest request = GetSingleTankRequest.builder()
                .withUserEmail(userEmail)
                .withTankId(tankId)
                .build();

        doReturn(tank).when(tankDao).getSingleTank(userEmail, tankId);

        // WHEN
        activity.handleRequest(request);

        // THEN
        verify(tankDao).getSingleTank(userEmail, tankId);
    }
}
