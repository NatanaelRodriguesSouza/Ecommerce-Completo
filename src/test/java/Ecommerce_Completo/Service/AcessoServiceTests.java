package Ecommerce_Completo.Service;

import Ecommerce_Completo.model.Acesso;
import Ecommerce_Completo.model.DTO.AcessoDTO;
import Ecommerce_Completo.repository.AcessoRepository;
import Ecommerce_Completo.service.AcessoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class AcessoServiceTests {
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
    public void insertShouldReturnAcessoDTO() {

        when(repository.save(any())).thenReturn(acesso);

        AcessoDTO dto = new AcessoDTO(null, "ROLE_ADMIN");
        AcessoDTO result = service.insert(dto);

        assertNotNull(result);
        assertEquals(existingId, result.getId());
        assertEquals("ROLE_ADMIN", result.getDescricao());

    }

    @Test
    public void findAllShouldReturnListOfAcessoDTO() {

        when(repository.findAll()).thenReturn(List.of(acesso));

        List<AcessoDTO> result = service.findAll();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("ROLE_ADMIN", result.get(0).getDescricao());

    }

    @Test
    public void findByIdShouldReturnAcessoDTOWhenIdExists() {

        when(repository.findById(existingId)).thenReturn(Optional.of(acesso));

        AcessoDTO result = service.findById(existingId);

        assertNotNull(result);
        assertEquals(existingId, result.getId());
        assertEquals("ROLE_ADMIN", result.getDescricao());

    }

    @Test
    public void findByIdShouldThrowExceptionWhenIdDoesNotExist() {

        when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.findById(nonExistingId)
        );

        assertEquals("Acesso não encontrado", exception.getMessage());
    }

    @Test
    public void updateShouldReturnUpdatedAcessoDTOWhenIdExists() {

        when(repository.findById(existingId)).thenReturn(Optional.of(acesso));
        when(repository.save(any())).thenReturn(acesso);

        AcessoDTO dto = new AcessoDTO(existingId, "ROLE_USER");
        AcessoDTO result = service.update(existingId, dto);

        assertEquals("ROLE_USER", result.getDescricao());
        verify(repository).findById(existingId);
        verify(repository).save(any());
    }

    @Test
    public void updateShouldThrowExceptionWhenIdDoesNotExist() {

        when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.update(nonExistingId, new AcessoDTO(null, "ROLE_USER"))
        );

        assertEquals("Acesso não encontrado", exception.getMessage());
    }

    @Test
    public void deleteShouldDoNothingWhenIdExists() {

        when(repository.existsById(existingId)).thenReturn(true);
        doNothing().when(repository).deleteById(existingId);

        assertDoesNotThrow(() -> service.delete(existingId));

        verify(repository).existsById(existingId);
        verify(repository).deleteById(existingId);
    }

    @Test
    public void deleteShouldThrowExceptionWhenIdDoesNotExist() {

        when(repository.existsById(nonExistingId)).thenReturn(false);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.delete(nonExistingId)
        );

        assertEquals("Acesso não encontrado", exception.getMessage());
    }

    @Test
    public void findByDescricaoShouldReturnAcessoDTOWhenDescricaoExists() {

        when(repository.findByDescricao("ROLE_ADMIN"))
                .thenReturn(Optional.of(acesso));

        AcessoDTO result = service.findByDescricao("ROLE_ADMIN");

        assertNotNull(result);
        assertEquals(existingId, result.getId());
        assertEquals("ROLE_ADMIN", result.getDescricao());

        verify(repository).findByDescricao("ROLE_ADMIN");
    }

    @Test
    public void findByDescricaoShouldThrowExceptionWhenDescricaoDoesNotExist() {

        when(repository.findByDescricao("ROLE_INEXISTENTE"))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.findByDescricao("ROLE_INEXISTENTE")
        );

        assertEquals("Acesso não encontrado", exception.getMessage());
    }


    @Test
    void findPagedShouldReturnPage() {

        Acesso acesso = new Acesso();
        acesso.setDescricao("ROLE_ADMIN");

        Page<Acesso> page = new PageImpl<>(
                List.of(acesso),
                PageRequest.of(0, 10),
                1
        );

        Mockito.when(repository.findByDescricaoContainingIgnoreCase(
                        Mockito.anyString(),
                        Mockito.any(Pageable.class)))
                .thenReturn(page);

        Page<AcessoDTO> result = service.findPaged("ROLE", PageRequest.of(0, 10));

        assertThat(result.getContent()).hasSize(1);
    }

}
