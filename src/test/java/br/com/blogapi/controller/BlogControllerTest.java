package br.com.blogapi.controller;


import br.com.blogapi.domain.post.PostRepository;
import br.com.blogapi.domain.usuario.User;
import br.com.blogapi.domain.usuario.UserDTO;
import br.com.blogapi.domain.usuario.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase
class BlogControllerTest {
    @Autowired
    UserRepository repository;

    @Autowired
    PostRepository postRepository;

    @Test
    @DisplayName("Test if user is created correctly")
    void registerUser() {
        var usuario = new User(new UserDTO("Fabio", "1234"));
        var h = repository.getReferenceById(1L);
        repository.save(usuario);

        assertEquals(usuario.getNome(),h.getUsername());
        assertEquals(usuario.getPassword(),h.getPassword());
    }

}