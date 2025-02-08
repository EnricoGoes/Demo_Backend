package br.edu.ifba.demo.backend.api.model;
import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "genero")
public class GeneroModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_genero")
    private Long idGenero;
    @Column(name = "genero_nome", nullable = false, unique = true)
    private String GeneroNome;
}
