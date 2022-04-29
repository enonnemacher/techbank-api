package com.wipro.techbank.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wipro.techbank.domain.AccountType;
import com.wipro.techbank.domain.Operation;
import com.wipro.techbank.dtos.TransactionRequestDto;
import com.wipro.techbank.dtos.TransactionResponseDto;
import com.wipro.techbank.dtos.TransactionResponseExtractDto;
import com.wipro.techbank.dtos.TransactionResponseOperationDto;
import com.wipro.techbank.services.TransactionService;
import com.wipro.techbank.services.exceptions.ResourceNotFoundException;
import com.wipro.techbank.tests.FactoryTransactions;
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

import java.time.LocalDateTime;
import java.util.List;

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

    private TransactionRequestDto transactionRequestDto;
    private TransactionResponseExtractDto transactionResponseExtractDto;
    private long existingId;

    @BeforeEach
    void setUp() throws Exception{
        Double DEPOSIT_VALUE = 200.00;
        TransactionResponseDto transactionResponseDto = new TransactionResponseDto();
        LocalDateTime createAt = LocalDateTime.of(2022, 04, 13, 0, 0);
        transactionResponseDto.setId(1L);
        transactionResponseDto.setCreatedAt(createAt);
        transactionResponseDto.setAccountType(AccountType.CHECKING_ACCOUNT);
        transactionResponseDto.setOperation(Operation.DEPOSIT);
        transactionResponseDto.setValue(DEPOSIT_VALUE);

        transactionRequestDto = new TransactionRequestDto();

        transactionRequestDto.setAccountType(AccountType.CHECKING_ACCOUNT);
        transactionRequestDto.setValue(DEPOSIT_VALUE);


        TransactionResponseOperationDto transactionResponseOperationDto = new TransactionResponseOperationDto();
        transactionResponseOperationDto.setId(1L);
        transactionResponseOperationDto.setCreatedAt(createAt);
        transactionResponseOperationDto.setAccountType(AccountType.CHECKING_ACCOUNT);
        transactionResponseOperationDto.setOperation(Operation.DEPOSIT);
        transactionResponseOperationDto.setValue(DEPOSIT_VALUE);

        transactionResponseExtractDto = new TransactionResponseExtractDto();
        transactionResponseExtractDto.setValue(200.00);
        transactionResponseExtractDto.setOperation(Operation.DEPOSIT);
        transactionResponseExtractDto.setCreatedAt(createAt);

        PageImpl<TransactionResponseDto> page = new PageImpl<>(List.of(transactionResponseDto));
        existingId = 1L;
        long nonExistingId = 2L;


        when(transactionService.findAllPaged(any())).thenReturn(page);

        when(transactionService.findById(existingId)).thenReturn(transactionResponseDto);
        when(transactionService.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);

        when(transactionService.deposit(existingId, transactionRequestDto)).thenReturn(transactionResponseOperationDto);
        when(transactionService.withdraw(existingId, transactionRequestDto)).thenReturn(transactionResponseOperationDto);
        when(transactionService.extract(existingId)).thenReturn(List.of(transactionResponseExtractDto));
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
        result.andExpect(jsonPath("$.createdAt").exists());

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
        result.andExpect(jsonPath("$.createdAt").exists());
    }

    @Test
    void extracts() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(transactionRequestDto);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/transactions/accounts/{id}/extracts", existingId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
    }

    @Test
    void withdraw() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(transactionResponseExtractDto);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/transactions/withdrawals/{id}", existingId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
    }
}