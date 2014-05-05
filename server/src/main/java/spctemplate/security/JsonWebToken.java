package spctemplate.security;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.ReadOnlyJWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.security.SecureRandom;
import java.text.ParseException;
import java.util.Date;

/**
 * Provides Json Web Token functionality.
 * <p/>
 * User: Sigurd Stendal
 * Date: 30.04.14
 */
public class JsonWebToken {

    private static String ISSUER = "http://spc-template";

    // Generate random 32-bit shared secret
    // The secret is re-generated on restart, so tokens will not survive a restart
    // NB! This strategy does not play well with clustering!
    private static SecureRandom random = new SecureRandom();
    private static byte[] sharedSecret = new byte[32];

    static {
        random.nextBytes(sharedSecret);
    }

    public static String generate(String username, String sessionId) {

        try {
            // Create HMAC signer
            JWSSigner signer = new MACSigner(sharedSecret);

            // Prepare JWT with claims set
            JWTClaimsSet claimsSet = new JWTClaimsSet();
            claimsSet.setSubject(username);
            claimsSet.setCustomClaim("session", sessionId);
            claimsSet.setIssueTime(new Date());
            claimsSet.setIssuer(ISSUER);

            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);

            // Apply the HMAC
            signedJWT.sign(signer);

            // To serialize to compact form, produces something like
            // eyJhbGciOiJIUzI1NiJ9.SGVsbG8sIHdvcmxkIQ.onO9Ihudz3WkiauDO2Uhyuz0Y18UASXlSc1eS0NkWyA
            return signedJWT.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException("Failed while generating JWT", e);
        }
    }

    public static JwtClaims verifyAndGetClaims(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);

            JWSVerifier verifier = new MACVerifier(sharedSecret);

            if (signedJWT.verify(verifier)) {
                ReadOnlyJWTClaimsSet claims = signedJWT.getJWTClaimsSet();
                return new JwtClaims() {
                    @Override
                    public String getSubject() {
                        return claims.getSubject();
                    }

                    @Override
                    public String getSessionId() {
                        return (String) claims.getCustomClaim("session");
                    }
                };
            } else {
                throw new RuntimeException("JWT did not verify: " + token);
            }
        } catch (ParseException e) {
            throw new RuntimeException("Failed while parsing JWT: " + token, e);
        } catch (JOSEException e) {
            throw new RuntimeException("Failed while verifying JWT: " + token, e);
        }
    }

}

/**
 * A simplified JWT claim
 */
interface JwtClaims {

    String getSubject();

    String getSessionId();

}
