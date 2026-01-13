package Ecommerce_Completo.controllers;

import Ecommerce_Completo.model.DTO.AcessoDTO;
import Ecommerce_Completo.service.AcessoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/acesso")
public class AcessoController {

    private final AcessoService service;

    public AcessoController(AcessoService service) {
        this.service = service;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AcessoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_OPERATOR')")
    public ResponseEntity<List<AcessoDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public ResponseEntity<AcessoDTO> insert(@Valid @RequestBody AcessoDTO dto) {
        AcessoDTO created = service.insert(dto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(uri).body(created);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AcessoDTO> update(@PathVariable Long id,
                                            @Valid @RequestBody AcessoDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/descricao")
    public ResponseEntity<AcessoDTO> findByDescricao(@RequestParam String descricao) {
        return ResponseEntity.ok(service.findByDescricao(descricao));
    }

    @GetMapping("/page")
    public ResponseEntity<Page<AcessoDTO>> findPaged(@RequestParam(required = false) String descricao,
                                                     Pageable pageable) {
        return ResponseEntity.ok(service.findPaged(descricao, pageable));
    }
}
