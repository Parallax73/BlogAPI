package br.com.blogapi.domain.post;

import java.time.LocalDateTime;

public record PostListDTO(
        Long postId,
        String texto,
        LocalDateTime dia,
        Long usuarioId
) {
    public PostListDTO(Post post){
        this(
                post.getPostId(),
                post.getTexto(),
                post.getDia(),
                post.getUsuarioId()
        );
    }
}
