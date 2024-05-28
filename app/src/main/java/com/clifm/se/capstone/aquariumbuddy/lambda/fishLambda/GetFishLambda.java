package com.clifm.se.capstone.aquariumbuddy.lambda.fishLambda;

import com.clifm.se.capstone.aquariumbuddy.activity.requests.fishrequest.GetFishRequest;
import com.clifm.se.capstone.aquariumbuddy.activity.results.fishresults.GetFishResult;
import com.clifm.se.capstone.aquariumbuddy.lambda.AuthenticatedLambdaRequest;
import com.clifm.se.capstone.aquariumbuddy.lambda.LambdaActivityRunner;
import com.clifm.se.capstone.aquariumbuddy.lambda.LambdaResponse;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetFishLambda
        extends LambdaActivityRunner<GetFishRequest, GetFishResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetFishRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetFishRequest> input, Context context) {
        return super.runActivity(() -> {
            GetFishRequest stagedRequest = input.fromUserClaims(claims ->
                    GetFishRequest.builder()
                            .withUserEmail(claims.get("email"))
                            .build());
            return input.fromPath(path -> GetFishRequest.builder()
                    .withUserEmail(stagedRequest.getUserEmail())
                    .build());
            }, (request, serviceComponent) ->
                        serviceComponent.provideGetFishActivity().handleRequest(request)
        );
    }
}
