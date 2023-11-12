package ir.amirhosseinsalari.authserver.service;

import ir.amirhosseinsalari.authserver.userclass.MyGrant;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component("myClientDetailsService")
public class MyClientDetailsService implements ClientDetailsService {
    @Override
    public ClientDetails loadClientByClientId(String s) throws ClientRegistrationException {
        Set set = new HashSet();
        set.add(new MyGrant());
        BaseClientDetails baseClientDetails = new BaseClientDetails(
                s,
                null,
                "any",
                "authorization_code,refresh_token,password",
                null,
                "http://127.0.0.1:8080/login/oauth2/code/mysso"
        );
        baseClientDetails.setClientSecret("simplesecret");
        baseClientDetails.setAutoApproveScopes(Arrays.asList("any"));
        return baseClientDetails;
    }
}
