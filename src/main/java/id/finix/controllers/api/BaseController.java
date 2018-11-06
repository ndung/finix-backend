package id.finix.controllers.api;


import id.finix.controllers.Response;
import id.finix.component.JwtTokenUtil;
import id.finix.domain.Reseller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class BaseController {

	@Autowired
	private JwtTokenUtil tokenUtil;

    protected final static ResponseEntity<Response> FORBIDDEN = new ResponseEntity<Response>(HttpStatus.FORBIDDEN);

    protected String createToken(Reseller rs) {
        return tokenUtil.createToken(rs);
    }

    protected boolean authenticate(String token) {
        return tokenUtil.authenticate(token);
    }

    protected String getUserId(String token) {
        return tokenUtil.getUserId(token);
    }

    protected ResponseEntity<Response> getHttpStatus(Response response) {
        HttpStatus hs = response.getData() == null ? HttpStatus.BAD_REQUEST :
                HttpStatus.OK;
		return new ResponseEntity<Response>(response, hs);
	}

    protected ResponseEntity<Response> getHttpStatus(Response response, HttpHeaders headers) {
        HttpStatus hs = response.getData() == null ? HttpStatus.BAD_REQUEST :
                HttpStatus.OK;
        return new ResponseEntity<Response>(response, headers, hs);
    }
}
