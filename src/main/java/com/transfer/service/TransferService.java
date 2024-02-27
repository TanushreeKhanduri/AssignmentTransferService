package com.transfer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.transfer.advice.RestTemplateResponseErrorHandler;
import com.transfer.converter.TransferRequestToAccountRequestConverter;
import com.transfer.converter.TransferToAccountConverter;
import com.transfer.exception.BankAccountManagerException;
import com.transfer.exception.InsufficientBalanceManagerException;
import com.transfer.request.dto.AccountRequest;
import com.transfer.request.dto.TransferRequest;


@Service
public class TransferService {
	
	private static final String URI = "http://localhost:8080/api/account/";
	private static final String updateURI = URI+"update/";
	private RestTemplate restTemplate;

    @Autowired
    public TransferService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder
          .errorHandler(new RestTemplateResponseErrorHandler())
          .build();
    }
    
    public void tranfer(TransferRequest request) {
    	HttpEntity<AccountRequest> fromRequest = new HttpEntity<AccountRequest>(TransferRequestToAccountRequestConverter.convert(request));
    	if(restTemplate.exchange(URI+request.getToAccount().getCif(), HttpMethod.GET, null, Void.class).getStatusCodeValue() != 200) {
    		throw new BankAccountManagerException("To Account Customer does not exist");
    	}
    	int responseCode =restTemplate.exchange(updateURI+request.getFromAccount().getCif(), HttpMethod.PUT, fromRequest, Void.class).getStatusCodeValue();
    	
    	if(responseCode == 400) {
    		throw new BankAccountManagerException("From Account Customer does not exist");
    	} else if(responseCode == 406) {
    		throw new InsufficientBalanceManagerException("Insuffficient balance");
    	}
    	
    	HttpEntity<AccountRequest> toRequest = new HttpEntity<AccountRequest>(TransferToAccountConverter.convert(request));
    	restTemplate.exchange(updateURI+request.getToAccount().getCif(), HttpMethod.PUT, toRequest, Void.class);
    }
    
}

