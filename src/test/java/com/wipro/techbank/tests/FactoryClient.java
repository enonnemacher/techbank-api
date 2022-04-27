package com.wipro.techbank.tests;

import com.wipro.techbank.domain.Client;
import com.wipro.techbank.dtos.ClientDto;

import java.util.ArrayList;
import java.util.List;

public class FactoryClient {
    private static final String CLIENT_NAME = "Fulano Beltrano dos Testes";
    private static final String CLIENT_CPF = "756.394.430-30";
    private static final String CLIENT_PHONE_NUMBER = "(10) 91998-9673";
    private static final String CLIENT_EMAIL = "fulano.beltrano.testes@techbank.com";

    public static final Client CLIENT_ENTITY = new Client(CLIENT_NAME, CLIENT_CPF, CLIENT_PHONE_NUMBER, CLIENT_EMAIL);
    public static final List<Client> CLIENTS_ENTITY_LIST = createClientList();
    public static final ClientDto CLIENT_DTO = new ClientDto(CLIENT_ENTITY);

    private static List<Client> createClientList() {
        List<Client> list = new ArrayList<>();
        for(int index = 1; index <= 10; index++) {
            Client client = new Client(CLIENT_NAME+index, CLIENT_CPF+(index-1), CLIENT_PHONE_NUMBER+(index-1), String.format("fulano.beltrano.testes%d@techbank.com", index));
            list.add(client);
        }
        return list;
    }
}
