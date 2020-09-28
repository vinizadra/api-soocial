package com.example.demo.services;

import com.example.demo.entity.AuthRequest;
import com.example.demo.entity.AuthResponse;
import com.example.demo.entity.Usuario;
import com.example.demo.exception.RegradeNegocioException;
import com.example.demo.utils.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private UsuarioService usuarioService;

    private String mensagem;
    private String jwt;
    private Usuario usuario;

    public Authentication authenticationToken(AuthRequest authRequest) throws Exception {
        try {
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getSenha())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Usuário ou senha inválidos", e);
        }
    }

    public ResponseEntity<?> carregaToken(UserDetails userDetails) {
        this.mensagem = "Autorização efetuada com sucesso";
        this.jwt = jwtTokenUtil.generateToken(userDetails);
        this.usuario = usuarioService
                .findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RegradeNegocioException("Usuário não encontrado"));

        return ResponseEntity.ok(new AuthResponse(mensagem, jwt, usuario));
    }
}
