package com.nashss.se.capstone.aquariumbuddy.activity.tank;

import com.clifm.se.capstone.aquariumbuddy.activity.tankactivities.GetTanksActivity;
import com.clifm.se.capstone.aquariumbuddy.activity.requests.tankrequest.GetTanksRequest;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.TankDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

public class GetTanksActivityTest {
    @Mock
    private TankDao tankDao;

    private GetTanksActivity activity;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        activity = new GetTanksActivity(tankDao);
    }

    @Test
    public void handleRequest_goodRequest_callsDaoLoadMethod() {
        // GIVEN
        String userEmail = "userEmail";
        GetTanksRequest request = GetTanksRequest.builder()
                .withUserEmail(userEmail)
                .build();

        doReturn(new ArrayList<>()).when(tankDao).getTanks(userEmail);

        // WHEN
        activity.handleRequest(request);

        // THEN
        verify(tankDao).getTanks(userEmail);
    }
}