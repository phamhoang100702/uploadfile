package com.example.demo.service;

import com.example.demo.model.Client;
import com.example.demo.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    ClientRepository clientRepository;

    public List<Client>  findAllClient(){
        return  clientRepository.findAll();
    }

    public Optional<Client> findById(Long id){
        return  clientRepository.findById(id);
    }
    public Client save(Client client){
        return clientRepository.save(client);
    }



}
