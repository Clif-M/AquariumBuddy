package com.clifm.se.capstone.aquariumbuddy.lambda.logLambda;

import com.clifm.se.capstone.aquariumbuddy.activity.requests.logrequest.CreateLogRequest;
import com.clifm.se.capstone.aquariumbuddy.activity.results.logresults.CreateLogResult;
import com.clifm.se.capstone.aquariumbuddy.lambda.AuthenticatedLambdaRequest;
import com.clifm.se.capstone.aquariumbuddy.lambda.LambdaActivityRunner;
import com.clifm.se.capstone.aquariumbuddy.lambda.LambdaResponse;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;


public class CreateLogLambda
        extends LambdaActivityRunner<CreateLogRequest, CreateLogResult>
        implements RequestHandler<AuthenticatedLambdaRequest<CreateLogRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<CreateLogRequest> input, Context context) {
        return super.runActivity(() -> {
            CreateLogRequest stagedRequest = input.fromBody(CreateLogRequest.class);

            return input.fromUserClaims(claims -> CreateLogRequest.builder()
                    .withLog(stagedRequest.getLog())
                    .withUserEmail(claims.get("email"))
                    .build());
            }, (request, serviceComponent) ->
                        serviceComponent.provideCreateLogActivity().handleRequest(request)
        );
    }
}
