package br.com.blogapi.domain.usuario;

public record UserLoginDTO(
        Long usuarioId,
        String nome,
        String senha
) {
}
