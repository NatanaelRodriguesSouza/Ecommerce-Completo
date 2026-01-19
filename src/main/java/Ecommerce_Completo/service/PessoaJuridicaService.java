package Ecommerce_Completo.service;

import Ecommerce_Completo.model.DTO.CepDTO;
import Ecommerce_Completo.model.DTO.EnderecoDTO;
import Ecommerce_Completo.model.DTO.PessoaJuridicaDTO;
import Ecommerce_Completo.model.Endereco;
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
    private final UsuarioService usuarioService;
    private final CepService cepService;

    public PessoaJuridicaService(PessoaJuridicaRepository repository,
                                 UsuarioService usuarioService,
                                 CepService cepService) {
        this.repository = Objects.requireNonNull(repository, "PessoaJuridicaRepository não pode ser nulo.");
        this.usuarioService = Objects.requireNonNull(usuarioService, "UsuarioService não pode ser nulo.");
        this.cepService = Objects.requireNonNull(cepService, "CepService não pode ser nulo.");
    }

    @Transactional
    public PessoaJuridicaDTO insert(PessoaJuridicaDTO dto) {
        Objects.requireNonNull(dto, "DTO não pode ser nulo.");

        final String cnpj = normalizeCnpj(requireNotBlank(dto.getCnpj(), "CNPJ não pode ser vazio."));
        validateCnpjUniqueForInsert(cnpj);

        PessoaJuridica entity = new PessoaJuridica();
        fillEntity(entity, dto, cnpj);

        entity = repository.save(entity);

        usuarioService.criarUsuarioParaPessoa(entity, entity, entity.getEmail());

        return toDTO(entity);
    }

    @Transactional
    public PessoaJuridicaDTO update(PessoaJuridicaDTO dto) {
        Objects.requireNonNull(dto, "DTO não pode ser nulo.");

        final Long id = dto.getId();
        if (id == null) {
            throw new IllegalArgumentException("ID é obrigatório para atualização.");
        }

        PessoaJuridica entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pessoa Jurídica não encontrada. ID: " + id));

        final String cnpj = normalizeCnpj(requireNotBlank(dto.getCnpj(), "CNPJ não pode ser vazio."));
        validateCnpjUniqueForUpdate(cnpj, id);

        fillEntity(entity, dto, cnpj);

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
        final String term = requireNotBlank(name, "Nome não pode ser vazio.").trim();

        return repository.findByName(term)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PessoaJuridicaDTO findByCnpj(String cnpj) {
        final String value = normalizeCnpj(requireNotBlank(cnpj, "CNPJ não pode ser vazio."));

        PessoaJuridica entity = repository.findByCnpj(value)
                .orElseThrow(() -> new EntityNotFoundException("CNPJ não encontrado: " + value));

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

    private void validateCnpjUniqueForInsert(String cnpj) {
        if (repository.existsByCnpj(cnpj)) {
            throw new IllegalArgumentException("CNPJ já cadastrado: " + cnpj);
        }
    }

    private void validateCnpjUniqueForUpdate(String cnpj, Long currentId) {
        repository.findByCnpj(cnpj)
                .filter(found -> !found.getId().equals(currentId))
                .ifPresent(found -> {
                    throw new IllegalArgumentException("CNPJ já cadastrado: " + cnpj);
                });
    }

    private String requireNotBlank(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    private String normalizeCnpj(String cnpj) {
        return cnpj.replaceAll("\\D", "");
    }

    private void fillEntity(PessoaJuridica entity, PessoaJuridicaDTO dto, String normalizedCnpj) {
        entity.setNome(requireNotBlank(dto.getNome(), "Nome é obrigatório."));
        entity.setEmail(requireNotBlank(dto.getEmail(), "Email é obrigatório."));
        entity.setTelefone(requireNotBlank(dto.getTelefone(), "Telefone é obrigatório."));
        entity.setTipoPessoa("JURIDICA");

        entity.setCnpj(normalizedCnpj);

        entity.setInscEstadual(dto.getInscEstadual());
        entity.setInscMunicipal(dto.getInscMunicipal());

        entity.setNomeFantasia(requireNotBlank(dto.getNomeFantasia(), "Nome Fantasia é obrigatório."));
        entity.setRazaoSocial(requireNotBlank(dto.getRazaoSocial(), "Razão Social é obrigatória."));
        entity.setCategoria(requireNotBlank(dto.getCategoria(), "Categoria é obrigatória."));

        syncEnderecos(entity, dto.getEnderecos());
    }


    private void syncEnderecos(PessoaJuridica pessoa, List<EnderecoDTO> enderecosDto) {
        if (enderecosDto == null) return;

        pessoa.getEnderecos().clear();

        for (EnderecoDTO eDto : enderecosDto) {

            CepDTO cepInfo = cepService.buscarCep(eDto.getCep());

            Endereco e = new Endereco();

            e.setCep(cepInfo.getCep());
            e.setRuaLogra(requireNotBlank(cepInfo.getLogradouro(), "Logradouro não retornado pelo ViaCEP."));
            e.setBairro(requireNotBlank(cepInfo.getBairro(), "Bairro não retornado pelo ViaCEP."));
            e.setCidade(requireNotBlank(cepInfo.getLocalidade(), "Cidade não retornada pelo ViaCEP."));
            e.setUf(requireNotBlank(cepInfo.getUf(), "UF não retornada pelo ViaCEP."));

            e.setNumero(requireNotBlank(eDto.getNumero(), "Número é obrigatório."));
            e.setComplemento(eDto.getComplemento());
            e.setTipoEndereco(Objects.requireNonNull(eDto.getTipoEndereco(), "Tipo de endereço é obrigatório."));

            e.setPessoa(pessoa);
            e.setEmpresa(pessoa);
            pessoa.getEnderecos().add(e);
        }
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

        if (entity.getEnderecos() != null) {
            dto.setEnderecos(
                    entity.getEnderecos().stream().map(e -> {
                        EnderecoDTO eDto = new EnderecoDTO();
                        eDto.setId(e.getId());
                        eDto.setRuaLogra(e.getRuaLogra());
                        eDto.setCep(e.getCep());
                        eDto.setNumero(e.getNumero());
                        eDto.setComplemento(e.getComplemento());
                        eDto.setBairro(e.getBairro());
                        eDto.setUf(e.getUf());
                        eDto.setCidade(e.getCidade());
                        eDto.setTipoEndereco(e.getTipoEndereco());
                        return eDto;
                    }).collect(Collectors.toList())
            );
        }

        return dto;
    }
}
