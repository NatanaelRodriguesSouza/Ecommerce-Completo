package Ecommerce_Completo.controllers;

import Ecommerce_Completo.model.Acesso;
import Ecommerce_Completo.model.DTO.AcessoDTO;
import Ecommerce_Completo.service.AcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/acesso")
public class AcessoController {
    @Autowired
    private AcessoService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<AcessoDTO> findById(@PathVariable Long id) {
        AcessoDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<AcessoDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public ResponseEntity<AcessoDTO> insert( @RequestBody AcessoDTO dto) {
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dto.getId())
                .toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AcessoDTO> update(
            @PathVariable Long id,
            @RequestBody AcessoDTO dto) {
        dto = service.update(id, dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/descricao")
    public ResponseEntity<AcessoDTO> findByDescricao(
            @RequestParam String descricao) {
        AcessoDTO dto = service.findByDescricao(descricao);
        return ResponseEntity.ok(dto);
    }
    @GetMapping("/page")
    public Page<AcessoDTO> findPaged(
            @RequestParam(required = false) String descricao,
            Pageable pageable) {

        return service.findPaged(descricao, pageable);
    }
}
