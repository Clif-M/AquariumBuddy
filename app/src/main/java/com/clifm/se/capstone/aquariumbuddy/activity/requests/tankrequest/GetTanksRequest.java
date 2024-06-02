package com.clifm.se.capstone.aquariumbuddy.activity.requests.tankrequest;

public class GetTanksRequest {
    private final String userEmail;

    private GetTanksRequest(String userEmail) {
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

        public GetTanksRequest build() {
            return new GetTanksRequest(userEmail);
        }
    }
}
