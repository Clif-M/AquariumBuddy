package com.nashss.se.capstone.aquariumbuddy.activity;

import com.clifm.se.capstone.aquariumbuddy.activity.fishActivities.DeleteFishActivity;
import com.clifm.se.capstone.aquariumbuddy.activity.requests.fishrequest.DeleteFishRequest;
import com.clifm.se.capstone.aquariumbuddy.activity.requests.fishrequest.GetSingleFishRequest;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.FishDao;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Fish;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static javax.management.Query.times;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.MockitoAnnotations.openMocks;

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

        doReturn(fish).when(fishDao).deleteFish(userEmail, fishId);

        // WHEN
        activity.handleRequest(request);

        // THEN
        verify(fishDao).deleteFish(userEmail, fishId);
    }
}
