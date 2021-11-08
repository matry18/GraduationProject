package com.graduationproject.ochestrator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduationproject.ochestrator.dto.saga.SagaResponseDto;
import com.graduationproject.ochestrator.kafka.consumers.ConsumerHelper;
import com.graduationproject.ochestrator.saga.SagaParticipator;
import com.graduationproject.ochestrator.type.SagaStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import com.graduationproject.ochestrator.TestFixtures.*;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class ConsumerHelperTest {

    private ConsumerHelper<Object> testee;

    @Mock
    private SagaParticipator<Object> sagaParticipator;

    private List<String> services = Collections.singletonList("test");
    private SagaResponseDto sagaResponseDto;

    @Before
    public void setup() {
        testee = new ConsumerHelper<Object>(sagaParticipator, services, Object.class);
        sagaResponseDto = SagaResponseDtoFixture.createSagaResponseDto();
    }

    @Test
    public void consumerHelperTest_sagaDone_sagaResponseSagaStatusIsFAILED_callsRevertMethod() {
        sagaResponseDto = SagaResponseDtoFixture.builder()
                .setSagaStatus(SagaStatus.FAILED)
                .build();
        try {
            String sagaResponseMessage = new ObjectMapper().writeValueAsString(sagaResponseDto);
            testee.sagaDone(sagaResponseMessage, "notImportantForThisTest");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        verify(sagaParticipator, times(1)).revert(any());
    }


    @Test
    public void consumerHelperTest_sagaDone_sagaResponseSagaStatusIsFAILED_neverCallsRevertMethod() {
        sagaResponseDto = SagaResponseDtoFixture.builder()
                .setSagaStatus(SagaStatus.SUCCESS)
                .build();
        try {
            String sagaResponseMessage = new ObjectMapper().writeValueAsString(sagaResponseDto);
            testee.sagaDone(sagaResponseMessage, "notImportantForThisTest");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        verify(sagaParticipator, times(0)).revert(any());
    }
}
