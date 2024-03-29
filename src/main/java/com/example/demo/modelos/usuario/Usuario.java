package com.example.demo.modelos.usuario;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.modelos.Crianca;
import com.example.demo.modelos.Responsavel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @OneToOne
    @JoinColumn(name = "id_responsavel")
    private Responsavel responsavel;

    @OneToOne
    @JoinColumn(name = "id_crianca")
    private Crianca crianca;

    private UserRole role;

    public Usuario(String email, String senha, UserRole role) {
        this.email = email;
        this.senha = senha;
        this.role = role;
    }

    public void vincularResponsavel(Responsavel responsavel) {
        this.responsavel = responsavel;
    }

    public void vincularCrianca(Crianca crianca) {
        this.crianca = crianca;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.RESPONSAVEL)
            return List.of(new SimpleGrantedAuthority("ROLE_RESPONSAVEL"), new SimpleGrantedAuthority("ROLE_CRIANCA"));
        else
            return List.of(new SimpleGrantedAuthority("ROLE_CRIANCA"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
