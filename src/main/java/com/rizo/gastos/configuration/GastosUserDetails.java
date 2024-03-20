package com.rizo.gastos.configuration;

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

@Service
public class GastosUserDetails implements UserDetailsService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String userName, password = null;
        List<GrantedAuthority> authorities = null;
        List<Usuario> usuario = usuarioRepository.findByEmail(username);
        if(usuario.isEmpty()){
            throw new UsernameNotFoundException("User not found" + username);
        } else{
            userName = usuario.get(0).getEmail();
            password = usuario.get(0).getSenha();
            authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(usuario.get(0).getRoles()));
        }
        return new User(username, password, authorities);

    }
}
