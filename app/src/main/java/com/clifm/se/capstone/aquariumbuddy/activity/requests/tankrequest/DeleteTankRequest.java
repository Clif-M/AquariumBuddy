package com.clifm.se.capstone.aquariumbuddy.activity.requests.tankrequest;

public class DeleteTankRequest {
    private final String userEmail;
    private final String tankId;

    private DeleteTankRequest(String userEmail, String tankId) {
        this.userEmail = userEmail;
        this.tankId = tankId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getTankId() {
        return tankId;
    }

    //CHECKSTYLE:OFF:BUILDER
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String userEmail;
        private String tankId;

        public Builder withUserEmail(String userEmail) {
            this.userEmail = userEmail;
            return this;
        }

        public Builder withTankId(String tankId) {
            this.tankId = tankId;
            return this;
        }

        public DeleteTankRequest build() {
            return new DeleteTankRequest(userEmail, tankId);
        }
    }
}
