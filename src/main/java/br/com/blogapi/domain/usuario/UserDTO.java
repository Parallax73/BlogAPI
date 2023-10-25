package br.com.blogapi.domain.usuario;

import jakarta.validation.constraints.NotBlank;



public record UserDTO(


        @NotBlank
        String nome,
        @NotBlank
        String senha) {
}
