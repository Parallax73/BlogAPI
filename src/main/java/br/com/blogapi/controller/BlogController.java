package br.com.blogapi.controller;

import br.com.blogapi.domain.post.*;
import br.com.blogapi.domain.usuario.User;
import br.com.blogapi.domain.usuario.UserDTO;
import br.com.blogapi.domain.usuario.UserLoginDTO;
import br.com.blogapi.domain.usuario.UserRepository;
import br.com.blogapi.infra.security.TokenDTO;
import br.com.blogapi.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("blog")
@SecurityRequirement(name = "bearer-key")
public class BlogController {

    private final AuthenticationManager manager;

    private final TokenService service;

    private final UserRepository repository;
    private final PostRepository postRepository;

    private Long idHolder=null;



    public BlogController(AuthenticationManager manager, TokenService service, UserRepository repository, PostRepository postRepository) {
        this.manager = manager;
        this.service = service;
        this.repository = repository;
        this.postRepository=postRepository;

    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity registerUser(@RequestBody @Valid UserDTO dto) {
        var usuario = new User(dto);
        usuario.passEncoder(dto);
        if(!repository.findIfExists(usuario.getNome()).equals(usuario.getNome())){
            repository.save(usuario);
            return ResponseEntity.ok("User successfully created");
        }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists");



    }

    @PostMapping("/login")
    @Transactional
    public ResponseEntity loginUser(@RequestBody @Valid UserLoginDTO dto) {

        User user = repository.findUsuarioByNome(dto.nome());
        idHolder = user.getUsuarioId();

        var authenticationToken = new UsernamePasswordAuthenticationToken(dto.nome(), dto.senha());
        var authentication = manager.authenticate(authenticationToken);
        var tokenJWT = service.gerarToken((User) authentication.getPrincipal());

        return ResponseEntity.status(HttpStatus.OK).body(new TokenDTO(tokenJWT));
    }

    @PostMapping("/logout")
    public ResponseEntity logoutUser(){
        if (idHolder==null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Você ainda não fez o login");
        }
        idHolder=null;
        return ResponseEntity.ok("Deslogado com sucesso");
    }

    @PostMapping("/posts")
    @Transactional
    public ResponseEntity createPost(@RequestBody @Valid PostDTO dto){
        if (idHolder==null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Você ainda não fez o login");
        }
        var post = new Post(dto);
        post.setUsuario(idHolder);
        postRepository.save(post);
        return ResponseEntity.ok(post);
    }

    @PutMapping("/posts/{id}")
    @Transactional
    public void editPost(@PathVariable("id") Long id, @RequestBody PostEditDTO dto){
        var post = postRepository.getReferenceById(id);
        post.editarPost(dto);
    }

    @GetMapping("/posts")
    public Page<PostListDTO> listPosts(Pageable pageable){
        if (idHolder!=null) {
            return postRepository.findAllByUsuarioIdOrderByDiaAsc(idHolder,pageable).map(PostListDTO::new);
        }
            return postRepository.findAllOrderByDiaAsc(pageable).map(PostListDTO::new);

    }

    @GetMapping("/posts/{id}")
    public ResponseEntity listPostsById(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(postRepository.findAllByPostId(id));
    }

    @DeleteMapping("/posts/{id}")
    @Transactional
    public void deletePost(@PathVariable("id") Long id){
        postRepository.deleteById(id);
    }
}
