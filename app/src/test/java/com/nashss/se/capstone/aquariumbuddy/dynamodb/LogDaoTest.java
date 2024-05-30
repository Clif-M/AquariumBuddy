package com.nashss.se.capstone.aquariumbuddy.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.LogDao;
import com.clifm.se.capstone.aquariumbuddy.dynamodb.models.Log;
import com.clifm.se.capstone.aquariumbuddy.exceptions.LogNotFoundException;
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

public class LogDaoTest {
    @Mock
    private DynamoDBMapper mapper;
    @Mock
    private PaginatedQueryList<Log> paginatedQueryList;
    private LogDao logDao;
    private Log log;


    @BeforeEach
    public void setup() {
        initMocks(this);
        logDao = new LogDao(mapper);
        log = new Log();
        log.setUserEmail("email@email.com");
        log.setLogId("log01");
        log.setTankId("tankId");
        log.setType("type");
    }

    @Test
    public void getSingleLog_MatchingLog_returnsLog() {
        //GIVEN
        doReturn(log).when(mapper).load(Log.class, log.getUserEmail(), log.getLogId());

        //WHEN
        Log result = logDao.getSingleLog(log.getUserEmail(), log.getLogId());

        //THEN
        assertEquals(log.getLogId(), result.getLogId(), "Expected method to pass back object with matching values");
        assertEquals(log.getUserEmail(), result.getUserEmail(), "Expected method to pass back object with matching values");
    }

    @Test
    public void getSingleLog_noMatchingLog_LogNotFoundException() {
        //GIVEN
        doReturn(null).when(mapper).load(Log.class, log.getUserEmail(), log.getLogId());

        //WHEN
        //THEN
        assertThrows(LogNotFoundException.class, () -> logDao.getSingleLog(log.getUserEmail(), log.getLogId()),
                "Expected method to throw error");
    }

    @Test
    public void getAllLogForTank_anyTank_returnsList() {
        //GIVEN
        ArgumentCaptor<DynamoDBQueryExpression<Log>> argumentCaptor = ArgumentCaptor.forClass(DynamoDBQueryExpression.class);
        doReturn(paginatedQueryList).when(mapper).query(eq(Log.class),any(DynamoDBQueryExpression.class));
        //WHEN
        logDao.getLogsforTank(log.getUserEmail());
        //THEN
        verify(mapper).query(eq(Log.class),argumentCaptor.capture());
    }

    @Test
    public void writeLog_anyLog_interactsWithSaveMethod() {
        //GIVEN
        //WHEN
        logDao.writeLog(log);
        //THEN
        verify(mapper).save(log);
    }

    @Test
    public void deleteProject_anyProject_interactsWithDeleteMethod() {
        //GIVEN
        //WHEN
        logDao.deleteLog(log.getUserEmail(), log.getLogId());

        //THEN
        verify(mapper).delete(any());
    }
}