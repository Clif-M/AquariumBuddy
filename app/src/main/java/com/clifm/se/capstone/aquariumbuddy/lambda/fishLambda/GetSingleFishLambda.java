package com.clifm.se.capstone.aquariumbuddy.lambda.fishLambda;

import com.clifm.se.capstone.aquariumbuddy.activity.requests.fishrequest.GetSingleFishRequest;
import com.clifm.se.capstone.aquariumbuddy.activity.results.fishresults.GetSingleFishResult;
import com.clifm.se.capstone.aquariumbuddy.lambda.AuthenticatedLambdaRequest;
import com.clifm.se.capstone.aquariumbuddy.lambda.LambdaActivityRunner;
import com.clifm.se.capstone.aquariumbuddy.lambda.LambdaResponse;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetSingleFishLambda
        extends LambdaActivityRunner<GetSingleFishRequest, GetSingleFishResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetSingleFishRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetSingleFishRequest> input, Context context) {
        return super.runActivity(() -> {
            GetSingleFishRequest stagedRequest = input.fromUserClaims(claims ->
                    GetSingleFishRequest.builder()
                            .withUserEmail(claims.get("email"))
                            .build());
            return input.fromPath(path -> GetSingleFishRequest.builder()
                    .withUserEmail(stagedRequest.getUserEmail())
                    .withFishId(path.get("fishId"))
                    .build());
            }, (request, serviceComponent) ->
                        serviceComponent.provideGetSingleFishActivity().handleRequest(request)
        );
    }
}
