package com.clifm.se.capstone.aquariumbuddy.activity.results.logresults;

import com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Log;

import java.util.List;

public class GetLogsByTypeResult {
    private final List<Log> logs;

    private GetLogsByTypeResult(List<Log> logs) {
        this.logs = logs;
    }
    public List<Log> getLog() {
        return logs;
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<Log> logs;

        public Builder withLog(List<Log> logs) {
            this.logs = logs;
            return this;
        }
        public GetLogsByTypeResult build() {
            return new GetLogsByTypeResult(logs);
        }
    }
}
