package com.clifm.se.capstone.aquariumbuddy.activity.fishActivities;

import com.clifm.se.capstone.aquariumbuddy.activity.requests.fishrequest.GetFishRequest;
import com.clifm.se.capstone.aquariumbuddy.activity.results.fishresults.GetFishResult;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.FishDao;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Fish;

import java.util.List;

import javax.inject.Inject;

/**
 * Implementation of the GetFishActivity for the Fish endpoint.
 *
 * This API allows the customer to interact with Fish objects in the database.
 */
public class GetFishActivity {
    private final FishDao fishDao;

    /**
     * Instantiates a new GetFishActivity object.
     *
     * @param fishDao FishDao to access the fish table.
     */
    @Inject
    public GetFishActivity(FishDao fishDao) {
        this.fishDao = fishDao;
    }

    /**
     * This method handles the incoming request by retrieving a list of fish from the database, if it exists.
     * <p>
     * It then returns the list of fish.
     * <p>
     * If the user has no fish does not exist on the database, this method will propagate a FishNotFoundException.
     *
     * @param getFishRequest request object containing the userEmail and fishId
     * @return GetFishResult result object containing the API defined
     * {@link com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Fish}
     */

    public GetFishResult handleRequest(final GetFishRequest getFishRequest) {
        List<Fish> fish = fishDao.getFish(getFishRequest.getUserEmail());
        return GetFishResult.builder()
                .withFish(fish)
                .build();
    }
}
