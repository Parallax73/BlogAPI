package br.com.blogapi.domain.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<PostListDTO> findAllByPostId(Long id);
    List<PostListDTO> findAllByUsuarioId(Long id);

    @Query(value = "select * from Posts\n" +
            "ORDER BY dia asc;",nativeQuery = true)
    Page<Post> findAllOrderByDiaAsc(Pageable pageable);


    @Query(value = "SELECT * FROM Posts WHERE usuario_id = :id ORDER BY dia asc",nativeQuery = true)
    Page<Post> findAllByUsuarioIdOrderByDiaAsc(@Param("id") Long id, Pageable pageable);


}
