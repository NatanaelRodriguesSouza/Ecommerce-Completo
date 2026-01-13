package Ecommerce_Completo.service;

import Ecommerce_Completo.model.Acesso;
import Ecommerce_Completo.model.DTO.AcessoDTO;
import Ecommerce_Completo.repository.AcessoRepository;
import Ecommerce_Completo.service.excepetions.ResourceNotFoundException;
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
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AcessoServiceTests {

    @InjectMocks
    private AcessoService service;

    @Mock
    private AcessoRepository repository;

    private Long existingId;
    private Long nonExistingId;
    private Acesso acesso;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 100L;

        acesso = new Acesso();
        acesso.setId(existingId);
        acesso.setDescricao("ROLE_ADMIN");
    }


    @Test
    void insertShouldReturnAcessoDTO() {
        when(repository.save(any(Acesso.class))).thenAnswer(inv -> {
            Acesso saved = inv.getArgument(0);
            saved.setId(existingId);
            return saved;
        });

        AcessoDTO dto = new AcessoDTO(null, "ROLE_ADMIN");
        AcessoDTO result = service.insert(dto);

        assertNotNull(result);
        assertEquals(existingId, result.getId());
        assertEquals("ROLE_ADMIN", result.getDescricao());
        verify(repository).save(any(Acesso.class));
    }

    @Test
    void insertShouldThrowNullPointerExceptionWhenDtoIsNull() {
        NullPointerException ex = assertThrows(NullPointerException.class, () -> service.insert(null));
        assertEquals("DTO não pode ser nulo.", ex.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void insertShouldThrowIllegalArgumentExceptionWhenDescricaoBlank() {
        AcessoDTO dto = new AcessoDTO(null, "  ");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> service.insert(dto));
        assertEquals("Descrição não pode ser vazia.", ex.getMessage());
        verify(repository, never()).save(any());
    }


    @Test
    void findAllShouldReturnListOfAcessoDTO() {
        when(repository.findAll()).thenReturn(List.of(acesso));

        List<AcessoDTO> result = service.findAll();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("ROLE_ADMIN", result.get(0).getDescricao());
        verify(repository).findAll();
    }


    @Test
    void findByIdShouldReturnAcessoDTOWhenIdExists() {
        when(repository.findById(existingId)).thenReturn(Optional.of(acesso));

        AcessoDTO result = service.findById(existingId);

        assertNotNull(result);
        assertEquals(existingId, result.getId());
        assertEquals("ROLE_ADMIN", result.getDescricao());
        verify(repository).findById(existingId);
    }

    @Test
    void findByIdShouldThrowIllegalArgumentExceptionWhenIdIsNull() {
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> service.findById(null));
        assertEquals("ID não pode ser nulo.", ex.getMessage());
        verify(repository, never()).findById(any());
    }

    @Test
    void findByIdShouldThrowEntityNotFoundExceptionWhenIdDoesNotExist() {
        when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> service.findById(nonExistingId));

        assertEquals("Acesso não encontrado. ID: " + nonExistingId, ex.getMessage());
        verify(repository).findById(nonExistingId);
    }


    @Test
    void findByDescricaoShouldReturnAcessoDTOWhenDescricaoExists() {
        when(repository.findByDescricao("ROLE_ADMIN")).thenReturn(Optional.of(acesso));

        AcessoDTO result = service.findByDescricao("ROLE_ADMIN");

        assertNotNull(result);
        assertEquals(existingId, result.getId());
        assertEquals("ROLE_ADMIN", result.getDescricao());
        verify(repository).findByDescricao("ROLE_ADMIN");
    }

    @Test
    void findByDescricaoShouldThrowIllegalArgumentExceptionWhenDescricaoBlank() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> service.findByDescricao(" "));
        assertEquals("Descrição não pode ser vazia.", ex.getMessage());
        verify(repository, never()).findByDescricao(anyString());
    }

    @Test
    void findByDescricaoShouldThrowEntityNotFoundExceptionWhenDescricaoDoesNotExist() {
        when(repository.findByDescricao("ROLE_INEXISTENTE")).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> service.findByDescricao("ROLE_INEXISTENTE"));

        assertEquals("Acesso não encontrado. Descrição: ROLE_INEXISTENTE", ex.getMessage());
        verify(repository).findByDescricao("ROLE_INEXISTENTE");
    }


    @Test
    void updateShouldReturnUpdatedAcessoDTOWhenIdExists() {
        when(repository.findById(existingId)).thenReturn(Optional.of(acesso));
        when(repository.save(any(Acesso.class))).thenAnswer(inv -> inv.getArgument(0));

        AcessoDTO dto = new AcessoDTO(existingId, "ROLE_USER");
        AcessoDTO result = service.update(existingId, dto);

        assertNotNull(result);
        assertEquals(existingId, result.getId());
        assertEquals("ROLE_USER", result.getDescricao());

        verify(repository).findById(existingId);
        verify(repository).save(any(Acesso.class));
    }

    @Test
    void updateShouldThrowIllegalArgumentExceptionWhenIdIsNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.update(null, new AcessoDTO(null, "ROLE_USER")));

        assertEquals("ID não pode ser nulo.", ex.getMessage());
        verify(repository, never()).findById(any());
    }

    @Test
    void updateShouldThrowNullPointerExceptionWhenDtoIsNull() {
        NullPointerException ex = assertThrows(NullPointerException.class, () -> service.update(existingId, null));
        assertEquals("DTO não pode ser nulo.", ex.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void updateShouldThrowIllegalArgumentExceptionWhenDescricaoBlank() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.update(existingId, new AcessoDTO(existingId, " ")));

        assertEquals("Descrição não pode ser vazia.", ex.getMessage());
        verify(repository, never()).findById(any());
        verify(repository, never()).save(any());
    }

    @Test
    void updateShouldThrowEntityNotFoundExceptionWhenIdDoesNotExist() {
        when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> service.update(nonExistingId, new AcessoDTO(null, "ROLE_USER")));

        assertEquals("Acesso não encontrado. ID: " + nonExistingId, ex.getMessage());
        verify(repository).findById(nonExistingId);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteShouldDoNothingWhenIdExists() {
        when(repository.existsById(existingId)).thenReturn(true);
        doNothing().when(repository).deleteById(existingId);

        assertDoesNotThrow(() -> service.delete(existingId));

        verify(repository).existsById(existingId);
        verify(repository).deleteById(existingId);
    }

    @Test
    void deleteShouldThrowIllegalArgumentExceptionWhenIdIsNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> service.delete(null));
        assertEquals("ID não pode ser nulo.", ex.getMessage());
        verify(repository, never()).deleteById(any());
    }

    @Test
    void deleteShouldThrowEntityNotFoundExceptionWhenIdDoesNotExist() {
        when(repository.existsById(nonExistingId)).thenReturn(false);

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> service.delete(nonExistingId));

        assertEquals("Acesso não encontrado. ID: " + nonExistingId, ex.getMessage());
        verify(repository).existsById(nonExistingId);
        verify(repository, never()).deleteById(any());
    }

    @Test
    void findPagedShouldReturnPageWhenDescricaoProvided() {
        Pageable pageable = PageRequest.of(0, 10);

        Page<Acesso> page = new PageImpl<>(List.of(acesso), pageable, 1);
        when(repository.findByDescricaoContainingIgnoreCase(eq("ROLE"), any(Pageable.class))).thenReturn(page);

        Page<AcessoDTO> result = service.findPaged("ROLE", pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals("ROLE_ADMIN", result.getContent().get(0).getDescricao());
        verify(repository).findByDescricaoContainingIgnoreCase(eq("ROLE"), any(Pageable.class));
    }

    @Test
    void findPagedShouldUseFindAllWhenDescricaoBlank() {
        Pageable pageable = PageRequest.of(0, 10);

        Page<Acesso> page = new PageImpl<>(List.of(acesso), pageable, 1);
        when(repository.findAll(any(Pageable.class))).thenReturn(page);

        Page<AcessoDTO> result = service.findPaged("  ", pageable);

        assertEquals(1, result.getTotalElements());
        verify(repository).findAll(any(Pageable.class));
        verify(repository, never()).findByDescricaoContainingIgnoreCase(anyString(), any(Pageable.class));
    }

    @Test
    void findPagedShouldUseFindAllWhenDescricaoNull() {
        Pageable pageable = PageRequest.of(0, 10);

        Page<Acesso> page = new PageImpl<>(List.of(acesso), pageable, 1);
        when(repository.findAll(any(Pageable.class))).thenReturn(page);

        Page<AcessoDTO> result = service.findPaged(null, pageable);

        assertEquals(1, result.getTotalElements());
        verify(repository).findAll(any(Pageable.class));
        verify(repository, never()).findByDescricaoContainingIgnoreCase(anyString(), any(Pageable.class));
    }
}
