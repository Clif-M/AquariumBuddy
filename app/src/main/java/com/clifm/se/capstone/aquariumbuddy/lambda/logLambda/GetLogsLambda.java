package com.clifm.se.capstone.aquariumbuddy.lambda.logLambda;

import com.clifm.se.capstone.aquariumbuddy.activity.requests.logrequest.GetLogsRequest;
import com.clifm.se.capstone.aquariumbuddy.activity.results.logresults.GetLogsResult;
import com.clifm.se.capstone.aquariumbuddy.lambda.AuthenticatedLambdaRequest;
import com.clifm.se.capstone.aquariumbuddy.lambda.LambdaActivityRunner;
import com.clifm.se.capstone.aquariumbuddy.lambda.LambdaResponse;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetLogsLambda
        extends LambdaActivityRunner<GetLogsRequest, GetLogsResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetLogsRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetLogsRequest> input, Context context) {
        return super.runActivity(() -> {
            GetLogsRequest stagedRequest = input.fromUserClaims(claims ->
                    GetLogsRequest.builder()
                            .withUserEmail(claims.get("email"))
                            .build());
            return input.fromPath(path -> GetLogsRequest.builder()
                    .withUserEmail(stagedRequest.getUserEmail())
                    .build());
            }, (request, serviceComponent) ->
                        serviceComponent.provideGetLogsActivity().handleRequest(request)
        );
    }
}
