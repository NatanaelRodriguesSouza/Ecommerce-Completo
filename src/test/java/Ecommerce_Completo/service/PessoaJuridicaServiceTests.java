package Ecommerce_Completo.service;

import Ecommerce_Completo.model.DTO.PessoaJuridicaDTO;
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
}
