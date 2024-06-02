package com.clifm.se.capstone.aquariumbuddy.activity.requests.tankrequest;

import com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Tank;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = UpdateTankRequest.Builder.class)
public class UpdateTankRequest {
    private Tank tank;
    private String userEmail;
    private UpdateTankRequest(Tank tank, String userEmail) {
        this.tank = tank;
        this.userEmail = userEmail;
    }

    public Tank getTank() {
        return tank;
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
        private Tank tank;
        private String userEmail;

        public Builder withTank(Tank tank) {
            this.tank=tank;
            return this;
        }

        public Builder withUserEmail(String userEmail) {
            this.userEmail = userEmail;
            return this;
        }

        public UpdateTankRequest build() {
            return new UpdateTankRequest(tank, userEmail);
        }
    }
}
