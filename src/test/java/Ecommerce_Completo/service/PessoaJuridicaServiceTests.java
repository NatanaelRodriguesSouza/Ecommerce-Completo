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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PessoaJuridicaServiceTests {

    @Mock
    private PessoaJuridicaRepository repository;

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
        dto.setCnpj("12.345.678/0001-00"); // com máscara pra validar normalização
        dto.setRazaoSocial("Empresa LTDA");
        dto.setNomeFantasia("Empresa");
        dto.setInscEstadual("ISENTO");
        dto.setCategoria("SERVICOS");
    }

    @Test
    void insert_shouldSaveAndReturnDTO_whenCnpjIsUnique() {
        when(repository.existsByCnpj("12345678000100")).thenReturn(false);
        when(repository.save(any(PessoaJuridica.class))).thenAnswer(inv -> {
            PessoaJuridica saved = inv.getArgument(0);
            saved.setId(1L);
            return saved;
        });

        PessoaJuridicaDTO result = service.insert(dto);

        assertNotNull(result);
        assertEquals("12345678000100", result.getCnpj()); // normalizado
        verify(repository).existsByCnpj("12345678000100");
        verify(repository).save(any(PessoaJuridica.class));
    }

    @Test
    void insert_shouldThrowNullPointerException_whenDtoIsNull() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> service.insert(null));

        assertEquals("DTO não pode ser nulo.", ex.getMessage());
        verifyNoInteractions(repository);
    }

    @Test
    void insert_shouldThrowIllegalArgumentException_whenCnpjBlank() {
        dto.setCnpj("  ");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.insert(dto));

        assertEquals("CNPJ não pode ser vazio.", ex.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void insert_shouldThrowIllegalArgumentException_whenCnpjAlreadyExists() {
        when(repository.existsByCnpj("12345678000100")).thenReturn(true);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.insert(dto));

        assertEquals("CNPJ já cadastrado: 12345678000100", ex.getMessage());
        verify(repository).existsByCnpj("12345678000100");
        verify(repository, never()).save(any());
    }

    @Test
    void update_shouldUpdateAndReturnDTO_whenOk() {
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.findByCnpj("12345678000100")).thenReturn(Optional.of(entity)); // mesmo id, ok
        when(repository.save(any(PessoaJuridica.class))).thenAnswer(inv -> inv.getArgument(0));

        PessoaJuridicaDTO result = service.update(dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("12345678000100", result.getCnpj()); // normalizado
        verify(repository).save(any(PessoaJuridica.class));
    }

    @Test
    void update_shouldThrowNullPointerException_whenDtoIsNull() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> service.update(null));

        assertEquals("DTO não pode ser nulo.", ex.getMessage());
    }

    @Test
    void update_shouldThrowIllegalArgumentException_whenIdIsNull() {
        dto.setId(null);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.update(dto));

        assertEquals("ID é obrigatório para atualização.", ex.getMessage());
    }

    @Test
    void update_shouldThrowEntityNotFoundException_whenEntityNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> service.update(dto));

        assertEquals("Pessoa Jurídica não encontrada. ID: 1", ex.getMessage());
    }

    @Test
    void update_shouldThrowIllegalArgumentException_whenCnpjBelongsToAnother() {
        PessoaJuridica other = new PessoaJuridica();
        other.setId(99L);
        other.setCnpj("12345678000100");

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.findByCnpj("12345678000100")).thenReturn(Optional.of(other));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.update(dto));

        assertEquals("CNPJ já cadastrado: 12345678000100", ex.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void findById_shouldReturnDTO_whenFound() {
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        PessoaJuridicaDTO result = service.findById(1L);

        assertNotNull(result);
        assertEquals(entity.getId(), result.getId());
    }

    @Test
    void findById_shouldThrowIllegalArgumentException_whenIdNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.findById(null));

        assertEquals("ID não pode ser nulo.", ex.getMessage());
    }

    @Test
    void findById_shouldThrowEntityNotFoundException_whenNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> service.findById(1L));

        assertEquals("Pessoa Jurídica não encontrada. ID: 1", ex.getMessage());
    }

    @Test
    void findAll_shouldReturnList() {
        when(repository.findAll()).thenReturn(List.of(entity));

        List<PessoaJuridicaDTO> list = service.findAll();

        assertEquals(1, list.size());
        assertEquals(entity.getCnpj(), list.get(0).getCnpj());
    }

    @Test
    void findByName_shouldThrowIllegalArgumentException_whenBlank() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.findByName(" "));

        assertEquals("Nome não pode ser vazio.", ex.getMessage());
    }

    @Test
    void findByName_shouldTrimAndReturnList() {
        when(repository.findByName("empresa")).thenReturn(List.of(entity));

        List<PessoaJuridicaDTO> result = service.findByName("  empresa  ");

        assertEquals(1, result.size());
        verify(repository).findByName("empresa");
    }

    @Test
    void findByCnpj_shouldThrowIllegalArgumentException_whenBlank() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.findByCnpj(" "));

        assertEquals("CNPJ não pode ser vazio.", ex.getMessage());
    }

    @Test
    void findByCnpj_shouldReturnDTO_whenFound_normalizingMask() {
        when(repository.findByCnpj("12345678000100")).thenReturn(Optional.of(entity));

        PessoaJuridicaDTO result = service.findByCnpj("12.345.678/0001-00");

        assertNotNull(result);
        assertEquals("12345678000100", result.getCnpj());
        verify(repository).findByCnpj("12345678000100");
    }

    @Test
    void findByCnpj_shouldThrowEntityNotFoundException_whenNotFound() {
        when(repository.findByCnpj(anyString())).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> service.findByCnpj("12.345.678/0001-00"));

        assertEquals("CNPJ não encontrado: 12345678000100", ex.getMessage());
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
    void findPage_shouldThrowIllegalArgumentException_whenPageNegative() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.findPage(-1, 10));

        assertEquals("page não pode ser negativo.", ex.getMessage());
    }

    @Test
    void findPage_shouldThrowIllegalArgumentException_whenSizeInvalid() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.findPage(0, 0));

        assertEquals("size deve ser maior que zero.", ex.getMessage());
    }

    @Test
    void delete_shouldDelete_whenFound() {
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        service.delete(1L);

        verify(repository).delete(entity);
    }

    @Test
    void delete_shouldThrowIllegalArgumentException_whenIdNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.delete(null));

        assertEquals("ID não pode ser nulo.", ex.getMessage());
    }

    @Test
    void delete_shouldThrowEntityNotFoundException_whenNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> service.delete(1L));

        assertEquals("Pessoa Jurídica não encontrada. ID: 1", ex.getMessage());
    }
}
