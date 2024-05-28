package com.clifm.se.capstone.aquariumbuddy.activity.fishActivities;

import com.clifm.se.capstone.aquariumbuddy.activity.requests.fishrequest.GetSingleFishRequest;
import com.clifm.se.capstone.aquariumbuddy.activity.results.fishresults.GetSingleFishResult;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.FishDao;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Fish;

import javax.inject.Inject;

/**
 * Implementation of the GetSingleFishActivity for the Fish endpoint.
 *
 * This API allows the customer to interact with Fish objects in the database.
 */
public class GetSingleFishActivity {
    private final FishDao fishDao;

    /**
     * Instantiates a new GetSingleFishActivity object.
     *
     * @param fishDao FishDao to access the fish table.
     */
    @Inject
    public GetSingleFishActivity(FishDao fishDao) {
        this.fishDao = fishDao;
    }

    /**
     * This method handles the incoming request by retrieving a fish from the database, if it exists.
     * <p>
     * It then returns the fish.
     * <p>
     * If the fish does not exist on the database, this method will propagate a FishNotFoundException.
     *
     * @param getSingleFishRequest request object containing the userEmail and fishId
     * @return GetFishResult result object containing the API defined
     * {@link com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Fish}
     */

    public GetSingleFishResult handleRequest(final GetSingleFishRequest getSingleFishRequest) {
        Fish fish = fishDao.getSingleFish(getSingleFishRequest.getUserEmail(), getSingleFishRequest.getFishId());
        return GetSingleFishResult.builder()
                .withFish(fish)
                .build();
    }
}
