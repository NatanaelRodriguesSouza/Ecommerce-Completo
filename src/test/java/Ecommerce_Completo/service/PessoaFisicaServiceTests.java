package Ecommerce_Completo.service;

import Ecommerce_Completo.model.DTO.PessoaFisicaDTO;
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
    void insert_shouldSaveAndReturnDTO_whenCpfIsUnique() {
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
    }

    @Test
    void insert_shouldThrowNullPointerException_whenDtoIsNull() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> service.insert(null));
        assertEquals("DTO não pode ser nulo.", ex.getMessage());
        verifyNoInteractions(repository);
    }

    @Test
    void insert_shouldThrowIllegalArgumentException_whenCpfIsBlank() {
        dto.setCpf("  ");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.insert(dto));

        assertEquals("CPF não pode ser vazio.", ex.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void insert_shouldThrowIllegalArgumentException_whenCpfAlreadyExists() {
        when(repository.existsByCpf("123")).thenReturn(true);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.insert(dto));

        assertEquals("CPF já cadastrado: 123", ex.getMessage());
        verify(repository).existsByCpf("123");
        verify(repository, never()).save(any());
    }

    @Test
    void update_shouldUpdateAndReturnDTO_whenOk() {
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.findByCpf("123")).thenReturn(Optional.of(entity)); // mesmo id, ok
        when(repository.save(any(PessoaFisica.class))).thenAnswer(inv -> inv.getArgument(0));

        PessoaFisicaDTO result = service.update(dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(repository).save(any(PessoaFisica.class));
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
    void update_shouldThrowEntityNotFoundException_whenPessoaFisicaNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> service.update(dto));

        assertEquals("Pessoa Física não encontrada. ID: 1", ex.getMessage());
    }

    @Test
    void update_shouldThrowIllegalArgumentException_whenCpfBelongsToAnotherPerson() {
        PessoaFisica other = new PessoaFisica();
        other.setId(99L);
        other.setCpf("123");

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.findByCpf("123")).thenReturn(Optional.of(other));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.update(dto));

        assertEquals("CPF já cadastrado: 123", ex.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void findById_shouldReturnDTO_whenFound() {
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        PessoaFisicaDTO result = service.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void findById_shouldThrowIllegalArgumentException_whenIdIsNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.findById(null));

        assertEquals("ID não pode ser nulo.", ex.getMessage());
    }

    @Test
    void findById_shouldThrowEntityNotFoundException_whenNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> service.findById(1L));

        assertEquals("Pessoa Física não encontrada. ID: 1", ex.getMessage());
    }

    @Test
    void findAll_shouldReturnList() {
        when(repository.findAll()).thenReturn(List.of(entity));

        List<PessoaFisicaDTO> result = service.findAll();

        assertEquals(1, result.size());
        assertEquals("João", result.get(0).getNome());
    }

    @Test
    void findByName_shouldThrowIllegalArgumentException_whenBlank() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.findByName(" "));

        assertEquals("Nome não pode ser vazio.", ex.getMessage());
    }

    @Test
    void findByName_shouldTrimAndReturnList() {
        when(repository.findByName("joao")).thenReturn(List.of(entity));

        List<PessoaFisicaDTO> result = service.findByName("  joao  ");

        assertEquals(1, result.size());
        verify(repository).findByName("joao");
    }

    @Test
    void findByCpf_shouldThrowIllegalArgumentException_whenBlank() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.findByCpf(" "));

        assertEquals("CPF não pode ser vazio.", ex.getMessage());
    }

    @Test
    void findByCpf_shouldReturnDTO_whenFound() {
        when(repository.findByCpf("123")).thenReturn(Optional.of(entity));

        PessoaFisicaDTO result = service.findByCpf("123");

        assertNotNull(result);
        assertEquals("123", result.getCpf());
    }

    @Test
    void findByCpf_shouldThrowEntityNotFoundException_whenNotFound() {
        when(repository.findByCpf("123")).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> service.findByCpf("123"));

        assertEquals("CPF não encontrado: 123", ex.getMessage());
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

        assertEquals("Pessoa Física não encontrada. ID: 1", ex.getMessage());
    }

    @Test
    void insert_shouldSetEmpresa_whenEmpresaIdProvided() {
        dto.setEmpresaId(10L);

        PessoaJuridica empresa = new PessoaJuridica();
        empresa.setId(10L);

        when(repository.existsByCpf("123")).thenReturn(false);
        when(pessoaJuridicaRepository.findById(10L)).thenReturn(Optional.of(empresa));
        when(repository.save(any(PessoaFisica.class))).thenAnswer(inv -> inv.getArgument(0));

        PessoaFisicaDTO result = service.insert(dto);

        assertEquals(10L, result.getEmpresaId());
        verify(pessoaJuridicaRepository).findById(10L);
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
    }
}
