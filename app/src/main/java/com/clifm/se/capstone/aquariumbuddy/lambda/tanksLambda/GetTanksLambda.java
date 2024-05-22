package com.clifm.se.capstone.aquariumbuddy.lambda.tanksLambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.clifm.se.capstone.aquariumbuddy.lambda.LambdaRequest;
import com.clifm.se.capstone.aquariumbuddy.lambda.LambdaResponse;

public class GetTanksLambda implements RequestHandler<LambdaRequest<String>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(LambdaRequest<String> input, Context context) {
        return LambdaResponse.success();
    }
}
