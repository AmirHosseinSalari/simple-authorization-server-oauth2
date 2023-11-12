package ir.amirhosseinsalari.authserver.userclass;

import org.springframework.security.core.GrantedAuthority;

public class MyGrant implements GrantedAuthority {
    @Override
    public String getAuthority() {
        return "password";
    }
}
