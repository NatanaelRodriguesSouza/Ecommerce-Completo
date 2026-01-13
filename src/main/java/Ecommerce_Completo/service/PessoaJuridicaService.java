package Ecommerce_Completo.service;

import Ecommerce_Completo.model.DTO.PessoaJuridicaDTO;
import Ecommerce_Completo.model.PessoaJuridica;
import Ecommerce_Completo.repository.PessoaJuridicaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PessoaJuridicaService {

    private final PessoaJuridicaRepository repository;

    public PessoaJuridicaService(PessoaJuridicaRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public PessoaJuridicaDTO insert(PessoaJuridicaDTO dto) {
        Objects.requireNonNull(dto, "DTO não pode ser nulo.");
        validateCnpjUnique(dto.getCnpj(), null);

        PessoaJuridica entity = new PessoaJuridica();
        fillEntity(entity, dto);

        entity = repository.save(entity);
        return toDTO(entity);
    }

    @Transactional
    public PessoaJuridicaDTO update(PessoaJuridicaDTO dto) {
        Objects.requireNonNull(dto, "DTO não pode ser nulo.");
        if (dto.getId() == null) {
            throw new IllegalArgumentException("ID é obrigatório para atualização.");
        }

        PessoaJuridica entity = repository.findById(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Pessoa Jurídica não encontrada. ID: " + dto.getId()));

        validateCnpjUnique(dto.getCnpj(), dto.getId());
        fillEntity(entity, dto);

        entity = repository.save(entity);
        return toDTO(entity);
    }

    @Transactional(readOnly = true)
    public PessoaJuridicaDTO findById(Long id) {
        if (id == null) throw new IllegalArgumentException("ID não pode ser nulo.");

        PessoaJuridica entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pessoa Jurídica não encontrada. ID: " + id));

        return toDTO(entity);
    }

    @Transactional(readOnly = true)
    public List<PessoaJuridicaDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PessoaJuridicaDTO> findByName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }

        return repository.findByName(name.toUpperCase())
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PessoaJuridicaDTO findByCnpj(String cnpj) {
        if (cnpj == null || cnpj.isBlank()) {
            throw new IllegalArgumentException("CNPJ não pode ser vazio.");
        }

        PessoaJuridica entity = repository.findByCnpj(cnpj)
                .orElseThrow(() -> new EntityNotFoundException("CNPJ não encontrado: " + cnpj));

        return toDTO(entity);
    }

    @Transactional(readOnly = true)
    public Page<PessoaJuridicaDTO> findPage(int page, int size) {
        if (page < 0) throw new IllegalArgumentException("page não pode ser negativo.");
        if (size <= 0) throw new IllegalArgumentException("size deve ser maior que zero.");

        return repository.findPage(PageRequest.of(page, size))
                .map(this::toDTO);
    }

    @Transactional
    public void delete(Long id) {
        if (id == null) throw new IllegalArgumentException("ID não pode ser nulo.");

        PessoaJuridica entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pessoa Jurídica não encontrada. ID: " + id));

        repository.delete(entity);
    }

    private void validateCnpjUnique(String cnpj, Long currentId) {
        if (cnpj == null || cnpj.isBlank()) {
            throw new IllegalArgumentException("CNPJ não pode ser vazio.");
        }

        repository.findByCnpj(cnpj)
                .filter(found -> currentId == null || !found.getId().equals(currentId))
                .ifPresent(found -> { throw new IllegalArgumentException("CNPJ já cadastrado: " + cnpj); });
    }

    private void fillEntity(PessoaJuridica entity, PessoaJuridicaDTO dto) {
        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
        entity.setTelefone(dto.getTelefone());

        entity.setCnpj(dto.getCnpj());
        entity.setInscEstadual(dto.getInscEstadual());
        entity.setInscMunicipal(dto.getInscMunicipal());
        entity.setNomeFantasia(dto.getNomeFantasia());
        entity.setRazaoSocial(dto.getRazaoSocial());
        entity.setCategoria(dto.getCategoria());

        entity.setTipoPessoa("JURIDICA");
    }

    private PessoaJuridicaDTO toDTO(PessoaJuridica entity) {
        PessoaJuridicaDTO dto = new PessoaJuridicaDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setEmail(entity.getEmail());
        dto.setTelefone(entity.getTelefone());

        dto.setCnpj(entity.getCnpj());
        dto.setInscEstadual(entity.getInscEstadual());
        dto.setInscMunicipal(entity.getInscMunicipal());
        dto.setNomeFantasia(entity.getNomeFantasia());
        dto.setRazaoSocial(entity.getRazaoSocial());
        dto.setCategoria(entity.getCategoria());

        return dto;
    }
}
