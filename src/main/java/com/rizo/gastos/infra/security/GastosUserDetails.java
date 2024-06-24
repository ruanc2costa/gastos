package com.rizo.gastos.infra.security;

import com.rizo.gastos.model.Usuario;
import com.rizo.gastos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GastosUserDetails implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        String username, password = null;
        List<GrantedAuthority> authorities = null;
        List<Usuario> usuario = usuarioRepository.findByEmail(email);
        if(usuario.isEmpty()){
            throw new UsernameNotFoundException("User not found" + email);
        } else{
            username = usuario.get(0).getEmail();
            password = usuario.get(0).getSenha();
            authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(usuario.get(0).getRole()));
        }
        return new User(username, password, authorities);
    }
}
