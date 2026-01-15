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
    private final UsuarioService usuarioService;

    public PessoaFisicaService(PessoaFisicaRepository repository,
                               PessoaJuridicaRepository pessoaJuridicaRepository,
                               UsuarioService usuarioService) {
        this.repository = Objects.requireNonNull(repository, "PessoaFisicaRepository não pode ser nulo.");
        this.pessoaJuridicaRepository = Objects.requireNonNull(pessoaJuridicaRepository, "PessoaJuridicaRepository não pode ser nulo.");
        this.usuarioService = Objects.requireNonNull(usuarioService, "UsuarioService não pode ser nulo.");
    }

    @Transactional
    public PessoaFisicaDTO insert(PessoaFisicaDTO dto) {
        Objects.requireNonNull(dto, "DTO não pode ser nulo.");

        final String cpf = requireNotBlank(dto.getCpf(), "CPF não pode ser vazio.");
        validateCpfUniqueForInsert(cpf);

        PessoaFisica entity = new PessoaFisica();
        fillEntity(entity, dto);

        entity = repository.save(entity);

        usuarioService.criarUsuarioParaPessoa(entity, entity.getEmpresa(), entity.getEmail());

        return toDTO(entity);
    }

    @Transactional
    public PessoaFisicaDTO update(PessoaFisicaDTO dto) {
        Objects.requireNonNull(dto, "DTO não pode ser nulo.");

        final Long id = dto.getId();
        if (id == null) {
            throw new IllegalArgumentException("ID é obrigatório para atualização.");
        }

        PessoaFisica entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pessoa Física não encontrada. ID: " + id));

        final String cpf = requireNotBlank(dto.getCpf(), "CPF não pode ser vazio.");
        validateCpfUniqueForUpdate(cpf, id);

        fillEntity(entity, dto);

        entity = repository.save(entity);
        return toDTO(entity);
    }

    @Transactional(readOnly = true)
    public PessoaFisicaDTO findById(Long id) {
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
        final String term = requireNotBlank(name, "Nome não pode ser vazio.").trim();

        return repository.findByName(term)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PessoaFisicaDTO findByCpf(String cpf) {
        final String value = requireNotBlank(cpf, "CPF não pode ser vazio.");

        PessoaFisica entity = repository.findByCpf(value)
                .orElseThrow(() -> new EntityNotFoundException("CPF não encontrado: " + value));

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

    private void validateCpfUniqueForInsert(String cpf) {
        if (repository.existsByCpf(cpf)) {
            throw new IllegalArgumentException("CPF já cadastrado: " + cpf);
        }
    }

    private void validateCpfUniqueForUpdate(String cpf, Long currentId) {
        repository.findByCpf(cpf)
                .filter(found -> !found.getId().equals(currentId))
                .ifPresent(found -> {
                    throw new IllegalArgumentException("CPF já cadastrado: " + cpf);
                });
    }

    private String requireNotBlank(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    private void fillEntity(PessoaFisica entity, PessoaFisicaDTO dto) {
        entity.setNome(requireNotBlank(dto.getNome(), "Nome é obrigatório."));
        entity.setEmail(requireNotBlank(dto.getEmail(), "Email é obrigatório."));
        entity.setTelefone(requireNotBlank(dto.getTelefone(), "Telefone é obrigatório."));
        entity.setTipoPessoa("FISICA");

        entity.setCpf(requireNotBlank(dto.getCpf(), "CPF é obrigatório."));
        entity.setDataNascimento(dto.getDataNascimento());

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
