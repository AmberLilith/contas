package br.com.amber.contas.controllers;

import br.com.amber.contas.dtos.ClientDto;
import br.com.amber.contas.models.Client;
import br.com.amber.contas.services.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/clientes/")
public class ClientController {

    @Autowired
    ClientService service;

    @PostMapping("/cadastrar")
    public ResponseEntity<ClientDto> save(@RequestBody @Valid ClientDto clientDto){
        Client client = new Client();
        BeanUtils.copyProperties(clientDto, client);
        service.save(client);
        return new ResponseEntity<>(clientDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDto> update(@RequestBody @Valid ClientDto clientDto, @PathVariable Long id){
        ClientDto savedClient = service.update(clientDto, id);
        return new ResponseEntity<>(savedClient, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/ativos")
    public ResponseEntity<Page<ClientDto>> findByStatus(Pageable pageable) {
        return ResponseEntity.ok(service.findByStatus(pageable));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClientDto> disableClient(@PathVariable Long id){
        return ResponseEntity.ok(service.disableClient(id));
    }
}
