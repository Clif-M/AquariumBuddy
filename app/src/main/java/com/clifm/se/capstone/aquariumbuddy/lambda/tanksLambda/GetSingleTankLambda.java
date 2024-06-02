package com.clifm.se.capstone.aquariumbuddy.lambda.tanksLambda;

import com.clifm.se.capstone.aquariumbuddy.activity.requests.tankrequest.GetSingleTankRequest;
import com.clifm.se.capstone.aquariumbuddy.activity.results.tankresults.GetSingleTankResult;
import com.clifm.se.capstone.aquariumbuddy.lambda.AuthenticatedLambdaRequest;
import com.clifm.se.capstone.aquariumbuddy.lambda.LambdaActivityRunner;
import com.clifm.se.capstone.aquariumbuddy.lambda.LambdaResponse;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetSingleTankLambda
        extends LambdaActivityRunner<GetSingleTankRequest, GetSingleTankResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetSingleTankRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetSingleTankRequest> input, Context context) {
        return super.runActivity(() -> {
            GetSingleTankRequest stagedRequest = input.fromUserClaims(claims ->
                    GetSingleTankRequest.builder()
                            .withUserEmail(claims.get("email"))
                            .build());
            return input.fromPath(path -> GetSingleTankRequest.builder()
                    .withUserEmail(stagedRequest.getUserEmail())
                    .withTankId(path.get("tankId"))
                    .build());
            }, (request, serviceComponent) ->
                        serviceComponent.provideGetSingleTankActivity().handleRequest(request)
        );
    }
}
