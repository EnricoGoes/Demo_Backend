package br.edu.ifba.demo.backend.api.dto;

import br.edu.ifba.demo.backend.api.model.GeneroModel;
import lombok.Data;

@Data
public class GeneroDTO {
    private Long id;
    private String nome;

    public static GeneroDTO converter(GeneroModel generoModel) {
        GeneroDTO dto = new GeneroDTO();
        dto.setId(generoModel.getIdGenero());
        dto.setNome(generoModel.getGeneroNome());
        return dto;
    }
}
