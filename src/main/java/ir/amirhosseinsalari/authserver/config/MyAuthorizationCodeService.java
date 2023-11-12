package ir.amirhosseinsalari.authserver.config;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;

public class MyAuthorizationCodeService extends RandomValueAuthorizationCodeServices {
    @Override
    protected void store(String s, OAuth2Authentication oAuth2Authentication) {
        System.out.println("Authorization code service: " + s);
    }

    @Override
    protected OAuth2Authentication remove(String s) {
        return null;
    }
}
