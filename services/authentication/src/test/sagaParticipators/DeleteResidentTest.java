package com.graduationProject.authentication.sagaParticipators;

import testFixtures.ResidentDtoFixture;
import testFixtures.SagaResidentDtoFixture;
import com.graduationProject.authentication.dto.ResidentDto;
import com.graduationProject.authentication.dto.saga.SagaResidentDto;
import com.graduationProject.authentication.kafka.KafkaApi;
import com.graduationProject.authentication.saga.sagaParticipators.DeleteResident;
import com.graduationProject.authentication.service.ResidentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class DeleteResidentTest {

    @InjectMocks
    private DeleteResident testee;
    @Mock
    private ResidentService residentService;
    @Mock
    private KafkaApi kafkaApi;

    private ResidentDto residentDto;
    private SagaResidentDto sagaResidentDto;

    @Before
    public void setup() {
        residentDto = ResidentDtoFixture.createResidentDto();
        sagaResidentDto = SagaResidentDtoFixture.createSagaResidentDto();
    }

    @Test
    public void DeleteResidentTest_transact_usernameIsNeverDelete_doesNotCallDeleteResident() {
        residentDto = ResidentDtoFixture.builder()
                .setUsername("neverDelete")
                .setPassword("1234")
                .build();
        sagaResidentDto = SagaResidentDtoFixture.builder()
                .setResidentDto(residentDto)
                .build();
        testee.transact(sagaResidentDto);
        verify(residentService, times(0)).deleteResident(any());
    }


    @Test
    public void DeleteResidentTest_transact_usernameIsNotNeverDelete_doesCallDeleteResident() {
        residentDto = ResidentDtoFixture.builder()
                .setUsername("test")
                .setPassword("1234")
                .build();
        sagaResidentDto = SagaResidentDtoFixture.builder()
                .setResidentDto(residentDto)
                .build();
        testee.transact(sagaResidentDto);
       verify(residentService, times(1)).deleteResident(any());
    }

}
