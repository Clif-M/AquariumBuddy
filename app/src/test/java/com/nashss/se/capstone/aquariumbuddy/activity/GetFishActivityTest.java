package com.nashss.se.capstone.aquariumbuddy.activity;

import com.clifm.se.capstone.aquariumbuddy.activity.fishActivities.GetFishActivity;
import com.clifm.se.capstone.aquariumbuddy.activity.requests.fishrequest.GetFishRequest;
import com.clifm.se.capstone.aquariumbuddy.activity.results.fishresults.GetFishResult;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.FishDao;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Fish;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class GetFishActivityTest {
    @Mock
    private FishDao fishDao;

    private GetFishActivity activity;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        activity = new GetFishActivity(fishDao);
    }

    @Test
    public void handleRequest_goodRequest_callsDaoLoadMethod() {
        // GIVEN
        String userEmail = "userEmail";
        Fish fish = new Fish();
        fish.setUserEmail(userEmail);
        GetFishRequest request = GetFishRequest.builder()
                .withUserEmail(userEmail)
                .build();

        doReturn(new ArrayList<>()).when(fishDao).getFish(userEmail);

        // WHEN
        activity.handleRequest(request);

        // THEN
        verify(fishDao).getFish(userEmail);
    }
}