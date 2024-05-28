package com.nashss.se.capstone.aquariumbuddy;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.FishDao;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Fish;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class FishDaoTest {
    @Mock
    private DynamoDBMapper mapper;
    private FishDao fishDao;
    private Fish fish;


    @BeforeEach
    public void setup() {
        initMocks(this);
        fishDao = new FishDao(mapper);
        fish = new Fish();
        fish.setUserEmail("email@email.com");
        fish.setFishId("fish01");
    }

    @Test
    public void getSingleFish_MatchingFish_returnsFish() {
        //GIVEN
        doReturn(fish).when(mapper).load(Fish.class, fish.getUserEmail(), fish.getFishId());

        //WHEN
        Fish result = fishDao.getSingleFish(fish.getUserEmail(), fish.getFishId());

        //THEN
        assertEquals(fish.getFishId(), result.getFishId(), "Expected method to pass back object with matching values");
        assertEquals(fish.getUserEmail(), result.getUserEmail(), "Expected method to pass back object with matching values");
    }
}