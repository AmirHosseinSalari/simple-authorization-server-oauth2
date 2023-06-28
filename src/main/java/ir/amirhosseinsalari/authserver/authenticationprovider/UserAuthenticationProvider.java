package ir.amirhosseinsalari.authserver.authenticationprovider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component("userAuthenticationProvider")
public class UserAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials() != null ? authentication.getCredentials().toString() : null;
        // TODO: check the username & password from database
        if(!username.equals("john") || !password.equals("123"))
            return null;
        User user = new User(username,password,authentication.getAuthorities());
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user,null, authentication.getAuthorities());
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
