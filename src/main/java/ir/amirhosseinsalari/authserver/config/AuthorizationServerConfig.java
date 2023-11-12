package ir.amirhosseinsalari.authserver.config;
import ir.amirhosseinsalari.authserver.authenticationprovider.ClientAuthenticationProvider;
import ir.amirhosseinsalari.authserver.service.MyClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerEndpointsConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;

@Import(AuthorizationServerEndpointsConfiguration.class)
@Configuration("authorizationServerConfig")
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Value("${jwkset.keystore.path}")
    private Resource jwksetKeyStorePath;
    @Value("${jwkset.keystore.password}")
    private String jwksetKeyStorePassword;
    @Value("${jwkset.keystore.alias}")
    private String jwksetKeyStoreAlias;

    @Autowired
    private MyClientDetailsService clientDetailsService;
    @Autowired
    private ClientAuthenticationProvider clientAuthenticationProvider;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private MyAccessTokenConverter myAccessTokenConverter;
    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthorizationServerConfig(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }



        @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(this.authenticationManager)
                .accessTokenConverter(accessTokenConverter())
                .tokenStore(tokenStore())
                .authorizationCodeServices(myAuthorizationCodeService());
    }

    @Bean
    public MyAuthorizationCodeService myAuthorizationCodeService(){
        return  new MyAuthorizationCodeService();
    }

    @Bean
    public TokenStore tokenStore(){
        return new MyJwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter(){
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyPairBean());
        converter.setAccessTokenConverter(myAccessTokenConverter);
        return converter;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .passwordEncoder(passwordEncoder)
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients()
                .addAuthenticationProvider(clientAuthenticationProvider);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService);
    }

    @Bean
    public KeyPair keyPairBean(){
        // keytool -genkeypair -alias jwt-keystore -keyalg RSA -keysize 2048 -storetype JKS -validity 1825 -keypass 123456 -storepass 123456 -keystore ./jwt-keystore.jks -dname "C=IR ST=Tehran L=Tehran OU=ServerAdmin O=Organization CN=127.0.0.1"
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(jwksetKeyStorePath,jwksetKeyStorePassword.toCharArray());
        return keyStoreKeyFactory.getKeyPair(jwksetKeyStoreAlias);
    }
}
