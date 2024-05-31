package com.nashss.se.capstone.aquariumbuddy.activity.fish;

import com.clifm.se.capstone.aquariumbuddy.activity.fishActivities.DeleteFishActivity;
import com.clifm.se.capstone.aquariumbuddy.activity.requests.fishrequest.DeleteFishRequest;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.FishDao;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Fish;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class DeleteFishActivityTest {
    @Mock
    private FishDao fishDao;
    private DeleteFishActivity activity;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        activity = new DeleteFishActivity(fishDao);
    }

    @Test
    public void handleRequest_goodRequest_callsProjectDaoDeleteMethod() {
        // GIVEN
        String userEmail = "userEmail";
        String fishId = "fishId";
        Fish fish = new Fish();
        fish.setFishId(fishId);
        fish.setUserEmail(userEmail);
        DeleteFishRequest request = DeleteFishRequest.builder()
                .withUserEmail(userEmail)
                .withFishId(fishId)
                .build();

        // WHEN
        activity.handleRequest(request);

        // THEN
        verify(fishDao).deleteFish(userEmail, fishId);
    }
}
