package testFixtures;

import com.graduationProject.authentication.dto.ResidentDto;

public class ResidentDtoFixture {
    private String username = "JohnDoe";
    private String password = "secretPassword";

    public static ResidentDto createResidentDto() {
        return new ResidentDtoFixture().build();
    }

    public static ResidentDtoFixture builder() {
        return new ResidentDtoFixture();
    }

    public ResidentDto build() {
        ResidentDto residentDto = new ResidentDto();
        residentDto.setUsername(username);
        residentDto.setPassword(password);
        return residentDto;
    }

    public ResidentDtoFixture setUsername(String username) {
        this.username = username;
        return this;
    }

    public ResidentDtoFixture setPassword(String password) {
        this.password = password;
        return this;
    }
}


