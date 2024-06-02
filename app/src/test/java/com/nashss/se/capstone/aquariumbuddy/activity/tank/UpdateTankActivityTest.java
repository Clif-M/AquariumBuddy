package com.nashss.se.capstone.aquariumbuddy.activity.tank;

import com.clifm.se.capstone.aquariumbuddy.activity.tankactivities.UpdateTankActivity;
import com.clifm.se.capstone.aquariumbuddy.activity.requests.tankrequest.UpdateTankRequest;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.TankDao;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Tank;
import com.clifm.se.capstone.aquariumbuddy.exceptions.TankNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

public class UpdateTankActivityTest {
    @Mock
    private TankDao tankDao;

    private UpdateTankActivity activity;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        activity = new UpdateTankActivity(tankDao);
    }

    @Test
    public void handleRequest_goodRequest_returnsUpdatedTank() {
        // GIVEN
        String userEmail = "userEmail";
        String name = "tankName";
        Tank tank = new Tank();
        tank.setUserEmail(userEmail);

        UpdateTankRequest request = UpdateTankRequest.builder()
                .withUserEmail(userEmail)
                .withTank(tank)
                .build();

        ArgumentCaptor<Tank> argumentCaptor = ArgumentCaptor.forClass(Tank.class);
        doThrow(new TankNotFoundException()).when(tankDao).getSingleTank(any(String.class), any(String.class));

        // WHEN
        activity.handleRequest(request);
        verify(tankDao).writeTank(argumentCaptor.capture());


        // THEN
        assertEquals(userEmail, argumentCaptor.getValue().getUserEmail(), "Expected class to pass provided orgId to DAO for write");
    }
}
