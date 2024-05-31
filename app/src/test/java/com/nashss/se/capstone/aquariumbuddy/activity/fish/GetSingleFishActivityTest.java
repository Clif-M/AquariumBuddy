package com.nashss.se.capstone.aquariumbuddy.activity.fish;

import com.clifm.se.capstone.aquariumbuddy.activity.fishActivities.GetSingleFishActivity;
import com.clifm.se.capstone.aquariumbuddy.activity.requests.fishrequest.GetSingleFishRequest;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.FishDao;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Fish;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;


import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

public class GetSingleFishActivityTest {
    @Mock
    private FishDao fishDao;

    private GetSingleFishActivity activity;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        activity = new GetSingleFishActivity(fishDao);
    }

    @Test
    public void handleRequest_goodRequest_callsDaoLoadMethod() {
        // GIVEN
        String userEmail = "userEmail";
        String fishId = "fishId";
        Fish fish = new Fish();
        fish.setFishId(fishId);
        fish.setUserEmail(userEmail);
        GetSingleFishRequest request = GetSingleFishRequest.builder()
                .withUserEmail(userEmail)
                .withFishId(fishId)
                .build();

        doReturn(fish).when(fishDao).getSingleFish(userEmail, fishId);

        // WHEN
        activity.handleRequest(request);

        // THEN
        verify(fishDao).getSingleFish(userEmail, fishId);
    }
}
