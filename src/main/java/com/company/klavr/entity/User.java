package com.company.klavr.entity;

import io.jmix.core.annotation.Secret;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.SystemLevel;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.security.authentication.JmixUserDetails;
import org.checkerframework.common.aliasing.qual.Unique;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@JmixEntity
@Entity
@Table(name = "USER_", indexes = {
        @Index(name = "IDX_USER__ON_USERNAME", columnList = "USERNAME", unique = true)
})
public class User implements JmixUserDetails {

    @Id
    @Column(name = "ID", nullable = false)
    @JmixGeneratedValue
    private UUID id;

    @InstanceName
    @Unique
    @Column(name = "USERNAME", nullable = false)
    protected String username;

    @Secret
    @SystemLevel
    @Column(name = "PASSWORD")
    protected String password;

    @Column(name = "ACTIVE")
    protected Boolean active = true;

    @Transient
    protected Collection<? extends GrantedAuthority> authorities;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities != null ? authorities : Collections.emptyList();
    }

    @Override
    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
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
        return Boolean.TRUE.equals(active);
    }


}