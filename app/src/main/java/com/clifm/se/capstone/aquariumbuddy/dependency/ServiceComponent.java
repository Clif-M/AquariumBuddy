package com.clifm.se.capstone.aquariumbuddy.dependency;

import com.clifm.se.capstone.aquariumbuddy.activity.fishActivities.CreateFishActivity;
import com.clifm.se.capstone.aquariumbuddy.activity.fishActivities.DeleteFishActivity;
import com.clifm.se.capstone.aquariumbuddy.activity.fishActivities.GetFishActivity;
import com.clifm.se.capstone.aquariumbuddy.activity.fishActivities.GetSingleFishActivity;
import com.clifm.se.capstone.aquariumbuddy.activity.fishActivities.UpdateFishActivity;

import com.clifm.se.capstone.aquariumbuddy.activity.logactivities.CreateLogActivity;
import com.clifm.se.capstone.aquariumbuddy.activity.logactivities.GetLogsForTankActivity;

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

    /**
     * Provides the relevant activity.
     * @return UpdateFishActivity
     */
    UpdateFishActivity provideUpdateFishActivity();

    /**
     * Provides the relevant activity.
     * @return GetFishActivity
     */
    GetFishActivity provideGetFishActivity();

    /**
     * Provides the relevant activity.
     * @return DeleteFishActivity
     */
    DeleteFishActivity provideDeleteFishActivity();

    /**
     * Provides the relevant activity.
     * @return CreateLogActivity
     */
    CreateLogActivity provideCreateLogActivity();

    /**
     * Provides the relevant activity.
     * @return GetLogsForTankActivity
     */
    GetLogsForTankActivity provideGetLogsForTankActivity();


}
