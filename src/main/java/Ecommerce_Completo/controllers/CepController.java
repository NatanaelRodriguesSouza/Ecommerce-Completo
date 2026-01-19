package Ecommerce_Completo.controllers;

import Ecommerce_Completo.model.DTO.CepDTO;
import Ecommerce_Completo.service.CepService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cep")
public class CepController {

    private final CepService cepService;

    public CepController(CepService cepService) {
        this.cepService = cepService;
    }

    @GetMapping("/{cep}")
    public CepDTO buscar(@PathVariable String cep) {
        return cepService.buscarCep(cep);
    }
}

