package com.graduationProject.authentication.service;

import com.graduationProject.authentication.dto.ResidentDto;
import com.graduationProject.authentication.entity.Resident;
import com.graduationProject.authentication.repository.ResidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResidentService {

    private final ResidentRepository residentRepository;

    @Autowired
    public ResidentService( ResidentRepository residentRepository) {
        this.residentRepository = residentRepository;
    }


    public void addResident(ResidentDto residentDto) {
        residentRepository.save(new Resident(residentDto));
    }


    public void deleteResident(String residentId) {
        //throw new Exception("Could not revert creation of resident");
        residentRepository.deleteById(residentId);
    }

    public boolean residentExists(String residentId) {
        return residentRepository.existsResidentById(residentId);
    }

    private boolean doesResidentExists(String id) {
        return residentRepository.existsResidentById(id);
    }

    public void deleteIfExists(String id) {
        if (doesResidentExists(id)) {
            deleteResident(id);
        }
    }
}
