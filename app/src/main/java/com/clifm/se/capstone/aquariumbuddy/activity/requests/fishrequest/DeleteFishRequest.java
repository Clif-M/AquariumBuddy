package com.clifm.se.capstone.aquariumbuddy.activity.requests.fishrequest;

public class DeleteFishRequest {
    private final String userEmail;
    private final String fishId;

    private DeleteFishRequest(String userEmail, String fishId) {
        this.userEmail = userEmail;
        this.fishId = fishId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getFishId() {
        return fishId;
    }

    //CHECKSTYLE:OFF:BUILDER
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String userEmail;
        private String fishId;

        public Builder withUserEmail(String userEmail) {
            this.userEmail = userEmail;
            return this;
        }

        public Builder withFishId(String fishId) {
            this.fishId = fishId;
            return this;
        }

        public DeleteFishRequest build() {
            return new DeleteFishRequest(userEmail, fishId);
        }
    }
}
