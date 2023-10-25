package br.com.blogapi.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;


public interface UserRepository extends JpaRepository<User,Long> {
    UserDetails findByNome(String nome);

    @Query(value = "SELECT * from Usuarios WHERE nome = :nome",nativeQuery = true)
    User findUsuarioByNome(@Param("nome") String nome);

    @Query(value = "SELECT nome FROM usuarios WHERE nome = :nome",nativeQuery = true)
    String findIfExists(@Param("nome")String nome);
}
