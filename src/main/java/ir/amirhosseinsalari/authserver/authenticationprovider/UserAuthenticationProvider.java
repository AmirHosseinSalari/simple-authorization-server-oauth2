package ir.amirhosseinsalari.authserver.authenticationprovider;

import ir.amirhosseinsalari.authserver.userclass.UserClassEntity;
import ir.amirhosseinsalari.authserver.userclass.UserClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component("userAuthenticationProvider")
public class UserAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UserClassRepository userClassRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials() != null ? authentication.getCredentials().toString() : null;
        UserClassEntity userClassEntity = loadUser(username,password);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userClassEntity,null, authentication.getAuthorities());
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private UserClassEntity loadUser(String username,String password){
        return userClassRepository.findByUsernameAndPassword(username,password);
    }
}
