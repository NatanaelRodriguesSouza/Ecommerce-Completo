package Ecommerce_Completo.service;

import Ecommerce_Completo.enums.TipoEndereco;
import Ecommerce_Completo.model.DTO.EnderecoDTO;
import Ecommerce_Completo.model.DTO.PessoaFisicaDTO;
import Ecommerce_Completo.model.Endereco;
import Ecommerce_Completo.model.PessoaFisica;
import Ecommerce_Completo.model.PessoaJuridica;
import Ecommerce_Completo.repository.PessoaFisicaRepository;
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

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PessoaFisicaServiceTests {

    @InjectMocks
    private PessoaFisicaService service;

    @Mock
    private PessoaFisicaRepository repository;

    @Mock
    private PessoaJuridicaRepository pessoaJuridicaRepository;

    @Mock
    private UsuarioService usuarioService;

    private PessoaFisica entity;
    private PessoaFisicaDTO dto;

    @BeforeEach
    void setUp() {
        entity = new PessoaFisica();
        entity.setId(1L);
        entity.setNome("João");
        entity.setEmail("joao@email.com");
        entity.setTelefone("9999");
        entity.setCpf("123");
        entity.setTipoPessoa("FISICA");

        dto = new PessoaFisicaDTO();
        dto.setId(1L);
        dto.setNome("João");
        dto.setEmail("joao@email.com");
        dto.setTelefone("9999");
        dto.setCpf("123");
        dto.setDataNascimento(new Date());
    }

    @Test
    void insert_shouldSaveAndReturnDTO_whenCpfIsUnique_andCreateUsuario() {
        when(repository.existsByCpf("123")).thenReturn(false);
        when(repository.save(any(PessoaFisica.class))).thenAnswer(inv -> {
            PessoaFisica saved = inv.getArgument(0);
            saved.setId(1L);
            return saved;
        });

        PessoaFisicaDTO result = service.insert(dto);

        assertNotNull(result);
        assertEquals("123", result.getCpf());

        verify(repository).existsByCpf("123");
        verify(repository).save(any(PessoaFisica.class));

        verify(usuarioService).criarUsuarioParaPessoa(
                any(PessoaFisica.class),
                isNull(),
                eq("joao@email.com")
        );
    }

    @Test
    void insert_shouldSetEmpresa_whenEmpresaIdProvided_andCreateUsuarioWithEmpresa() {
        dto.setEmpresaId(10L);

        PessoaJuridica empresa = new PessoaJuridica();
        empresa.setId(10L);

        when(repository.existsByCpf("123")).thenReturn(false);
        when(pessoaJuridicaRepository.findById(10L)).thenReturn(Optional.of(empresa));
        when(repository.save(any(PessoaFisica.class))).thenAnswer(inv -> {
            PessoaFisica saved = inv.getArgument(0);
            saved.setId(1L);
            return saved;
        });

        PessoaFisicaDTO result = service.insert(dto);

        assertEquals(10L, result.getEmpresaId());
        verify(pessoaJuridicaRepository).findById(10L);

        verify(usuarioService).criarUsuarioParaPessoa(
                any(PessoaFisica.class),
                eq(empresa),
                eq("joao@email.com")
        );
    }

    @Test
    void insert_shouldThrowIllegalArgumentException_whenCpfIsBlank() {
        dto.setCpf("  ");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.insert(dto));

        assertEquals("CPF não pode ser vazio.", ex.getMessage());
        verify(repository, never()).save(any());
        verifyNoInteractions(usuarioService);
    }

    @Test
    void insert_shouldThrowIllegalArgumentException_whenCpfAlreadyExists() {
        when(repository.existsByCpf("123")).thenReturn(true);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.insert(dto));

        assertEquals("CPF já cadastrado: 123", ex.getMessage());
        verify(repository).existsByCpf("123");
        verify(repository, never()).save(any());
        verifyNoInteractions(usuarioService);
    }

    @Test
    void insert_shouldThrowEntityNotFoundException_whenEmpresaIdNotFound() {
        dto.setEmpresaId(10L);

        when(repository.existsByCpf("123")).thenReturn(false);
        when(pessoaJuridicaRepository.findById(10L)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> service.insert(dto));

        assertEquals("Empresa não encontrada. ID: 10", ex.getMessage());
        verify(repository, never()).save(any());
        verifyNoInteractions(usuarioService);
    }

    @Test
    void update_shouldUpdateAndReturnDTO_whenOk() {
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.findByCpf("123")).thenReturn(Optional.of(entity)); // mesmo id
        when(repository.save(any(PessoaFisica.class))).thenAnswer(inv -> inv.getArgument(0));

        PessoaFisicaDTO result = service.update(dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(repository).save(any(PessoaFisica.class));

        verifyNoInteractions(usuarioService);
    }

    @Test
    void findById_shouldReturnDTO_whenFound() {
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        PessoaFisicaDTO result = service.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void findAll_shouldReturnList() {
        when(repository.findAll()).thenReturn(List.of(entity));

        List<PessoaFisicaDTO> result = service.findAll();

        assertEquals(1, result.size());
        assertEquals("João", result.get(0).getNome());
    }

    @Test
    void findPage_shouldReturnPage() {
        PageRequest pr = PageRequest.of(0, 10);
        Page<PessoaFisica> page = new PageImpl<>(List.of(entity), pr, 1);

        when(repository.findPage(any(PageRequest.class))).thenReturn(page);

        Page<PessoaFisicaDTO> result = service.findPage(0, 10);

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
    void insert_shouldThrowException_whenEnderecoProvidedWithoutEmpresa() {
        EnderecoDTO enderecoDTO = new EnderecoDTO();
        enderecoDTO.setRuaLogra("Rua A");
        enderecoDTO.setCep("12345-000");
        enderecoDTO.setNumero("10");
        enderecoDTO.setBairro("Centro");
        enderecoDTO.setCidade("São Paulo");
        enderecoDTO.setUf("SP");
        enderecoDTO.setTipoEndereco(TipoEndereco.ENTREGA);

        dto.setEnderecos(List.of(enderecoDTO)); // empresaId = null

        when(repository.existsByCpf("123")).thenReturn(false);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.insert(dto)
        );

        assertEquals(
                "Para cadastrar endereço, a Pessoa Física precisa estar vinculada a uma empresa (empresaId).",
                ex.getMessage()
        );

        verify(repository, never()).save(any());
        verifyNoInteractions(usuarioService);
    }

    @Test
    void insert_shouldIgnoreEnderecoSync_whenEnderecosIsNull() {
        dto.setEmpresaId(10L);
        dto.setEnderecos(null);

        PessoaJuridica empresa = new PessoaJuridica();
        empresa.setId(10L);

        when(repository.existsByCpf("123")).thenReturn(false);
        when(pessoaJuridicaRepository.findById(10L)).thenReturn(Optional.of(empresa));

        when(repository.save(any(PessoaFisica.class))).thenAnswer(inv -> {
            PessoaFisica pf = inv.getArgument(0);
            pf.setId(1L);
            return pf;
        });

        PessoaFisicaDTO result = service.insert(dto);

        assertNotNull(result);
        assertTrue(result.getEnderecos().isEmpty());

        verify(repository).save(any());
    }


    @Test
    void insert_shouldThrowException_whenEnderecoCepIsBlank() {
        dto.setEmpresaId(10L);

        EnderecoDTO enderecoDTO = new EnderecoDTO();
        enderecoDTO.setRuaLogra("Rua A");
        enderecoDTO.setCep(" ");
        enderecoDTO.setNumero("10");
        enderecoDTO.setBairro("Centro");
        enderecoDTO.setCidade("São Paulo");
        enderecoDTO.setUf("SP");
        enderecoDTO.setTipoEndereco(TipoEndereco.ENTREGA);

        dto.setEnderecos(List.of(enderecoDTO));

        PessoaJuridica empresa = new PessoaJuridica();
        empresa.setId(10L);

        when(repository.existsByCpf("123")).thenReturn(false);
        when(pessoaJuridicaRepository.findById(10L)).thenReturn(Optional.of(empresa));

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.insert(dto)
        );

        assertEquals("CEP é obrigatório.", ex.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void update_shouldReplaceExistingEnderecos() {
        PessoaJuridica empresa = new PessoaJuridica();
        empresa.setId(10L);
        entity.setEmpresa(empresa);

        Endereco antigo = new Endereco();
        antigo.setRuaLogra("Rua Antiga");
        antigo.setPessoa(entity);
        antigo.setEmpresa(empresa);
        entity.getEnderecos().add(antigo);

        EnderecoDTO novoEndereco = new EnderecoDTO();
        novoEndereco.setRuaLogra("Rua Nova");
        novoEndereco.setCep("99999-000");
        novoEndereco.setNumero("50");
        novoEndereco.setBairro("Novo Bairro");
        novoEndereco.setCidade("Curitiba");
        novoEndereco.setUf("PR");
        novoEndereco.setTipoEndereco(TipoEndereco.COBRANCA);

        dto.setEmpresaId(10L);
        dto.setEnderecos(List.of(novoEndereco));

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.findByCpf("123")).thenReturn(Optional.of(entity));
        when(pessoaJuridicaRepository.findById(10L)).thenReturn(Optional.of(empresa));
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        PessoaFisicaDTO result = service.update(dto);

        assertEquals(1, result.getEnderecos().size());
        assertEquals("Rua Nova", result.getEnderecos().get(0).getRuaLogra());
    }

    @Test
    void insert_shouldThrowException_whenTipoEnderecoIsNull() {
        dto.setEmpresaId(10L);

        EnderecoDTO enderecoDTO = new EnderecoDTO();
        enderecoDTO.setRuaLogra("Rua A");
        enderecoDTO.setCep("12345-000");
        enderecoDTO.setNumero("10");
        enderecoDTO.setBairro("Centro");
        enderecoDTO.setCidade("São Paulo");
        enderecoDTO.setUf("SP");
        enderecoDTO.setTipoEndereco(null);

        dto.setEnderecos(List.of(enderecoDTO));

        PessoaJuridica empresa = new PessoaJuridica();
        empresa.setId(10L);

        when(repository.existsByCpf("123")).thenReturn(false);
        when(pessoaJuridicaRepository.findById(10L)).thenReturn(Optional.of(empresa));

        NullPointerException ex = assertThrows(
                NullPointerException.class,
                () -> service.insert(dto)
        );

        assertEquals("Tipo de endereço é obrigatório.", ex.getMessage());
    }


}
