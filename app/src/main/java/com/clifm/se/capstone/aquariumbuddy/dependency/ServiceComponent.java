package com.clifm.se.capstone.aquariumbuddy.dependency;

import com.clifm.se.capstone.aquariumbuddy.activity.fishActivities.CreateFishActivity;
import com.clifm.se.capstone.aquariumbuddy.activity.fishActivities.GetSingleFishActivity;

import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = DaoModule.class)
public interface ServiceComponent {

    /**
     * Provides the relevant activity.
     * @return GetSingleFishActivity
     */
    GetSingleFishActivity provideGetSingleFishActivity();

    /**
     * Provides the relevant activity.
     * @return CreateFishActivity
     */
    CreateFishActivity provideCreateFishActivity();
}
