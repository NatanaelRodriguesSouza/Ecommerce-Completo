package Ecommerce_Completo.service;

import Ecommerce_Completo.model.Acesso;
import Ecommerce_Completo.model.DTO.AcessoDTO;
import Ecommerce_Completo.repository.AcessoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AcessoService {

    @Autowired
    private AcessoRepository repository;

    public AcessoDTO insert(AcessoDTO dto) {
        Acesso acesso = new Acesso();
        acesso.setDescricao(dto.getDescricao());
        acesso = repository.save(acesso);
        return new AcessoDTO(acesso.getId(), acesso.getDescricao());
    }

    public List<AcessoDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(a -> new AcessoDTO(a.getId(), a.getDescricao()))
                .collect(Collectors.toList());
    }

    public AcessoDTO findById(Long id) {
        Acesso acesso = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Acesso não encontrado"));
        return new AcessoDTO(acesso.getId(), acesso.getDescricao());
    }

    public AcessoDTO findByDescricao(String descricao) {
        Acesso acesso = repository.findByDescricao(descricao)
                .orElseThrow(() -> new RuntimeException("Acesso não encontrado"));
        return new AcessoDTO(acesso.getId(), acesso.getDescricao());
    }

    public AcessoDTO update(Long id, AcessoDTO dto) {
        Acesso acesso = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Acesso não encontrado"));

        acesso.setDescricao(dto.getDescricao());
        acesso = repository.save(acesso);
        return new AcessoDTO(acesso.getId(), acesso.getDescricao());
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Acesso não encontrado");
        }
        repository.deleteById(id);
    }

    public Page<AcessoDTO> findPaged(String descricao, Pageable pageable) {

        Page<Acesso> page;

        if (descricao == null || descricao.isBlank()) {
            page = repository.findAll(pageable);
        } else {
            page = repository.findByDescricaoContainingIgnoreCase(descricao, pageable);
        }

        return page.map(a -> new AcessoDTO(a.getId(), a.getDescricao()));
    }


}
