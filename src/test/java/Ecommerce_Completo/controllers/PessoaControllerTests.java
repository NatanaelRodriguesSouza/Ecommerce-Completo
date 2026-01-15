package Ecommerce_Completo.controllers;

import Ecommerce_Completo.controllers.exceptions.ResourceExceptionsHandler;
import Ecommerce_Completo.model.DTO.EnderecoDTO;
import Ecommerce_Completo.model.DTO.PessoaFisicaDTO;
import Ecommerce_Completo.model.DTO.PessoaJuridicaDTO;
import Ecommerce_Completo.service.PessoaFisicaService;
import Ecommerce_Completo.service.PessoaJuridicaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = PessoaController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@AutoConfigureMockMvc
@Import(ResourceExceptionsHandler.class)
public class PessoaControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PessoaFisicaService pessoaFisicaService;

    @MockBean
    private PessoaJuridicaService pessoaJuridicaService;

    @Autowired
    private ObjectMapper objectMapper;

    private long existingId;
    private long nonExistingId;

    private PessoaFisicaDTO pessoaFisicaDTO;
    private PessoaJuridicaDTO pessoaJuridicaDTO;

    private EnderecoDTO endereco1;
    private EnderecoDTO endereco2;

    private Page<PessoaFisicaDTO> pfPage;
    private Page<PessoaJuridicaDTO> pjPage;
    private final String TIPO_ENDERECO_VALIDO = "ENTREGA";
    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 100L;

        endereco1 = new EnderecoDTO();
        endereco1.setId(10L);
        endereco1.setRuaLogra("Rua A");
        endereco1.setCep("57000-000");
        endereco1.setNumero("10");
        endereco1.setComplemento("AP 101");
        endereco1.setBairro("Centro");
        endereco1.setUf("AL");
        endereco1.setCidade("Maceió");

        endereco2 = new EnderecoDTO();
        endereco2.setId(20L);
        endereco2.setRuaLogra("Rua B");
        endereco2.setCep("57000-111");
        endereco2.setNumero("20");
        endereco2.setComplemento(null);
        endereco2.setBairro("Farol");
        endereco2.setUf("AL");
        endereco2.setCidade("Maceió");

        pessoaFisicaDTO = new PessoaFisicaDTO();
        pessoaFisicaDTO.setId(existingId);
        pessoaFisicaDTO.setNome("João da Silva");
        pessoaFisicaDTO.setEmail("joao@email.com");
        pessoaFisicaDTO.setTelefone("82999999999");
        pessoaFisicaDTO.setCpf("529.982.247-25");
        pessoaFisicaDTO.setDataNascimento(new Date(0));
        pessoaFisicaDTO.setEmpresaId(10L);
        pessoaFisicaDTO.setEnderecos(new ArrayList<>(List.of(endereco1)));

        pessoaJuridicaDTO = new PessoaJuridicaDTO();
        pessoaJuridicaDTO.setId(existingId);
        pessoaJuridicaDTO.setNome("Empresa X");
        pessoaJuridicaDTO.setEmail("contato@empresa.com");
        pessoaJuridicaDTO.setTelefone("1133334444");
        pessoaJuridicaDTO.setCnpj("04.252.011/0001-10");
        pessoaJuridicaDTO.setInscEstadual("ISENTO");
        pessoaJuridicaDTO.setInscMunicipal("123");
        pessoaJuridicaDTO.setNomeFantasia("Empresa X LTDA");
        pessoaJuridicaDTO.setRazaoSocial("Empresa X Comércio LTDA");
        pessoaJuridicaDTO.setCategoria("VAREJO");
        pessoaJuridicaDTO.setEnderecos(new ArrayList<>(List.of(endereco2)));

        pfPage = new PageImpl<>(List.of(pessoaFisicaDTO), PageRequest.of(0, 10), 1);
        pjPage = new PageImpl<>(List.of(pessoaJuridicaDTO), PageRequest.of(0, 10), 1);
    }


    private String enderecoJson(Long id) {
        return """
        {
          "id": %s,
          "ruaLogra": "Rua A",
          "cep": "57000-000",
          "numero": "10",
          "complemento": "AP 101",
          "bairro": "Centro",
          "uf": "AL",
          "cidade": "Maceió",
          "tipoEndereco": "%s"
        }
        """.formatted(id == null ? "null" : id.toString(), TIPO_ENDERECO_VALIDO);
    }

    private String pessoaFisicaJsonValida(Long id) {
        return """
        {
          "id": %s,
          "nome": "João da Silva",
          "email": "joao@email.com",
          "telefone": "82999999999",
          "cpf": "529.982.247-25",
          "dataNascimento": "1970-01-01T00:00:00.000+00:00",
          "empresaId": 10,
          "enderecos": [%s]
        }
        """.formatted(id == null ? "null" : id.toString(), enderecoJson(10L));
    }

    private String pessoaFisicaJsonInvalidaCampos() {
        // Falta nome/telefone/cpf/dataNascimento e enderecos vazio => 422
        return """
        {
          "email": "email-invalido",
          "enderecos": []
        }
        """;
    }

    private String pessoaFisicaJsonCpfInvalido() {
        return """
        {
          "nome": "João",
          "email": "joao@email.com",
          "telefone": "82999999999",
          "cpf": "111.111.111-11",
          "dataNascimento": "1970-01-01T00:00:00.000+00:00",
          "empresaId": 10,
          "enderecos": [%s]
        }
        """.formatted(enderecoJson(10L));
    }

    private String pessoaJuridicaJsonValida(Long id) {
        return """
        {
          "id": %s,
          "nome": "Empresa X",
          "email": "contato@empresa.com",
          "telefone": "1133334444",
          "cnpj": "04.252.011/0001-10",
          "inscEstadual": "ISENTO",
          "inscMunicipal": "123",
          "nomeFantasia": "Empresa X LTDA",
          "razaoSocial": "Empresa X Comércio LTDA",
          "categoria": "VAREJO",
          "enderecos": [%s]
        }
        """.formatted(id == null ? "null" : id.toString(), enderecoJson(20L));
    }

    private String pessoaJuridicaJsonInvalidaCampos() {
        // falta campos obrigatórios => 422
        return """
        {
          "email": "contato@empresa.com",
          "enderecos": []
        }
        """;
    }

    private String pessoaJuridicaJsonCnpjInvalido() {
        return """
        {
          "nome": "Empresa X",
          "email": "contato@empresa.com",
          "telefone": "1133334444",
          "cnpj": "11.111.111/1111-11",
          "inscEstadual": "ISENTO",
          "inscMunicipal": "123",
          "nomeFantasia": "Empresa X LTDA",
          "razaoSocial": "Empresa X Comércio LTDA",
          "categoria": "VAREJO",
          "enderecos": [%s]
        }
        """.formatted(enderecoJson(20L));
    }


    @Test
    void createFisicaShouldReturnCreatedAndPessoaFisicaDTO() throws Exception {
        Mockito.when(pessoaFisicaService.insert(Mockito.any())).thenReturn(pessoaFisicaDTO);

        mockMvc.perform(post("/pessoas/fisicas")
                        .content(pessoaFisicaJsonValida(null))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").value(existingId))
                .andExpect(jsonPath("$.nome").value("João da Silva"));
    }

    @Test
    void createFisicaShouldReturn422WhenInvalidBody() throws Exception {
        mockMvc.perform(post("/pessoas/fisicas")
                        .content(pessoaFisicaJsonInvalidaCampos())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(422))
                .andExpect(jsonPath("$.error").value("Erro de validação"))
                .andExpect(jsonPath("$.path").value("/pessoas/fisicas"));
    }

    @Test
    void createFisicaShouldReturn422WhenCpfInvalid() throws Exception {
        mockMvc.perform(post("/pessoas/fisicas")
                        .content(pessoaFisicaJsonCpfInvalido())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.status").value(422))
                .andExpect(jsonPath("$.error").value("Erro de validação"));
    }

    @Test
    void createFisicaShouldReturn400WhenServiceThrowsIllegalArgument() throws Exception {
        Mockito.when(pessoaFisicaService.insert(Mockito.any()))
                .thenThrow(new IllegalArgumentException("CPF já cadastrado"));

        mockMvc.perform(post("/pessoas/fisicas")
                        .content(pessoaFisicaJsonValida(null))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Requisição inválida"));
    }


    @Test
    void updateFisicaShouldReturnUpdatedPessoaFisicaDTO() throws Exception {
        Mockito.when(pessoaFisicaService.update(Mockito.any())).thenReturn(pessoaFisicaDTO);

        mockMvc.perform(put("/pessoas/fisicas/{id}", existingId)
                        .content(pessoaFisicaJsonValida(null))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(existingId));

        // garante que controller setou o id vindo do path
        ArgumentCaptor<PessoaFisicaDTO> captor = ArgumentCaptor.forClass(PessoaFisicaDTO.class);
        Mockito.verify(pessoaFisicaService).update(captor.capture());
        assertThat(captor.getValue().getId()).isEqualTo(existingId);
    }

    @Test
    void updateFisicaShouldReturn422WhenInvalidBody() throws Exception {
        mockMvc.perform(put("/pessoas/fisicas/{id}", existingId)
                        .content(pessoaFisicaJsonInvalidaCampos())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.status").value(422));
    }

    @Test
    void updateFisicaShouldReturn404WhenEntityNotFound() throws Exception {
        Mockito.when(pessoaFisicaService.update(Mockito.any()))
                .thenThrow(new EntityNotFoundException("Pessoa Física não encontrada. ID: " + existingId));

        mockMvc.perform(put("/pessoas/fisicas/{id}", existingId)
                        .content(pessoaFisicaJsonValida(null))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void updateFisicaShouldReturn400WhenServiceThrowsIllegalArgument() throws Exception {
        Mockito.when(pessoaFisicaService.update(Mockito.any()))
                .thenThrow(new IllegalArgumentException("CPF já cadastrado"));

        mockMvc.perform(put("/pessoas/fisicas/{id}", existingId)
                        .content(pessoaFisicaJsonValida(null))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }


    @Test
    void findFisicaByIdShouldReturnPessoaFisicaDTOWhenIdExists() throws Exception {
        Mockito.when(pessoaFisicaService.findById(existingId)).thenReturn(pessoaFisicaDTO);

        mockMvc.perform(get("/pessoas/fisicas/{id}", existingId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(existingId))
                .andExpect(jsonPath("$.cpf").value("529.982.247-25"));
    }

    @Test
    void findFisicaByIdShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        Mockito.when(pessoaFisicaService.findById(nonExistingId))
                .thenThrow(new EntityNotFoundException("Pessoa Física não encontrada. ID: " + nonExistingId));

        mockMvc.perform(get("/pessoas/fisicas/{id}", nonExistingId))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void findAllFisicasShouldReturnList() throws Exception {
        Mockito.when(pessoaFisicaService.findAll()).thenReturn(List.of(pessoaFisicaDTO));

        mockMvc.perform(get("/pessoas/fisicas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(existingId))
                .andExpect(jsonPath("$[0].nome").value("João da Silva"));
    }

    @Test
    void findFisicasByNameShouldReturnList() throws Exception {
        Mockito.when(pessoaFisicaService.findByName("João")).thenReturn(List.of(pessoaFisicaDTO));

        mockMvc.perform(get("/pessoas/fisicas/busca")
                        .param("nome", "João"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("João da Silva"));
    }

    @Test
    void findFisicaByCpfShouldReturnPessoaFisicaDTO() throws Exception {
        Mockito.when(pessoaFisicaService.findByCpf("529.982.247-25")).thenReturn(pessoaFisicaDTO);

        mockMvc.perform(get("/pessoas/fisicas/cpf/{cpf}", "529.982.247-25"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(existingId));
    }

    @Test
    void findFisicaByCpfShouldReturnNotFoundWhenCpfDoesNotExist() throws Exception {
        Mockito.when(pessoaFisicaService.findByCpf("000.000.000-00"))
                .thenThrow(new EntityNotFoundException("CPF não encontrado"));

        mockMvc.perform(get("/pessoas/fisicas/cpf/{cpf}", "000.000.000-00"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void findFisicasPageShouldReturnPage() throws Exception {
        Mockito.when(pessoaFisicaService.findPage(Mockito.eq(0), Mockito.eq(10))).thenReturn(pfPage);

        mockMvc.perform(get("/pessoas/fisicas/paged")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(existingId));
    }

    @Test
    void findFisicasPageShouldReturn400WhenServiceThrowsIllegalArgument() throws Exception {
        Mockito.when(pessoaFisicaService.findPage(Mockito.anyInt(), Mockito.anyInt()))
                .thenThrow(new IllegalArgumentException("size deve ser maior que zero"));

        mockMvc.perform(get("/pessoas/fisicas/paged")
                        .param("page", "0")
                        .param("size", "0"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }


    @Test
    void deleteFisicaShouldReturnNoContentWhenIdExists() throws Exception {
        Mockito.doNothing().when(pessoaFisicaService).delete(existingId);

        mockMvc.perform(delete("/pessoas/fisicas/{id}", existingId))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteFisicaShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        Mockito.doThrow(new EntityNotFoundException("Pessoa Física não encontrada. ID: " + nonExistingId))
                .when(pessoaFisicaService).delete(nonExistingId);

        mockMvc.perform(delete("/pessoas/fisicas/{id}", nonExistingId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }


    @Test
    void replaceEnderecosFisicaShouldReturnUpdatedPessoaFisicaDTO() throws Exception {
        Mockito.when(pessoaFisicaService.findById(existingId)).thenReturn(pessoaFisicaDTO);
        Mockito.when(pessoaFisicaService.update(Mockito.any())).thenReturn(pessoaFisicaDTO);

        String body = "[" + enderecoJson(123L) + "]";

        mockMvc.perform(put("/pessoas/fisicas/{id}/enderecos", existingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(existingId));

        Mockito.verify(pessoaFisicaService).findById(existingId);
        Mockito.verify(pessoaFisicaService).update(Mockito.any());
    }

    @Test
    void replaceEnderecosFisicaShouldReturn500WhenEnderecoInvalid() throws Exception {
        String body = """
    [{
      "ruaLogra": "",
      "cep": "",
      "numero": "",
      "bairro": "",
      "uf": "",
      "cidade": "",
      "tipoEndereco": null
    }]
    """;

        mockMvc.perform(put("/pessoas/fisicas/{id}/enderecos", existingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500));
    }

    @Test
    void replaceEnderecosFisicaShouldReturn404WhenPessoaNotFound() throws Exception {
        Mockito.when(pessoaFisicaService.findById(existingId))
                .thenThrow(new EntityNotFoundException("Pessoa Física não encontrada. ID: " + existingId));

        String body = "[" + enderecoJson(123L) + "]";

        mockMvc.perform(put("/pessoas/fisicas/{id}/enderecos", existingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void addEnderecoFisicaShouldReturnCreatedAndLocation() throws Exception {
        PessoaFisicaDTO current = buildPessoaFisica(existingId);
        current.setEnderecos(new ArrayList<>(List.of(buildEndereco(10L))));

        PessoaFisicaDTO updated = buildPessoaFisica(existingId);
        updated.setEnderecos(new ArrayList<>(List.of(buildEndereco(10L), buildEndereco(20L))));

        Mockito.when(pessoaFisicaService.findById(existingId)).thenReturn(current);
        Mockito.when(pessoaFisicaService.update(Mockito.any())).thenReturn(updated);

        mockMvc.perform(post("/pessoas/fisicas/{id}/enderecos", existingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(enderecoJson(null)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").value(existingId));
    }

    @Test
    void addEnderecoFisicaShouldReturn422WhenEnderecoInvalid() throws Exception {
        String invalidEndereco = """
        {
          "ruaLogra": "",
          "cep": "",
          "numero": "",
          "bairro": "",
          "uf": "",
          "cidade": "",
          "tipoEndereco": null
        }
        """;

        mockMvc.perform(post("/pessoas/fisicas/{id}/enderecos", existingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidEndereco))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.status").value(422));
    }

    @Test
    void deleteEnderecoFisicaShouldReturnNoContentAndRemoveEndereco() throws Exception {
        PessoaFisicaDTO current = buildPessoaFisica(existingId);
        EnderecoDTO e1 = buildEndereco(10L);
        EnderecoDTO e2 = buildEndereco(20L);
        current.setEnderecos(new ArrayList<>(List.of(e1, e2)));

        Mockito.when(pessoaFisicaService.findById(existingId)).thenReturn(current);
        Mockito.when(pessoaFisicaService.update(Mockito.any())).thenReturn(current);

        mockMvc.perform(delete("/pessoas/fisicas/{id}/enderecos/{enderecoId}", existingId, 20L))
                .andExpect(status().isNoContent());

        ArgumentCaptor<PessoaFisicaDTO> captor = ArgumentCaptor.forClass(PessoaFisicaDTO.class);
        Mockito.verify(pessoaFisicaService).update(captor.capture());

        assertThat(captor.getValue().getEnderecos())
                .extracting(EnderecoDTO::getId)
                .containsExactly(10L);
    }


    @Test
    void createJuridicaShouldReturnCreatedAndPessoaJuridicaDTO() throws Exception {
        Mockito.when(pessoaJuridicaService.insert(Mockito.any())).thenReturn(pessoaJuridicaDTO);

        mockMvc.perform(post("/pessoas/juridicas")
                        .content(pessoaJuridicaJsonValida(null))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").value(existingId))
                .andExpect(jsonPath("$.cnpj").value("04.252.011/0001-10"));
    }

    @Test
    void createJuridicaShouldReturn422WhenInvalidBody() throws Exception {
        mockMvc.perform(post("/pessoas/juridicas")
                        .content(pessoaJuridicaJsonInvalidaCampos())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.status").value(422));
    }

    @Test
    void createJuridicaShouldReturn422WhenCnpjInvalid() throws Exception {
        mockMvc.perform(post("/pessoas/juridicas")
                        .content(pessoaJuridicaJsonCnpjInvalido())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.status").value(422));
    }

    @Test
    void createJuridicaShouldReturn400WhenServiceThrowsIllegalArgument() throws Exception {
        Mockito.when(pessoaJuridicaService.insert(Mockito.any()))
                .thenThrow(new IllegalArgumentException("CNPJ já cadastrado"));

        mockMvc.perform(post("/pessoas/juridicas")
                        .content(pessoaJuridicaJsonValida(null))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }


    @Test
    void updateJuridicaShouldReturnUpdatedPessoaJuridicaDTO() throws Exception {
        Mockito.when(pessoaJuridicaService.update(Mockito.any())).thenReturn(pessoaJuridicaDTO);

        mockMvc.perform(put("/pessoas/juridicas/{id}", existingId)
                        .content(pessoaJuridicaJsonValida(null))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(existingId));

        ArgumentCaptor<PessoaJuridicaDTO> captor = ArgumentCaptor.forClass(PessoaJuridicaDTO.class);
        Mockito.verify(pessoaJuridicaService).update(captor.capture());
        assertThat(captor.getValue().getId()).isEqualTo(existingId);
    }

    @Test
    void updateJuridicaShouldReturn422WhenInvalidBody() throws Exception {
        mockMvc.perform(put("/pessoas/juridicas/{id}", existingId)
                        .content(pessoaJuridicaJsonInvalidaCampos())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.status").value(422));
    }

    @Test
    void updateJuridicaShouldReturn404WhenEntityNotFound() throws Exception {
        Mockito.when(pessoaJuridicaService.update(Mockito.any()))
                .thenThrow(new EntityNotFoundException("Pessoa Jurídica não encontrada. ID: " + existingId));

        mockMvc.perform(put("/pessoas/juridicas/{id}", existingId)
                        .content(pessoaJuridicaJsonValida(null))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void updateJuridicaShouldReturn400WhenServiceThrowsIllegalArgument() throws Exception {
        Mockito.when(pessoaJuridicaService.update(Mockito.any()))
                .thenThrow(new IllegalArgumentException("CNPJ já cadastrado"));

        mockMvc.perform(put("/pessoas/juridicas/{id}", existingId)
                        .content(pessoaJuridicaJsonValida(null))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }


    @Test
    void findJuridicaByIdShouldReturnPessoaJuridicaDTOWhenIdExists() throws Exception {
        Mockito.when(pessoaJuridicaService.findById(existingId)).thenReturn(pessoaJuridicaDTO);

        mockMvc.perform(get("/pessoas/juridicas/{id}", existingId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(existingId))
                .andExpect(jsonPath("$.nomeFantasia").value("Empresa X LTDA"));
    }

    @Test
    void findJuridicaByIdShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        Mockito.when(pessoaJuridicaService.findById(nonExistingId))
                .thenThrow(new EntityNotFoundException("Pessoa Jurídica não encontrada. ID: " + nonExistingId));

        mockMvc.perform(get("/pessoas/juridicas/{id}", nonExistingId))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void findAllJuridicasShouldReturnList() throws Exception {
        Mockito.when(pessoaJuridicaService.findAll()).thenReturn(List.of(pessoaJuridicaDTO));

        mockMvc.perform(get("/pessoas/juridicas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(existingId))
                .andExpect(jsonPath("$[0].cnpj").value("04.252.011/0001-10"));
    }

    @Test
    void findJuridicasByNameShouldReturnList() throws Exception {
        Mockito.when(pessoaJuridicaService.findByName("Empresa")).thenReturn(List.of(pessoaJuridicaDTO));

        mockMvc.perform(get("/pessoas/juridicas/busca")
                        .param("nome", "Empresa"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].razaoSocial").value("Empresa X Comércio LTDA"));
    }

    @Test
    void findJuridicaByCnpjShouldReturnPessoaJuridicaDTO() throws Exception {
        String cnpj = "04252011000110"; // sem / e sem máscara

        Mockito.when(pessoaJuridicaService.findByCnpj(cnpj)).thenReturn(pessoaJuridicaDTO);

        mockMvc.perform(get("/pessoas/juridicas/cnpj/{cnpj}", cnpj))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(existingId));
    }

    @Test
    void findJuridicaByCnpjShouldReturnNotFoundWhenCnpjDoesNotExist() throws Exception {
        String cnpj = "00000000000000";

        Mockito.when(pessoaJuridicaService.findByCnpj(cnpj))
                .thenThrow(new EntityNotFoundException("CNPJ não encontrado"));

        mockMvc.perform(get("/pessoas/juridicas/cnpj/{cnpj}", cnpj))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }


    @Test
    void findJuridicasPageShouldReturnPage() throws Exception {
        Mockito.when(pessoaJuridicaService.findPage(Mockito.eq(0), Mockito.eq(10))).thenReturn(pjPage);

        mockMvc.perform(get("/pessoas/juridicas/paged")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(existingId));
    }

    @Test
    void findJuridicasPageShouldReturn400WhenServiceThrowsIllegalArgument() throws Exception {
        Mockito.when(pessoaJuridicaService.findPage(Mockito.anyInt(), Mockito.anyInt()))
                .thenThrow(new IllegalArgumentException("size deve ser maior que zero"));

        mockMvc.perform(get("/pessoas/juridicas/paged")
                        .param("page", "0")
                        .param("size", "0"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }


    @Test
    void deleteJuridicaShouldReturnNoContentWhenIdExists() throws Exception {
        Mockito.doNothing().when(pessoaJuridicaService).delete(existingId);

        mockMvc.perform(delete("/pessoas/juridicas/{id}", existingId))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteJuridicaShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        Mockito.doThrow(new EntityNotFoundException("Pessoa Jurídica não encontrada. ID: " + nonExistingId))
                .when(pessoaJuridicaService).delete(nonExistingId);

        mockMvc.perform(delete("/pessoas/juridicas/{id}", nonExistingId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void replaceEnderecosJuridicaShouldReturnUpdatedPessoaJuridicaDTO() throws Exception {
        Mockito.when(pessoaJuridicaService.findById(existingId)).thenReturn(pessoaJuridicaDTO);
        Mockito.when(pessoaJuridicaService.update(Mockito.any())).thenReturn(pessoaJuridicaDTO);

        String body = "[" + enderecoJson(123L) + "]";

        mockMvc.perform(put("/pessoas/juridicas/{id}/enderecos", existingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(existingId));

        Mockito.verify(pessoaJuridicaService).findById(existingId);
        Mockito.verify(pessoaJuridicaService).update(Mockito.any());
    }

    @Test
    void replaceEnderecosJuridicaShouldReturn500WhenEnderecoInvalid() throws Exception {
        String body = """
    [{
      "ruaLogra": "",
      "cep": "",
      "numero": "",
      "bairro": "",
      "uf": "",
      "cidade": "",
      "tipoEndereco": null
    }]
    """;

        mockMvc.perform(put("/pessoas/juridicas/{id}/enderecos", existingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500));
    }
    @Test
    void replaceEnderecosJuridicaShouldReturn404WhenPessoaNotFound() throws Exception {
        Mockito.when(pessoaJuridicaService.findById(existingId))
                .thenThrow(new EntityNotFoundException("Pessoa Jurídica não encontrada. ID: " + existingId));

        String body = "[" + enderecoJson(123L) + "]";

        mockMvc.perform(put("/pessoas/juridicas/{id}/enderecos", existingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void addEnderecoJuridicaShouldReturnCreatedAndLocation() throws Exception {
        PessoaJuridicaDTO current = buildPessoaJuridica(existingId);
        current.setEnderecos(new ArrayList<>(List.of(buildEndereco(10L))));

        PessoaJuridicaDTO updated = buildPessoaJuridica(existingId);
        updated.setEnderecos(new ArrayList<>(List.of(buildEndereco(10L), buildEndereco(20L))));

        Mockito.when(pessoaJuridicaService.findById(existingId)).thenReturn(current);
        Mockito.when(pessoaJuridicaService.update(Mockito.any())).thenReturn(updated);

        mockMvc.perform(post("/pessoas/juridicas/{id}/enderecos", existingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(enderecoJson(null)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").value(existingId));
    }

    @Test
    void addEnderecoJuridicaShouldReturn422WhenEnderecoInvalid() throws Exception {
        String invalidEndereco = """
        {
          "ruaLogra": "",
          "cep": "",
          "numero": "",
          "bairro": "",
          "uf": "",
          "cidade": "",
          "tipoEndereco": null
        }
        """;

        mockMvc.perform(post("/pessoas/juridicas/{id}/enderecos", existingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidEndereco))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.status").value(422));
    }

    @Test
    void deleteEnderecoJuridicaShouldReturnNoContentAndRemoveEndereco() throws Exception {
        PessoaJuridicaDTO current = buildPessoaJuridica(existingId);
        EnderecoDTO e1 = buildEndereco(10L);
        EnderecoDTO e2 = buildEndereco(20L);
        current.setEnderecos(new ArrayList<>(List.of(e1, e2)));

        Mockito.when(pessoaJuridicaService.findById(existingId)).thenReturn(current);
        Mockito.when(pessoaJuridicaService.update(Mockito.any())).thenReturn(current);

        mockMvc.perform(delete("/pessoas/juridicas/{id}/enderecos/{enderecoId}", existingId, 20L))
                .andExpect(status().isNoContent());

        ArgumentCaptor<PessoaJuridicaDTO> captor = ArgumentCaptor.forClass(PessoaJuridicaDTO.class);
        Mockito.verify(pessoaJuridicaService).update(captor.capture());

        assertThat(captor.getValue().getEnderecos())
                .extracting(EnderecoDTO::getId)
                .containsExactly(10L);
    }

    private PessoaFisicaDTO buildPessoaFisica(Long id) {
        PessoaFisicaDTO dto = new PessoaFisicaDTO();
        dto.setId(id);
        dto.setNome("João da Silva");
        dto.setEmail("joao@email.com");
        dto.setTelefone("82999999999");
        dto.setCpf("529.982.247-25"); // cpf válido conhecido
        dto.setDataNascimento(new Date(0)); // passado
        dto.setEmpresaId(10L);
        dto.setEnderecos(new ArrayList<>());
        return dto;
    }

    private PessoaJuridicaDTO buildPessoaJuridica(Long id) {
        PessoaJuridicaDTO dto = new PessoaJuridicaDTO();
        dto.setId(id);
        dto.setNome("Empresa X");
        dto.setEmail("contato@empresa.com");
        dto.setTelefone("1133334444");
        dto.setCnpj("04.252.011/0001-10"); // cnpj válido conhecido
        dto.setInscEstadual("ISENTO");
        dto.setInscMunicipal("123");
        dto.setNomeFantasia("Empresa X LTDA");
        dto.setRazaoSocial("Empresa X Comércio LTDA");
        dto.setCategoria("VAREJO");
        dto.setEnderecos(new ArrayList<>());
        return dto;
    }

    private EnderecoDTO buildEndereco(Long id) {
        EnderecoDTO e = new EnderecoDTO();
        e.setId(id);
        e.setRuaLogra("Rua A");
        e.setCep("57000-000");
        e.setNumero("10");
        e.setComplemento("AP 101");
        e.setBairro("Centro");
        e.setUf("AL");
        e.setCidade("Maceió");
        return e;
    }

}
