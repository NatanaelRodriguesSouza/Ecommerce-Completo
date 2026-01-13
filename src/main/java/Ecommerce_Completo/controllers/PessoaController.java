package Ecommerce_Completo.controllers;

import Ecommerce_Completo.model.DTO.PessoaFisicaDTO;
import Ecommerce_Completo.model.DTO.PessoaJuridicaDTO;
import Ecommerce_Completo.service.PessoaFisicaService;
import Ecommerce_Completo.service.PessoaJuridicaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    private final PessoaFisicaService pessoaFisicaService;
    private final PessoaJuridicaService pessoaJuridicaService;

    public PessoaController(PessoaFisicaService pessoaFisicaService,
                            PessoaJuridicaService pessoaJuridicaService) {
        this.pessoaFisicaService = pessoaFisicaService;
        this.pessoaJuridicaService = pessoaJuridicaService;
    }


    @PostMapping("/fisicas")
    public ResponseEntity<PessoaFisicaDTO> createFisica(@Valid @RequestBody PessoaFisicaDTO dto) {
        PessoaFisicaDTO created = pessoaFisicaService.insert(dto);
        URI location = URI.create("/pessoas/fisicas/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/fisicas/{id}")
    public ResponseEntity<PessoaFisicaDTO> updateFisica(@PathVariable Long id,
                                                        @Valid @RequestBody PessoaFisicaDTO dto) {
        dto.setId(id);
        PessoaFisicaDTO updated = pessoaFisicaService.update(dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/fisicas/{id}")
    public ResponseEntity<PessoaFisicaDTO> findFisicaById(@PathVariable Long id) {
        return ResponseEntity.ok(pessoaFisicaService.findByID(id));
    }

    @GetMapping("/fisicas")
    public ResponseEntity<List<PessoaFisicaDTO>> findAllFisicas() {
        return ResponseEntity.ok(pessoaFisicaService.findAll());
    }

    @GetMapping("/fisicas/busca")
    public ResponseEntity<List<PessoaFisicaDTO>> findFisicasByName(@RequestParam String nome) {
        return ResponseEntity.ok(pessoaFisicaService.findByName(nome));
    }

    @GetMapping("/fisicas/cpf/{cpf}")
    public ResponseEntity<PessoaFisicaDTO> findFisicaByCpf(@PathVariable String cpf) {
        return ResponseEntity.ok(pessoaFisicaService.findByCpf(cpf));
    }

    @GetMapping("/fisicas/paged")
    public ResponseEntity<Page<PessoaFisicaDTO>> findFisicasPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(pessoaFisicaService.findPage(page, size));
    }

    @DeleteMapping("/fisicas/{id}")
    public ResponseEntity<Void> deleteFisica(@PathVariable Long id) {
        pessoaFisicaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/juridicas")
    public ResponseEntity<PessoaJuridicaDTO> createJuridica(@Valid @RequestBody PessoaJuridicaDTO dto) {
        PessoaJuridicaDTO created = pessoaJuridicaService.insert(dto);
        URI location = URI.create("/pessoas/juridicas/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/juridicas/{id}")
    public ResponseEntity<PessoaJuridicaDTO> updateJuridica(@PathVariable Long id,
                                                            @Valid @RequestBody PessoaJuridicaDTO dto) {
        dto.setId(id);
        PessoaJuridicaDTO updated = pessoaJuridicaService.update(dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/juridicas/{id}")
    public ResponseEntity<PessoaJuridicaDTO> findJuridicaById(@PathVariable Long id) {
        return ResponseEntity.ok(pessoaJuridicaService.findById(id));
    }

    @GetMapping("/juridicas")
    public ResponseEntity<List<PessoaJuridicaDTO>> findAllJuridicas() {
        return ResponseEntity.ok(pessoaJuridicaService.findAll());
    }

    @GetMapping("/juridicas/busca")
    public ResponseEntity<List<PessoaJuridicaDTO>> findJuridicasByName(@RequestParam String nome) {
        return ResponseEntity.ok(pessoaJuridicaService.findByName(nome));
    }

    @GetMapping("/juridicas/cnpj/{cnpj}")
    public ResponseEntity<PessoaJuridicaDTO> findJuridicaByCnpj(@PathVariable String cnpj) {
        return ResponseEntity.ok(pessoaJuridicaService.findByCnpj(cnpj));
    }

    @GetMapping("/juridicas/paged")
    public ResponseEntity<Page<PessoaJuridicaDTO>> findJuridicasPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(pessoaJuridicaService.findPage(page, size));
    }

    @DeleteMapping("/juridicas/{id}")
    public ResponseEntity<Void> deleteJuridica(@PathVariable Long id) {
        pessoaJuridicaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
