package com.wipro.techbank.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wipro.techbank.dtos.TransactionRequestDto;
import com.wipro.techbank.dtos.TransactionResponseDto;
import com.wipro.techbank.dtos.TransactionResponseOperationDto;
import com.wipro.techbank.services.TransactionService;
import com.wipro.techbank.services.exceptions.ResourceNotFoundException;
import com.wipro.techbank.tests.FactoryTransactions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Autowired
    private ObjectMapper objectMapper;

    private PageImpl<TransactionResponseDto> page;
    private TransactionResponseDto transactionResponseDto;
    private TransactionRequestDto transactionRequestDto;
    private TransactionResponseOperationDto transactionResponseOperationDto;
    private long existingId;
    private long nonExistingId;
    private long depndentId;

    @BeforeEach
    void setUp() throws Exception{
        transactionResponseDto = FactoryTransactions.TRANSACTION_RESPONSE_DTO;
        transactionRequestDto = FactoryTransactions.TRANSACTION_REQUEST_DTO;
        transactionResponseOperationDto = FactoryTransactions.TRANSACTION_RESPONSE_OPERATION_DTO;
        page = new PageImpl<>(List.of(transactionResponseDto));
        existingId = 1L;
        nonExistingId = 2L;
        depndentId = 3;

        when(transactionService.findAllPaged(any())).thenReturn(page);

        when(transactionService.findById(existingId)).thenReturn(transactionResponseDto);
        when(transactionService.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);

        when(transactionService.deposit(existingId, transactionRequestDto)).thenReturn(transactionResponseOperationDto);
    }

    @Test
    void findAllShouldReturnPage() throws Exception {
        ResultActions result = mockMvc.perform(get("/transactions")
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
    }

    @Test
    void findByIdShouldReturnTransactionResponseDtoWhenIdExixts() throws Exception {
        ResultActions result = mockMvc.perform(get("/transactions/{id}", existingId)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.accountType").exists());
        result.andExpect(jsonPath("$.operation").exists());
        result.andExpect(jsonPath("$.createAt").exists());

    }

    @Test
    void deposit() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(transactionRequestDto);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/transactions/deposits/{id}", existingId)
                        .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.accountType").exists());
        result.andExpect(jsonPath("$.operation").exists());
        result.andExpect(jsonPath("$.createAt").exists());
    }

    @Test
    void withdraw() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(transactionRequestDto);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/transactions/withdrawals/{id}", existingId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
    }
}