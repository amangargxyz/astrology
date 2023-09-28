package com.project.astro.astrology.service;

import com.project.astro.astrology.dto.responseDto.ClientResponseDto;
import com.project.astro.astrology.model.Client;

import java.util.List;

public interface ClientService {
    List<ClientResponseDto> getAllClients();
    Client getClientById(Long id);
    Client addClient(Client client);
    Client updateClient(Long id, Client client);
    void deleteClient(Long id);
}
