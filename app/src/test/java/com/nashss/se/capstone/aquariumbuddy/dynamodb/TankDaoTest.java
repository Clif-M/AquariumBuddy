package com.nashss.se.capstone.aquariumbuddy.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.TankDao;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Tank;
import com.clifm.se.capstone.aquariumbuddy.exceptions.TankNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class TankDaoTest {
    @Mock
    private DynamoDBMapper mapper;
    @Mock
    private PaginatedQueryList<Tank> paginatedQueryList;
    private TankDao tankDao;
    private Tank tank;


    @BeforeEach
    public void setup() {
        initMocks(this);
        tankDao = new TankDao(mapper);
        tank = new Tank();
        tank.setUserEmail("email@email.com");
        tank.setTankId("tank01");
    }

    @Test
    public void getSingleTank_MatchingTank_returnsTank() {
        //GIVEN
        doReturn(tank).when(mapper).load(Tank.class, tank.getUserEmail(), tank.getTankId());

        //WHEN
        Tank result = tankDao.getSingleTank(tank.getUserEmail(), tank.getTankId());

        //THEN
        assertEquals(tank.getTankId(), result.getTankId(), "Expected method to pass back object with matching values");
        assertEquals(tank.getUserEmail(), result.getUserEmail(), "Expected method to pass back object with matching values");
    }

    @Test
    public void getSingleTank_noMatchingTank_TankNotFoundException() {
        //GIVEN
        doReturn(null).when(mapper).load(Tank.class, tank.getUserEmail(), tank.getTankId());

        //WHEN
        //THEN
        assertThrows(TankNotFoundException.class, () -> tankDao.getSingleTank(tank.getUserEmail(), tank.getTankId()),
                "Expected method to throw error");
    }


    @Test
    public void writeTank_anyTank_interactsWithSaveMethod() {
        //GIVEN
        //WHEN
        tankDao.writeTank(tank);
        //THEN
        verify(mapper).save(tank);
    }

    @Test
    public void deleteTank_anyProject_interactsWithDeleteMethod() {
        //GIVEN
        //WHEN
        tankDao.deleteTank(tank.getUserEmail(), tank.getTankId());

        //THEN
        verify(mapper).delete(any());
    }

    @Test
    public void getTanks_something_somthing() {
        //GIVEN
        ArgumentCaptor<DynamoDBQueryExpression<Tank>> argumentCaptor = ArgumentCaptor.forClass(DynamoDBQueryExpression.class);
        doReturn(paginatedQueryList).when(mapper).query(eq(Tank.class),any(DynamoDBQueryExpression.class));
        //WHEN
        tankDao.getTanks(tank.getUserEmail());
        //THEN
        verify(mapper).query(eq(Tank.class),argumentCaptor.capture());
    }
}