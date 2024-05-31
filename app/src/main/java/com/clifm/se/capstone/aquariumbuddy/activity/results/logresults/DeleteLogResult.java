package com.clifm.se.capstone.aquariumbuddy.activity.results.logresults;

import com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Log;

public class DeleteLogResult {
    private final Log log;

    private DeleteLogResult(Log log) {
        this.log = log;
    }
    public Log getLog() {
        return log;
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Log log;

        public Builder withLog(Log log) {
            this.log = log;
            return this;
        }
        public DeleteLogResult build() {
            return new DeleteLogResult(log);
        }
    }
}
