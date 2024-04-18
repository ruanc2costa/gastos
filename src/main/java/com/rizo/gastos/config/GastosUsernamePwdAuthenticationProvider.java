package com.rizo.gastos.config;

import com.rizo.gastos.model.Usuario;
import com.rizo.gastos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GastosUsernamePwdAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String pwd = authentication.getCredentials().toString();
        List<Usuario> usuario = usuarioRepository.findByEmail(username);
        if(!usuario.isEmpty()) {
            if (passwordEncoder.matches(pwd, usuario.get(0).getSenha())) {
                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority(usuario.get(0).getRole()));
                return new UsernamePasswordAuthenticationToken(username, pwd, authorities);
            } else {
                throw new BadCredentialsException("Senha inválida");
            }
        }   else{
            throw new BadCredentialsException("Usuário não registrado com esses detalhes");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return(UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
