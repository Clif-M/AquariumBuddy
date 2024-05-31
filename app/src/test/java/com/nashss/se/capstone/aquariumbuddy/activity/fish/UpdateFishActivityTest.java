package com.nashss.se.capstone.aquariumbuddy.activity.fish;

import com.clifm.se.capstone.aquariumbuddy.activity.fishActivities.UpdateFishActivity;
import com.clifm.se.capstone.aquariumbuddy.activity.requests.fishrequest.UpdateFishRequest;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.FishDao;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Fish;
import com.clifm.se.capstone.aquariumbuddy.exceptions.FishNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

public class UpdateFishActivityTest {
    @Mock
    private FishDao fishDao;

    private UpdateFishActivity activity;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        activity = new UpdateFishActivity(fishDao);
    }

    @Test
    public void handleRequest_goodRequest_returnsUpdatedFish() {
        // GIVEN
        String userEmail = "userEmail";
        String name = "fishName";
        Fish fish = new Fish();
        fish.setUserEmail(userEmail);
        fish.setName(name);

        UpdateFishRequest request = UpdateFishRequest.builder()
                .withUserEmail(userEmail)
                .withFish(fish)
                .build();

        ArgumentCaptor<Fish> argumentCaptor = ArgumentCaptor.forClass(Fish.class);
        doThrow(new FishNotFoundException()).when(fishDao).getSingleFish(any(String.class), any(String.class));

        // WHEN
        activity.handleRequest(request);
        verify(fishDao).writeFish(argumentCaptor.capture());


        // THEN
        assertEquals(userEmail, argumentCaptor.getValue().getUserEmail(), "Expected class to pass provided orgId to DAO for write");
        assertEquals(name, argumentCaptor.getValue().getName(), "Expected class to pass provided displayName to DAO for write");
    }
}
