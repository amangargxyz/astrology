package com.project.astro.astrology.controllers;

import com.project.astro.astrology.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/astrology/client")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllClients() {
        return new ResponseEntity<>(clientService.getAllClients(), HttpStatus.OK);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<?> getClientById(@PathVariable("id") Long id) {
//        return new ResponseEntity<>(clientService.getClientById(id), HttpStatus.OK);
//    }
//
//    @PostMapping("/add")
//    public ResponseEntity<?> addClient(@RequestBody Client client) {
//        return new ResponseEntity<>(clientService.addClient(client), HttpStatus.OK);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateClient(@PathVariable("id") Long id, @RequestBody Client client) {
//        return new ResponseEntity<>(clientService.updateClient(id, client), HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteClient(@PathVariable("id") Long id) {
//        clientService.deleteClient(id);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
