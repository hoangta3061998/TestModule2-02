package com.codegym.service;

import com.codegym.model.Receptionist;
import com.codegym.repository.ReceptionistRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ReceptionistService implements GeneralService<Receptionist> {
    @Autowired
    private ReceptionistRepository receptionistRepository;

    @Override
    public List<Receptionist> findAll() {
        return receptionistRepository.findAll();
    }

    @Override
    public void add(Receptionist receptionist) {
        receptionistRepository.add(receptionist);
    }

    @Override
    public void update(int id, Receptionist receptionist) {
        receptionistRepository.update(id, receptionist);
    }

    @Override
    public void delete(int id) {
        receptionistRepository.delete(id);
    }

    @Override
    public Receptionist findByName(String name) {
        return receptionistRepository.findByName(name);
    }
    @Override
    public Receptionist findById(int id){
        return receptionistRepository.findById(id);
    }
}
