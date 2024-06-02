package com.clifm.se.capstone.aquariumbuddy.lambda.tanksLambda;

import com.clifm.se.capstone.aquariumbuddy.activity.requests.tankrequest.GetTanksRequest;
import com.clifm.se.capstone.aquariumbuddy.activity.results.tankresults.GetTanksResult;
import com.clifm.se.capstone.aquariumbuddy.lambda.AuthenticatedLambdaRequest;
import com.clifm.se.capstone.aquariumbuddy.lambda.LambdaActivityRunner;
import com.clifm.se.capstone.aquariumbuddy.lambda.LambdaResponse;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetTanksLambda
        extends LambdaActivityRunner<GetTanksRequest, GetTanksResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetTanksRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetTanksRequest> input, Context context) {
        return super.runActivity(() -> {
            GetTanksRequest stagedRequest = input.fromUserClaims(claims ->
                    GetTanksRequest.builder()
                            .withUserEmail(claims.get("email"))
                            .build());
            return input.fromPath(path -> GetTanksRequest.builder()
                    .withUserEmail(stagedRequest.getUserEmail())
                    .build());
            }, (request, serviceComponent) ->
                        serviceComponent.provideGetTanksActivity().handleRequest(request)
        );
    }
}
