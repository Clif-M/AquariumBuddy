package com.clifm.se.capstone.aquariumbuddy.activity.requests.fishrequest;

import com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Fish;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = UpdateFishRequest.Builder.class)
public class UpdateFishRequest {
    private Fish fish;
    private String userEmail;
    private UpdateFishRequest(Fish fish, String userEmail) {
        this.fish = fish;
        this.userEmail = userEmail;
    }

    public Fish getFish() {
        return fish;
    }

    public String getUserEmail() {
        return userEmail;
    }

    //CHECKSTYLE:OFF:BUILDER
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private Fish fish;
        private String userEmail;

        public Builder withFish(Fish fish) {
            this.fish=fish;
            return this;
        }

        public Builder withUserEmail(String userEmail) {
            this.userEmail = userEmail;
            return this;
        }

        public UpdateFishRequest build() {
            return new UpdateFishRequest(fish, userEmail);
        }
    }
}
