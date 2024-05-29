package com.nashss.se.capstone.aquariumbuddy.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.FishDao;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Fish;
import com.clifm.se.capstone.aquariumbuddy.exceptions.FishNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
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
    @Mock
    private PaginatedQueryList<Fish> paginatedQueryList;
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

    @Test
    public void getSingleFish_noMatchingFish_FishNotFoundException() {
        //GIVEN
        doReturn(null).when(mapper).load(Fish.class, fish.getUserEmail(), fish.getFishId());

        //WHEN
        //THEN
        assertThrows(FishNotFoundException.class, () -> fishDao.getSingleFish(fish.getUserEmail(), fish.getFishId()),
                "Expected method to throw error");
    }

    @Test
    public void getAllFish_anyUser_returnsList() {
        //GIVEN
        ArgumentCaptor<DynamoDBQueryExpression<Fish>> argumentCaptor = ArgumentCaptor.forClass(DynamoDBQueryExpression.class);
        doReturn(paginatedQueryList).when(mapper).query(eq(Fish.class),any(DynamoDBQueryExpression.class));
        //WHEN
        fishDao.getFish(fish.getUserEmail());
        verify(mapper).query(eq(Fish.class),argumentCaptor.capture());

        //THEN
        assertEquals(fish.getUserEmail(), argumentCaptor.getValue().getHashKeyValues().getUserEmail(), "Expected method to pass provided values to mapper");

    }

    @Test
    public void writeFish_anyFish_interactsWithSaveMethod() {
        //GIVEN
        //WHEN
        fishDao.writeFish(fish);
        //THEN
        verify(mapper).save(fish);
    }

    @Test
    public void deleteProject_anyProject_interactsWithDeleteMethod() {
        //GIVEN
        //WHEN
        fishDao.deleteFish(fish.getUserEmail(), fish.getFishId());

        //THEN
        verify(mapper).delete(any());
    }
}