package com.clifm.se.capstone.aquariumbuddy.lambda.logLambda;

import com.clifm.se.capstone.aquariumbuddy.activity.requests.logrequest.GetLogsByTypeRequest;
import com.clifm.se.capstone.aquariumbuddy.activity.results.logresults.GetLogsByTypeResult;
import com.clifm.se.capstone.aquariumbuddy.lambda.AuthenticatedLambdaRequest;
import com.clifm.se.capstone.aquariumbuddy.lambda.LambdaActivityRunner;
import com.clifm.se.capstone.aquariumbuddy.lambda.LambdaResponse;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetLogsByTypeLambda
        extends LambdaActivityRunner<GetLogsByTypeRequest, GetLogsByTypeResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetLogsByTypeRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetLogsByTypeRequest> input, Context context) {
        return super.runActivity(
            () -> input.fromPath(path ->
                    GetLogsByTypeRequest.builder()
                            .withTankId(path.get("tankId"))
                            .withFlavor(path.get("flavor"))
                            .build()),
            (request, serviceComponent) ->
                    serviceComponent.provideGetLogsByTypeActivity().handleRequest(request)
        );
    }
}
