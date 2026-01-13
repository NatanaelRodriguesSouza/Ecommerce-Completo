package Ecommerce_Completo.service;

import Ecommerce_Completo.model.Acesso;
import Ecommerce_Completo.model.DTO.AcessoDTO;
import Ecommerce_Completo.repository.AcessoRepository;
import Ecommerce_Completo.service.excepetions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AcessoService {

    private final AcessoRepository repository;

    public AcessoService(AcessoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public AcessoDTO insert(AcessoDTO dto) {
        Objects.requireNonNull(dto, "DTO não pode ser nulo.");
        if (dto.getDescricao() == null || dto.getDescricao().isBlank()) {
            throw new IllegalArgumentException("Descrição não pode ser vazia.");
        }

        Acesso acesso = new Acesso();
        acesso.setDescricao(dto.getDescricao());
        acesso = repository.save(acesso);

        return new AcessoDTO(acesso.getId(), acesso.getDescricao());
    }

    @Transactional(readOnly = true)
    public List<AcessoDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(a -> new AcessoDTO(a.getId(), a.getDescricao()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AcessoDTO findById(Long id) {
        if (id == null) throw new ResourceNotFoundException("ID não pode ser nulo.");

        Acesso acesso = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Acesso não encontrado. ID: " + id));

        return new AcessoDTO(acesso.getId(), acesso.getDescricao());
    }

    @Transactional(readOnly = true)
    public AcessoDTO findByDescricao(String descricao) {
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("Descrição não pode ser vazia.");
        }

        Acesso acesso = repository.findByDescricao(descricao)
                .orElseThrow(() -> new EntityNotFoundException("Acesso não encontrado. Descrição: " + descricao));

        return new AcessoDTO(acesso.getId(), acesso.getDescricao());
    }

    @Transactional
    public AcessoDTO update(Long id, AcessoDTO dto) {
        if (id == null) throw new IllegalArgumentException("ID não pode ser nulo.");
        Objects.requireNonNull(dto, "DTO não pode ser nulo.");
        if (dto.getDescricao() == null || dto.getDescricao().isBlank()) {
            throw new IllegalArgumentException("Descrição não pode ser vazia.");
        }

        Acesso acesso = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Acesso não encontrado. ID: " + id));

        acesso.setDescricao(dto.getDescricao());
        acesso = repository.save(acesso);

        return new AcessoDTO(acesso.getId(), acesso.getDescricao());
    }

    @Transactional
    public void delete(Long id) {
        if (id == null) throw new IllegalArgumentException("ID não pode ser nulo.");

        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Acesso não encontrado. ID: " + id);
        }
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<AcessoDTO> findPaged(String descricao, Pageable pageable) {
        Page<Acesso> page;

        if (descricao == null || descricao.isBlank()) {
            page = repository.findAll(pageable);
        } else {
            page = repository.findByDescricaoContainingIgnoreCase(descricao, pageable);
        }

        return page.map(a -> new AcessoDTO(a.getId(), a.getDescricao()));
    }
}
