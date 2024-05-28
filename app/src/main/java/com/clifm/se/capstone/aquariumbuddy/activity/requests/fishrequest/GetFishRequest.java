package com.clifm.se.capstone.aquariumbuddy.activity.requests.fishrequest;

public class GetFishRequest {
    private final String userEmail;

    private GetFishRequest(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserEmail() {
        return userEmail;
    }

    //CHECKSTYLE:OFF:BUILDER
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String userEmail;

        public Builder withUserEmail(String userEmail) {
            this.userEmail = userEmail;
            return this;
        }

        public GetFishRequest build() {
            return new GetFishRequest(userEmail);
        }
    }
}
