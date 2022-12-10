package br.com.amber.contas.controllers;

import br.com.amber.contas.dtos.ClientDto;
import br.com.amber.contas.dtos.ClientPatchDto;
import br.com.amber.contas.models.Client;
import br.com.amber.contas.services.ClientService;
import ch.qos.logback.core.joran.util.beans.BeanUtil;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
        return ResponseEntity.ok().body(clientDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id).get());
    }

    @PatchMapping("/alterar_nome/{id}")
    public ResponseEntity<ClientDto> updateName(@PathVariable Long id,
                                                @RequestBody ClientPatchDto clientPatchDto){
        return ResponseEntity.ok(service.updateName(id, clientPatchDto));
    }
}
