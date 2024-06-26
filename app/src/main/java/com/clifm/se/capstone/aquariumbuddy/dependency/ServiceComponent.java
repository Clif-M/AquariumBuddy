package com.clifm.se.capstone.aquariumbuddy.dependency;

import com.clifm.se.capstone.aquariumbuddy.activity.fishActivities.CreateFishActivity;
import com.clifm.se.capstone.aquariumbuddy.activity.fishActivities.DeleteFishActivity;
import com.clifm.se.capstone.aquariumbuddy.activity.fishActivities.GetFishActivity;
import com.clifm.se.capstone.aquariumbuddy.activity.fishActivities.GetSingleFishActivity;
import com.clifm.se.capstone.aquariumbuddy.activity.fishActivities.UpdateFishActivity;

import com.clifm.se.capstone.aquariumbuddy.activity.logactivities.CreateLogActivity;
import com.clifm.se.capstone.aquariumbuddy.activity.logactivities.DeleteLogActivity;
import com.clifm.se.capstone.aquariumbuddy.activity.logactivities.GetLogsActivity;
import com.clifm.se.capstone.aquariumbuddy.activity.logactivities.GetLogsByTypeActivity;
import com.clifm.se.capstone.aquariumbuddy.activity.logactivities.GetLogsForTankActivity;
import com.clifm.se.capstone.aquariumbuddy.activity.logactivities.GetSingleLogActivity;
import com.clifm.se.capstone.aquariumbuddy.activity.logactivities.UpdateLogActivity;

import com.clifm.se.capstone.aquariumbuddy.activity.tankactivities.CreateTankActivity;
import com.clifm.se.capstone.aquariumbuddy.activity.tankactivities.DeleteTankActivity;
import com.clifm.se.capstone.aquariumbuddy.activity.tankactivities.GetSingleTankActivity;
import com.clifm.se.capstone.aquariumbuddy.activity.tankactivities.GetTanksActivity;
import com.clifm.se.capstone.aquariumbuddy.activity.tankactivities.UpdateTankActivity;

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

    /**
     * Provides the relevant activity.
     * @return UpdateLogActivity
     */
    UpdateLogActivity provideUpdateLogActivity();

    /**
     * Provides the relevant activity.
     * @return DeleteLogActivity
     */
    DeleteLogActivity provideDeleteLogActivity();

    /**
     * Provides the relevant activity.
     * @return GetSingleLogActivity
     */
    GetSingleLogActivity provideGetSingleLogActivity();

    /**
     * Provides the relevant activity.
     * @return GetLogsActivity
     */
    GetLogsActivity provideGetLogsActivity();

    /**
     * Provides the relevant activity.
     * @return GetLogsByTypeActivity
     */
    GetLogsByTypeActivity provideGetLogsByTypeActivity();

    /**
     * Provides the relevant activity.
     * @return CreateTankActivity
     */
    CreateTankActivity provideCreateTankActivity();

    /**
     * Provides the relevant activity.
     * @return GetTanksActivity
     */
    GetTanksActivity provideGetTanksActivity();

    /**
     * Provides the relevant activity.
     * @return GetSingleTankActivity
     */
    GetSingleTankActivity provideGetSingleTankActivity();

    /**
     * Provides the relevant activity.
     * @return DeleteTankActivity
     */
    DeleteTankActivity provideDeleteTankActivity();

    /**
     * Provides the relevant activity.
     * @return UpdateTankActivity
     */
    UpdateTankActivity provideUpdateTankActivity();

}
