package testFixtures;

import com.graduationProject.authentication.dto.ResidentDto;
import com.graduationProject.authentication.dto.saga.SagaResidentDto;

public class SagaResidentDtoFixture {

    private String sagaId;
    private ResidentDto residentDto = ResidentDtoFixture.createResidentDto();

    public static SagaResidentDto createSagaResidentDto() {
        return new SagaResidentDtoFixture().build();
    }

    public static SagaResidentDtoFixture builder() {
        return new SagaResidentDtoFixture();
    }

    public SagaResidentDto build() {
        SagaResidentDto sagaResidentDto = new SagaResidentDto();
        sagaResidentDto.setResidentDto(residentDto);
        sagaResidentDto.setSagaId(sagaId);
        return sagaResidentDto;
    }

    public SagaResidentDtoFixture setSagaId(String sagaId) {
        this.sagaId = sagaId;
        return this;
    }

    public SagaResidentDtoFixture setResidentDto(ResidentDto residentDto) {
        this.residentDto = residentDto;
        return this;
    }

}
