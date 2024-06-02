package com.clifm.se.capstone.aquariumbuddy.lambda.tanksLambda;

import com.clifm.se.capstone.aquariumbuddy.activity.requests.tankrequest.DeleteTankRequest;
import com.clifm.se.capstone.aquariumbuddy.activity.results.tankresults.DeleteTankResult;
import com.clifm.se.capstone.aquariumbuddy.lambda.AuthenticatedLambdaRequest;
import com.clifm.se.capstone.aquariumbuddy.lambda.LambdaActivityRunner;
import com.clifm.se.capstone.aquariumbuddy.lambda.LambdaResponse;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class DeleteTankLambda
        extends LambdaActivityRunner<DeleteTankRequest, DeleteTankResult>
        implements RequestHandler<AuthenticatedLambdaRequest<DeleteTankRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<DeleteTankRequest> input, Context context) {
        return super.runActivity(() -> {
            DeleteTankRequest stagedRequest = input.fromUserClaims(claims ->
                    DeleteTankRequest.builder()
                            .withUserEmail(claims.get("email"))
                            .build());
            return input.fromPath(path -> DeleteTankRequest.builder()
                    .withUserEmail(stagedRequest.getUserEmail())
                    .withTankId(path.get("tankId"))
                    .build());
            }, (request, serviceComponent) ->
                        serviceComponent.provideDeleteTankActivity().handleRequest(request)
        );
    }
}
