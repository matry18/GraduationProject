package com.graduationproject.bosted.service;

import com.graduationproject.bosted.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final CreateEmployee...


    // private final ResidentRepository residentRepository;
    //
    //    private final CreateResident createResident;
    //    private final DeleteResident deleteResident;
    //    private final UpdateResident updateResident;
    //
    //    @Autowired
    //    public ResidentService(ResidentRepository residentRepository, CreateResident createResident, DeleteResident deleteResident, UpdateResident updateResident) {
    //        this.residentRepository = residentRepository;
    //        this.createResident = createResident;
    //        this.deleteResident = deleteResident;
    //        this.updateResident = updateResident;
    //    }
    //
    //    public void addCitizen(ResidentDto residentDto) {
    //        residentDto.setId(UUID.randomUUID().toString());
    //        Resident resident = residentRepository.save(new Resident(residentDto));
    //        createResident.initSaga(new ResidentDto(resident));
    //    }
    //
    //
    //    public List<Resident> getAllCitizen() {
    //        return residentRepository.findAll();
    //    }
    //
    //    @Transactional
    //    public Resident editCitizen(ResidentDto residentDto) {
    //        Resident resident = residentRepository.findById(residentDto.getId()).orElse(null);
    //        ResidentDto oldResident = new ResidentDto(resident);
    //        resident.setFirstname(residentDto.getFirstname());
    //        resident.setLastname(residentDto.getLastname());
    //        resident.setDepartment(new Department(residentDto.getDepartment()));
    //        resident.setEmail(residentDto.getEmail());
    //        resident.setPhoneNumber(resident.getPhoneNumber());
    //        residentRepository.save(resident);
    //        ResidentDto newResident = new ResidentDto(resident);
    //        updateResident.initSaga(oldResident, newResident);
    //
    //        return resident;
    //    }
    //
    //    public Resident deleteCitizen(String citizenId) {
    //        Resident resident = residentRepository.getById(citizenId);
    //        residentRepository.deleteById(citizenId);
    //        deleteResident.initSaga(new ResidentDto(resident));
    //        return resident;
    //    }
}
