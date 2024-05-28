package com.clifm.se.capstone.aquariumbuddy.activity.fishActivities;

import com.clifm.se.capstone.aquariumbuddy.activity.requests.fishrequest.GetSingleFishRequest;
import com.clifm.se.capstone.aquariumbuddy.activity.results.fishresults.GetSingleFishResult;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.FishDao;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Fish;

import javax.inject.Inject;

public class GetSingleFishActivity {
    private final FishDao fishDao;

    @Inject
    public GetSingleFishActivity(FishDao fishDao) {
        this.fishDao = fishDao;
    }

    public GetSingleFishResult handleRequest(final GetSingleFishRequest getSingleFishRequest) {
        Fish fish = fishDao.getSingleFish(getSingleFishRequest.getUserEmail(), getSingleFishRequest.getFishId());
        return GetSingleFishResult.builder()
                .withFish(fish)
                .build();
    }
}
