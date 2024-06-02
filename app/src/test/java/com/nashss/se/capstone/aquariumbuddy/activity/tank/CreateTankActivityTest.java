package com.nashss.se.capstone.aquariumbuddy.activity.tank;

import com.clifm.se.capstone.aquariumbuddy.activity.tankactivities.CreateTankActivity;
import com.clifm.se.capstone.aquariumbuddy.activity.requests.tankrequest.CreateTankRequest;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.TankDao;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Tank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

public class CreateTankActivityTest {
    @Mock
    private TankDao tankDao;

    private CreateTankActivity activity;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        activity = new CreateTankActivity(tankDao);
    }

    @Test
    public void handleRequest_goodRequest_returnsNewOrganization() {
        // GIVEN
        String userEmail = "userEmail";
        String name = "tankName";
        Tank tank = new Tank();
        tank.setUserEmail(userEmail);

        CreateTankRequest request = CreateTankRequest.builder()
                .withUserEmail(userEmail)
                .withTank(tank)
                .build();

        ArgumentCaptor<Tank> argumentCaptor = ArgumentCaptor.forClass(Tank.class);

        // WHEN
        activity.handleRequest(request);
        verify(tankDao).writeTank(argumentCaptor.capture());

        // THEN
        assertEquals(userEmail, argumentCaptor.getValue().getUserEmail(), "Expected class to pass provided orgId to DAO for write");

    }
}
