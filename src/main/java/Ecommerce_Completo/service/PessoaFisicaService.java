package Ecommerce_Completo.service;

import Ecommerce_Completo.model.DTO.PessoaFisicaDTO;
import Ecommerce_Completo.model.PessoaFisica;
import Ecommerce_Completo.model.PessoaJuridica;
import Ecommerce_Completo.repository.PessoaFisicaRepository;
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
public class PessoaFisicaService {

    private final PessoaFisicaRepository repository;
    private final PessoaJuridicaRepository pessoaJuridicaRepository;

    public PessoaFisicaService(PessoaFisicaRepository repository,
                               PessoaJuridicaRepository pessoaJuridicaRepository) {
        this.repository = repository;
        this.pessoaJuridicaRepository = pessoaJuridicaRepository;
    }

    @Transactional
    public PessoaFisicaDTO insert(PessoaFisicaDTO dto) {
        Objects.requireNonNull(dto, "DTO não pode ser nulo.");
        validateCpfUnique(dto.getCpf(), null);

        PessoaFisica entity = new PessoaFisica();
        fillEntity(entity, dto);

        entity = repository.save(entity);
        return toDTO(entity);
    }


    @Transactional
    public PessoaFisicaDTO update(PessoaFisicaDTO dto) {
        Objects.requireNonNull(dto, "DTO não pode ser nulo.");
        if (dto.getId() == null) {
            throw new IllegalArgumentException("ID é obrigatório para atualização.");
        }

        PessoaFisica entity = repository.findById(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Pessoa Física não encontrada. ID: " + dto.getId()));

        validateCpfUnique(dto.getCpf(), dto.getId());
        fillEntity(entity, dto);

        entity = repository.save(entity);
        return toDTO(entity);
    }

    @Transactional(readOnly = true)
    public PessoaFisicaDTO findByID(Long id) {
        if (id == null) throw new IllegalArgumentException("ID não pode ser nulo.");

        PessoaFisica entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pessoa Física não encontrada. ID: " + id));

        return toDTO(entity);
    }

    @Transactional(readOnly = true)
    public List<PessoaFisicaDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PessoaFisicaDTO> findByName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }

        return repository.findByName(name.toUpperCase())
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PessoaFisicaDTO findByCpf(String cpf) {
        if (cpf == null || cpf.isBlank()) {
            throw new IllegalArgumentException("CPF não pode ser vazio.");
        }

        PessoaFisica entity = repository.findByCpf(cpf)
                .orElseThrow(() -> new EntityNotFoundException("CPF não encontrado: " + cpf));

        return toDTO(entity);
    }

    @Transactional(readOnly = true)
    public Page<PessoaFisicaDTO> findPage(int page, int size) {
        if (page < 0) throw new IllegalArgumentException("page não pode ser negativo.");
        if (size <= 0) throw new IllegalArgumentException("size deve ser maior que zero.");

        return repository.findPage(PageRequest.of(page, size))
                .map(this::toDTO);
    }

    @Transactional
    public void delete(Long id) {
        if (id == null) throw new IllegalArgumentException("ID não pode ser nulo.");

        PessoaFisica entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pessoa Física não encontrada. ID: " + id));

        repository.delete(entity);
    }

    private void validateCpfUnique(String cpf, Long currentId) {
        if (cpf == null || cpf.isBlank()) {
            throw new IllegalArgumentException("CPF não pode ser vazio.");
        }

        repository.findByCpf(cpf)
                .filter(found -> currentId == null || !found.getId().equals(currentId))
                .ifPresent(found -> { throw new IllegalArgumentException("CPF já cadastrado: " + cpf); });
    }

    private void fillEntity(PessoaFisica entity, PessoaFisicaDTO dto) {
        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
        entity.setTelefone(dto.getTelefone());
        entity.setCpf(dto.getCpf());
        entity.setDataNascimento(dto.getDataNascimento());
        entity.setTipoPessoa("FISICA");

        if (dto.getEmpresaId() != null) {
            PessoaJuridica empresa = pessoaJuridicaRepository.findById(dto.getEmpresaId())
                    .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada. ID: " + dto.getEmpresaId()));
            entity.setEmpresa(empresa);
        } else {
            entity.setEmpresa(null);
        }
    }

    private PessoaFisicaDTO toDTO(PessoaFisica entity) {
        PessoaFisicaDTO dto = new PessoaFisicaDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setEmail(entity.getEmail());
        dto.setTelefone(entity.getTelefone());
        dto.setCpf(entity.getCpf());
        dto.setDataNascimento(entity.getDataNascimento());

        if (entity.getEmpresa() != null) {
            dto.setEmpresaId(entity.getEmpresa().getId());
        }
        return dto;
    }
}
