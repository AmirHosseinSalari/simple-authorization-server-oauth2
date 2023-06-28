package ir.amirhosseinsalari.authserver.service;

import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component("myClientDetailsService")
public class MyClientDetailsService implements ClientDetailsService {
    @Override
    public ClientDetails loadClientByClientId(String s) throws ClientRegistrationException {
        BaseClientDetails baseClientDetails = new BaseClientDetails(
                s,
                null,
                "any",
                "authorization_code",
                null,
                "http://127.0.0.1:8080/login/oauth2/code/mysso"
        );
        baseClientDetails.setClientSecret("simplesecret");
        baseClientDetails.setAutoApproveScopes(Arrays.asList("any"));
        return baseClientDetails;
    }
}
