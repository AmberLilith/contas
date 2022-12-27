package br.com.amber.contas.controllers;

import br.com.amber.contas.dtos.AccountDto;
import br.com.amber.contas.dtos.ClientDto;
import br.com.amber.contas.models.Account;
import br.com.amber.contas.services.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@RestController
@RequestMapping("/v1/contas")
public class AccountController {

    @Autowired
    AccountService service;

    @PostMapping("/")
    private ResponseEntity<AccountDto> save(@RequestBody @Valid AccountDto accountDto){
        Account account = new Account();
        BeanUtils.copyProperties(accountDto, account);
        AccountDto accountDtoResponse =  service.save(account);
        return new ResponseEntity<>(accountDtoResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    private ResponseEntity<List<AccountDto>> findByClientId(@PathVariable Long id){
        List<AccountDto> accountDtoList = service.findByClientId(id);
        return new ResponseEntity<>(accountDtoList, HttpStatus.OK);
    }
}
