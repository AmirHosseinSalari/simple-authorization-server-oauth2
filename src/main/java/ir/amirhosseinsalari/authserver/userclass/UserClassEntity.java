package ir.amirhosseinsalari.authserver.userclass;

import ir.amirhosseinsalari.authserver.core.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserClassEntity extends BaseEntity implements UserDetails {

    private String username;
    private String password;

    public UserClassEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }


    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set s = new HashSet();
        s.add(new MyGrant());
        return s;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    @Transient
    public boolean isEnabled() {
        return true;
    }
}
