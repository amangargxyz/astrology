package com.project.astro.astrology.service.impl;

import com.project.astro.astrology.dto.responseDto.ClientResponseDto;
import com.project.astro.astrology.mapper.ClientMapper;
import com.project.astro.astrology.model.Client;
import com.project.astro.astrology.repository.ClientRepository;
import com.project.astro.astrology.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientMapper clientMapper;

    @Override
    public List<ClientResponseDto> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        return clients.stream().map(clientMapper::entityToResponseDto).toList();
    }

    @Override
    public Client getClientById(Long id) {
        Optional<Client> opt = clientRepository.findById(id);
        return opt.orElse(null);
    }

    @Override
    public Client addClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Client updateClient(Long id, Client client) {
        Optional<Client> opt = clientRepository.findById(id);
        if(opt.isPresent()) {
            Client oldClient = opt.get();
            oldClient.setUser(client.getUser());

            return clientRepository.save(oldClient);
        }

        return null;
    }

    @Override
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}
