package ir.amirhosseinsalari.authserver.endpoint;

import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.crypto.Cipher;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.JWKSet;

@FrameworkEndpoint
public class JwkSetEndpoint {

    private KeyPair keyPair;
    private Cipher publicCipher;
    private Cipher privateCipher;

    public JwkSetEndpoint(KeyPair keyPair) {
        this.keyPair = keyPair;
        try{
            publicCipher = Cipher.getInstance("RSA");
            publicCipher.init(Cipher.ENCRYPT_MODE,keyPair.getPublic());
            privateCipher = Cipher.getInstance("RSA");
            privateCipher.init(Cipher.ENCRYPT_MODE,keyPair.getPrivate());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @GetMapping("/.well-known/jwks.json")
    @ResponseBody
    public Map<String,Object> getKey(){
        RSAPublicKey publicKey = (RSAPublicKey) this.keyPair.getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).build();
        return new JWKSet(key).toJSONObject();
    }

}
