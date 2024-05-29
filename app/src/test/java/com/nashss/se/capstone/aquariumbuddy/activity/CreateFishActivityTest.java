package com.nashss.se.capstone.aquariumbuddy.activity;

import com.clifm.se.capstone.aquariumbuddy.activity.fishActivities.CreateFishActivity;
import com.clifm.se.capstone.aquariumbuddy.activity.requests.fishrequest.CreateFishRequest;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.FishDao;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Fish;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

public class CreateFishActivityTest {
    @Mock
    private FishDao fishDao;

    private CreateFishActivity activity;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        activity = new CreateFishActivity(fishDao);
    }

    @Test
    public void handleRequest_goodRequest_returnsNewOrganization() {
        // GIVEN
        String userEmail = "userEmail";
        String name = "fishName";
        Fish fish = new Fish();
        fish.setUserEmail(userEmail);
        fish.setName(name);

        CreateFishRequest request = CreateFishRequest.builder()
                .withUserEmail(userEmail)
                .withFish(fish)
                .build();

        ArgumentCaptor<Fish> argumentCaptor = ArgumentCaptor.forClass(Fish.class);

        // WHEN
        activity.handleRequest(request);
        verify(fishDao).writeFish(argumentCaptor.capture());

        // THEN
        assertEquals(userEmail, argumentCaptor.getValue().getUserEmail(), "Expected class to pass provided orgId to DAO for write");
        assertEquals(name, argumentCaptor.getValue().getName(), "Expected class to pass provided displayName to DAO for write");
    }
}
