package Ecommerce_Completo.service;

import Ecommerce_Completo.enums.TipoEndereco;
import Ecommerce_Completo.model.DTO.EnderecoDTO;
import Ecommerce_Completo.model.DTO.PessoaJuridicaDTO;
import Ecommerce_Completo.model.Endereco;
import Ecommerce_Completo.model.PessoaJuridica;
import Ecommerce_Completo.repository.PessoaJuridicaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PessoaJuridicaServiceTests {

    @Mock
    private PessoaJuridicaRepository repository;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private PessoaJuridicaService service;

    private PessoaJuridica entity;
    private PessoaJuridicaDTO dto;

    @BeforeEach
    void setUp() {
        entity = new PessoaJuridica();
        entity.setId(1L);
        entity.setNome("Empresa");
        entity.setEmail("empresa@email.com");
        entity.setTelefone("9999");
        entity.setCnpj("12345678000100");
        entity.setRazaoSocial("Empresa LTDA");
        entity.setNomeFantasia("Empresa");
        entity.setInscEstadual("ISENTO");
        entity.setTipoPessoa("JURIDICA");

        dto = new PessoaJuridicaDTO();
        dto.setId(1L);
        dto.setNome("Empresa");
        dto.setEmail("empresa@email.com");
        dto.setTelefone("9999");
        dto.setCnpj("12.345.678/0001-00"); // máscara
        dto.setRazaoSocial("Empresa LTDA");
        dto.setNomeFantasia("Empresa");
        dto.setInscEstadual("ISENTO");
        dto.setCategoria("SERVICOS");
    }

    @Test
    void insert_shouldSaveAndReturnDTO_whenCnpjIsUnique_andCreateUsuario() {
        when(repository.existsByCnpj("12345678000100")).thenReturn(false);
        when(repository.save(any(PessoaJuridica.class))).thenAnswer(inv -> {
            PessoaJuridica saved = inv.getArgument(0);
            saved.setId(1L);
            return saved;
        });

        PessoaJuridicaDTO result = service.insert(dto);

        assertNotNull(result);
        assertEquals("12345678000100", result.getCnpj());

        verify(repository).existsByCnpj("12345678000100");
        verify(repository).save(any(PessoaJuridica.class));

        verify(usuarioService).criarUsuarioParaPessoa(
                any(PessoaJuridica.class),
                any(PessoaJuridica.class),
                eq("empresa@email.com")
        );
    }

    @Test
    void insert_shouldThrowIllegalArgumentException_whenCnpjAlreadyExists() {
        when(repository.existsByCnpj("12345678000100")).thenReturn(true);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.insert(dto));

        assertEquals("CNPJ já cadastrado: 12345678000100", ex.getMessage());
        verify(repository, never()).save(any());
        verifyNoInteractions(usuarioService);
    }

    @Test
    void update_shouldUpdateAndReturnDTO_whenOk() {
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.findByCnpj("12345678000100")).thenReturn(Optional.of(entity)); // mesmo id
        when(repository.save(any(PessoaJuridica.class))).thenAnswer(inv -> inv.getArgument(0));

        PessoaJuridicaDTO result = service.update(dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("12345678000100", result.getCnpj());

        verify(repository).save(any(PessoaJuridica.class));

        verifyNoInteractions(usuarioService);
    }

    @Test
    void findById_shouldReturnDTO_whenFound() {
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        PessoaJuridicaDTO result = service.findById(1L);

        assertNotNull(result);
        assertEquals(entity.getId(), result.getId());
    }

    @Test
    void findPage_shouldReturnPage() {
        PageRequest pr = PageRequest.of(0, 10);
        Page<PessoaJuridica> page = new PageImpl<>(List.of(entity), pr, 1);

        when(repository.findPage(any(PageRequest.class))).thenReturn(page);

        Page<PessoaJuridicaDTO> result = service.findPage(0, 10);

        assertEquals(1, result.getTotalElements());
        verify(repository).findPage(any(PageRequest.class));
    }

    @Test
    void delete_shouldDelete_whenFound() {
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        service.delete(1L);

        verify(repository).delete(entity);
        verifyNoInteractions(usuarioService);
    }

    @Test
    void delete_shouldThrowEntityNotFoundException_whenNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> service.delete(1L));

        assertEquals("Pessoa Jurídica não encontrada. ID: 1", ex.getMessage());
        verifyNoInteractions(usuarioService);
    }

    @Test
    void insert_shouldPersistAndReturnEnderecos() {
        EnderecoDTO endereco = new EnderecoDTO();
        endereco.setRuaLogra("Rua A");
        endereco.setCep("12345-000");
        endereco.setNumero("10");
        endereco.setBairro("Centro");
        endereco.setCidade("São Paulo");
        endereco.setUf("SP");
        endereco.setTipoEndereco(TipoEndereco.ENTREGA);

        dto.setEnderecos(List.of(endereco));

        when(repository.existsByCnpj("12345678000199")).thenReturn(false);
        when(repository.save(any())).thenAnswer(inv -> {
            PessoaJuridica pj = inv.getArgument(0);
            pj.setId(1L);
            pj.getEnderecos().forEach(e -> e.setId(1L));
            return pj;
        });

        dto.setCnpj("12.345.678/0001-99");

        PessoaJuridicaDTO result = service.insert(dto);

        assertEquals(1, result.getEnderecos().size());
        assertEquals("Rua A", result.getEnderecos().get(0).getRuaLogra());

        verify(usuarioService).criarUsuarioParaPessoa(any(), any(), eq(dto.getEmail()));
    }

    @Test
    void insert_shouldIgnoreEnderecoSync_whenEnderecosIsNull() {
        dto.setEnderecos(null);

        when(repository.existsByCnpj("12345678000199")).thenReturn(false);
        when(repository.save(any())).thenAnswer(inv -> {
            PessoaJuridica pj = inv.getArgument(0);
            pj.setId(1L);
            return pj;
        });

        dto.setCnpj("12.345.678/0001-99");

        PessoaJuridicaDTO result = service.insert(dto);

        assertTrue(result.getEnderecos().isEmpty());
    }

    @Test
    void insert_shouldThrowException_whenEnderecoCepIsBlank() {
        EnderecoDTO endereco = new EnderecoDTO();
        endereco.setRuaLogra("Rua A");
        endereco.setCep(" ");
        endereco.setNumero("10");
        endereco.setBairro("Centro");
        endereco.setCidade("SP");
        endereco.setUf("SP");
        endereco.setTipoEndereco(TipoEndereco.COBRANCA);

        dto.setEnderecos(List.of(endereco));
        dto.setCnpj("12.345.678/0001-99");

        when(repository.existsByCnpj("12345678000199")).thenReturn(false);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.insert(dto)
        );

        assertEquals("CEP é obrigatório.", ex.getMessage());
    }

    @Test
    void insert_shouldThrowException_whenTipoEnderecoIsNull() {
        EnderecoDTO endereco = new EnderecoDTO();
        endereco.setRuaLogra("Rua A");
        endereco.setCep("12345-000");
        endereco.setNumero("10");
        endereco.setBairro("Centro");
        endereco.setCidade("SP");
        endereco.setUf("SP");
        endereco.setTipoEndereco(null);

        dto.setEnderecos(List.of(endereco));
        dto.setCnpj("12.345.678/0001-99");

        when(repository.existsByCnpj("12345678000199")).thenReturn(false);

        NullPointerException ex = assertThrows(
                NullPointerException.class,
                () -> service.insert(dto)
        );

        assertEquals("Tipo de endereço é obrigatório.", ex.getMessage());
    }

    @Test
    void update_shouldReplaceExistingEnderecos() {
        PessoaJuridica entity = new PessoaJuridica();
        entity.setId(1L);

        Endereco antigo = new Endereco();
        antigo.setRuaLogra("Rua Antiga");
        antigo.setEmpresa(entity);
        entity.getEnderecos().add(antigo);

        EnderecoDTO novo = new EnderecoDTO();
        novo.setRuaLogra("Rua Nova");
        novo.setCep("99999-000");
        novo.setNumero("50");
        novo.setBairro("Novo");
        novo.setCidade("Curitiba");
        novo.setUf("PR");
        novo.setTipoEndereco(TipoEndereco.COBRANCA);

        dto.setId(1L);
        dto.setEnderecos(List.of(novo));
        dto.setCnpj("12.345.678/0001-99");

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.findByCnpj("12345678000199")).thenReturn(Optional.of(entity));
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        PessoaJuridicaDTO result = service.update(dto);

        assertEquals(1, result.getEnderecos().size());
        assertEquals("Rua Nova", result.getEnderecos().get(0).getRuaLogra());
    }

    @Test
    void insert_shouldNormalizeCnpj() {
        dto.setCnpj("12.345.678/0001-99");

        when(repository.existsByCnpj("12345678000199")).thenReturn(false);
        when(repository.save(any())).thenAnswer(inv -> {
            PessoaJuridica pj = inv.getArgument(0);
            pj.setId(1L);
            return pj;
        });

        PessoaJuridicaDTO result = service.insert(dto);

        assertEquals("12345678000199", result.getCnpj());
    }


}
