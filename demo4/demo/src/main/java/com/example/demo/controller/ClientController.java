package com.example.demo.controller;

import com.example.demo.model.Client;
import com.example.demo.model.ResponseObject;
import com.example.demo.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/Client")
public class ClientController {
    @Autowired
    ClientService clientService;

    @GetMapping("")
    ResponseEntity<ResponseObject> findAll() {
        List<Client> clientList = clientService.findAllClient();
        if (!clientList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Tat ca client", clientList)
            );
        } else {
            return ResponseEntity.badRequest().body(
                    new ResponseObject("NOT FOUND", "Khong tim thay", "{}"));
        }
    }

    @PostMapping("")
    ResponseEntity<ResponseObject> save(@RequestBody Client client) {
        System.out.println(client.toString());
        Client client1 = clientService.save(client);
        return ResponseEntity.ok(new ResponseObject("ok", "Da tim thay", client1));
    }
}
