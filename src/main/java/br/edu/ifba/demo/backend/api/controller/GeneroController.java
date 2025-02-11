package br.edu.ifba.demo.backend.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.edu.ifba.demo.backend.api.dto.GeneroDTO;
import br.edu.ifba.demo.backend.api.model.GeneroModel;
import br.edu.ifba.demo.backend.api.model.LivroModel;
import br.edu.ifba.demo.backend.api.repository.GeneroRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/genero")
@RequiredArgsConstructor
public class GeneroController {

    @Autowired
    private final GeneroRepository generoRepository;

    // Método para adicionar um novo gênero
    @PostMapping
    public ResponseEntity<GeneroDTO> addGenero(@RequestBody GeneroDTO generoDTO) {
        GeneroModel genero = new GeneroModel();
        genero.setGeneroNome(generoDTO.getNome());
        GeneroModel savedGenero = generoRepository.save(genero);
        return ResponseEntity.ok(GeneroDTO.converter(savedGenero));
    }

    // Método para listar todos os gêneros
    @GetMapping("/listall")
    public List<GeneroDTO> listall() {
        var generos = generoRepository.findAll();
        return generos.stream().map(GeneroDTO::converter).collect(Collectors.toList());
    }

    // Método para buscar um gênero pelo id
    @GetMapping("/{id}")
    public GeneroModel getById(@PathVariable("id") Long id) {
        return generoRepository.findById(id).orElse(null);
    }

    // Método para atualizar um gênero por id
    @PutMapping("/{id}")
    public ResponseEntity<GeneroModel> updateGenero(@PathVariable("id") Long id, @RequestBody GeneroDTO generoDTO) {
        GeneroModel genero = generoRepository.findById(id).orElse(null);
        if (genero != null) {
            genero.setGeneroNome(generoDTO.getNome());
            generoRepository.save(genero);
            return ResponseEntity.ok(genero);
        }
        return ResponseEntity.notFound().build();
    }

    // Método para deletar um gênero por id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenero(@PathVariable("id") Long id) {
        GeneroModel genero = generoRepository.findById(id).orElse(null);
        if (genero != null) {
            generoRepository.delete(genero);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
