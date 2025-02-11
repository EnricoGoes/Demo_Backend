package br.edu.ifba.demo.backend.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifba.demo.backend.api.repository.LivroRepository;
import br.edu.ifba.demo.backend.api.model.LivroModel;
import br.edu.ifba.demo.backend.api.dto.LivroDTO;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/livro")
public class LivroController {

    @Autowired
    private LivroRepository livroRepository;

    // Método para adicionar um novo livro
    @PostMapping
    public ResponseEntity<LivroModel> addLivro(@RequestBody LivroModel livro) {
        LivroModel savedLivro = livroRepository.save(livro);
        return new ResponseEntity<LivroModel>(savedLivro, HttpStatus.CREATED);
    }

    // Método para listar todos os livros
    @GetMapping("/listall")
    public List<LivroModel> listall() {
        var livros = livroRepository.findAll();
        return livros;
    }

    // Método para buscar um livro pelo id
    @GetMapping("/getById/{id}")
    public LivroModel getById(@PathVariable("id") Long id) {
        Optional<LivroModel> livro = livroRepository.findById(id);
        if (livro.isPresent())
            return livro.get();
        return null;
    }

    // Método para buscar um livro pelo isbn
    @GetMapping("/getByIsbn/{isbn}")
    public ResponseEntity<LivroDTO> buscarPorISBN(@PathVariable String isbn) {
        LivroModel livro = livroRepository.findByIsbn(isbn);
        return (livro != null) ? ResponseEntity.ok(LivroDTO.converter(livro))
                : ResponseEntity.notFound().build();
    }

    // Método para buscar um livro pelo titulo
    @GetMapping("/getByTitulo/{titulo}")
    public ResponseEntity<List<LivroDTO>> getbyTitulo(@PathVariable("titulo") String titulo) {
        List<LivroModel> livros = livroRepository.findByTitulo(titulo);
        if (livros.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            List<LivroDTO> livrosDTO = LivroDTO.converter(livros);
            return ResponseEntity.ok(livrosDTO);
        }
    }

    // Método para atualizar um livro
    @PutMapping("/{id}")
    public ResponseEntity<LivroDTO> atualizarLivro(@PathVariable Long id, @RequestBody LivroDTO livroAtualizado) {
        return livroRepository.findById(id)
                .map(livroExistente -> {
                    livroExistente.setTitulo(livroAtualizado.getTitulo());
                    livroExistente.setAutor(livroAtualizado.getAutor());
                    livroExistente.setEditora(livroAtualizado.getEditora());
                    livroExistente.setAnoPublicacao(livroAtualizado.getAnoPublicacao());
                    livroExistente.setGenero(null); // Tratar adequadamente se necessário
                    livroExistente.setIsbn(livroAtualizado.getIsbn());
                    livroExistente.setNumPaginas(livroAtualizado.getNumPaginas());
                    livroExistente.setSinopse(livroAtualizado.getSinopse());
                    livroExistente.setIdioma(livroAtualizado.getIdioma());
                    livroExistente.setDataCadastro(livroAtualizado.getDataCadastro());
                    livroExistente.setPreco(livroAtualizado.getPreco());

                    LivroModel livroSalvo = livroRepository.save(livroExistente);
                    return ResponseEntity.ok(LivroDTO.converter(livroSalvo));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Método para deletar um livro
    @DeleteMapping("/{id}")
    public ResponseEntity<LivroModel> deleteLivro(@PathVariable("id") Long id) {
        Optional<LivroModel> livro = livroRepository.findById(id);
        if (livro.isPresent()) {
            livroRepository.delete(livro.get());
            return new ResponseEntity<LivroModel>(livro.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}