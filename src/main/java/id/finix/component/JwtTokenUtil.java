package id.finix.component;

import id.finix.domain.Reseller;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtTokenUtil {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private long expiration;

	public String createToken(Reseller rs) {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
		JwtBuilder builder = Jwts.builder()
				.setId(UUID.randomUUID().toString())
				.setIssuedAt(now)
				.setSubject(rs.getId())
				.setIssuer("id.finix")
				.signWith(signatureAlgorithm, signingKey);
		if (expiration >= 0) {
			long expMillis = nowMillis + (expiration * 1000);
			Date exp = new Date(expMillis);
			builder.setExpiration(exp);
		}
		return builder.compact();
	}

	public boolean authenticate(String token) {
		try {
			Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secret)).parseClaimsJws(token).getBody();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public String getUserId(String token) {
		try {
			Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secret)).parseClaimsJws(token)
					.getBody();
			return claims.getSubject();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

}