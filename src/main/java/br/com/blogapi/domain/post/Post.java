package br.com.blogapi.domain.post;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "Posts")
@Entity(name = "Posts")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    private String texto;
    private Long usuarioId;
    private LocalDateTime dia;


    public Post(PostDTO dto){
        this.texto=dto.texto();
        this.dia=LocalDateTime.now();

    }

    public void setUsuario(Long id){
        this.usuarioId=id;
    }

    public void editarPost(PostEditDTO dto){
        this.texto=dto.texto();
        this.dia=LocalDateTime.now();
    }

}
