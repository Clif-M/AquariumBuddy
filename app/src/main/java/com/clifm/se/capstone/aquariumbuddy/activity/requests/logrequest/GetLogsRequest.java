package com.clifm.se.capstone.aquariumbuddy.activity.requests.logrequest;

public class GetLogsRequest {
    private final String userEmail;

    private GetLogsRequest(String userEmail) {
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

        public GetLogsRequest build() {
            return new GetLogsRequest(userEmail);
        }
    }
}
