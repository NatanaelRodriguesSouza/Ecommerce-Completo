package Ecommerce_Completo.controllers;

import Ecommerce_Completo.model.DTO.AcessoDTO;
import Ecommerce_Completo.service.AcessoService;
import Ecommerce_Completo.service.excepetions.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = AcessoController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@AutoConfigureMockMvc
public class AcessoControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AcessoService service;

    @Autowired
    private ObjectMapper objectMapper;

    private long nonExistingId;
    private long existingId;
    private AcessoDTO acessoDTO;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 100L;
        acessoDTO = new AcessoDTO(existingId, "ROLE_ADMIN");
    }

    @Test
    void insertShouldReturnCreatedAndAcessoDTO() throws Exception {
        AcessoDTO dto = new AcessoDTO(null, "ROLE_ADMIN");

        Mockito.when(service.insert(Mockito.any())).thenReturn(acessoDTO);

        String jsonBody = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/acesso")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").value(existingId))
                .andExpect(jsonPath("$.descricao").value("ROLE_ADMIN"));
    }

    @Test
    void findAllShouldReturnListOfAcessoDTO() throws Exception {
        Mockito.when(service.findAll()).thenReturn(List.of(acessoDTO));

        mockMvc.perform(get("/acesso"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(existingId))
                .andExpect(jsonPath("$[0].descricao").value("ROLE_ADMIN"));
    }

    @Test
    void findByIdShouldReturnAcessoDTOWhenIdExists() throws Exception {
        Mockito.when(service.findById(existingId)).thenReturn(acessoDTO);

        mockMvc.perform(get("/acesso/{id}", existingId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(existingId))
                .andExpect(jsonPath("$.descricao").value("ROLE_ADMIN"));
    }

    @Test
    void findByIdShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        // conforme sua observação: você usa ResourceNotFoundException
        Mockito.when(service.findById(nonExistingId))
                .thenThrow(new ResourceNotFoundException("Acesso não encontrado. ID: " + nonExistingId));

        mockMvc.perform(get("/acesso/{id}", nonExistingId))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void updateShouldReturnUpdatedAcessoDTO() throws Exception {
        AcessoDTO updatedDTO = new AcessoDTO(existingId, "ROLE_USER");

        Mockito.when(service.update(Mockito.eq(existingId), Mockito.any())).thenReturn(updatedDTO);

        String jsonBody = objectMapper.writeValueAsString(updatedDTO);

        mockMvc.perform(put("/acesso/{id}", existingId)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(existingId))
                .andExpect(jsonPath("$.descricao").value("ROLE_USER"));
    }

    @Test
    void deleteShouldReturnNoContentWhenIdExists() throws Exception {
        Mockito.doNothing().when(service).delete(existingId);

        mockMvc.perform(delete("/acesso/{id}", existingId))
                .andExpect(status().isNoContent());
    }

    @Test
    void findByDescricaoShouldReturnAcessoDTO() throws Exception {
        Mockito.when(service.findByDescricao("ROLE_ADMIN")).thenReturn(acessoDTO);

        mockMvc.perform(get("/acesso/descricao")
                        .param("descricao", "ROLE_ADMIN"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descricao").value("ROLE_ADMIN"));
    }

    @Test
    void findPagedShouldReturnPage() throws Exception {
        Page<AcessoDTO> page = new PageImpl<>(
                List.of(acessoDTO),
                PageRequest.of(0, 10),
                1
        );

        Mockito.when(service.findPaged(Mockito.eq("ROLE"), Mockito.any(Pageable.class)))
                .thenReturn(page);

        mockMvc.perform(get("/acesso/page")
                        .param("descricao", "ROLE")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].descricao").value("ROLE_ADMIN"));
    }
}
