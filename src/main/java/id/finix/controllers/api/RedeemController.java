package id.finix.controllers.api;

import id.finix.Application;
import id.finix.controllers.Response;
import id.finix.domain.Gift;
import id.finix.domain.RedeemLog;
import id.finix.domain.Reseller;
import id.finix.services.redeem.RedeemService;
import id.finix.services.user.ResellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(Application.API_PATH + "/redeem")
public class RedeemController extends BaseController {

    @Autowired
    RedeemService redeemService;

    @Autowired
    ResellerService resellerService;

    @RequestMapping(value = "/get-gifts", method = RequestMethod.GET)
    public ResponseEntity<Response> getGifts(@RequestHeader(Application.AUTH) String token) {
        try {
            String userId = getUserId(token);
            if (userId==null){
                return FORBIDDEN;
            }
            return getHttpStatus(new Response(redeemService.getGifts()));

        } catch (Exception e) {
            return getHttpStatus(new Response(e.getMessage()));
        }
    }

    @RequestMapping(value = "/redeem-gift/{id}", method = RequestMethod.POST)
    public ResponseEntity<Response> redeemGift(@RequestHeader(Application.AUTH) String token,
                                                @PathVariable("id") long id) {
        try {
            String userId = getUserId(token);
            if (userId==null){
                return FORBIDDEN;
            }
            Gift gift = redeemService.getGift(id);
            Reseller rs = resellerService.findOne(userId);
            if (rs!=null && gift!=null) {
                if (rs.getPoints()>gift.getPts()){
                    redeemService.redeem(userId, gift);
                    return getHttpStatus(new Response(resellerService.findOne(userId)));
                }else {
                    return getHttpStatus(new Response("Point Anda tidak mencukupi"));
                }
            }else{
                return getHttpStatus(new Response("User atau hadiah tidak ditemukan"));
            }
        } catch (Exception e) {
            return getHttpStatus(new Response(e.getMessage()));
        }
    }

    @RequestMapping(value = "/get-logs", method = RequestMethod.POST)
    public ResponseEntity<Response> getLogs(@RequestHeader(Application.AUTH) String token) {
        try {
            String userId = getUserId(token);
            if (userId==null){
                return FORBIDDEN;
            }
            List<RedeemLog> list = redeemService.getLogs(userId);
            if (list!=null) {
                return getHttpStatus(new Response(list));
            }else{
                return getHttpStatus(new Response("Log tidak ditemukan"));
            }
        } catch (Exception e) {
            return getHttpStatus(new Response(e.getMessage()));
        }
    }

    @RequestMapping(value = "/convert-to-balance", method = RequestMethod.POST)
    public ResponseEntity<Response> convertToBalance(@RequestHeader(Application.AUTH) String token) {
        try {
            String userId = getUserId(token);
            if (userId==null){
                return FORBIDDEN;
            }
            Reseller rs = resellerService.findOne(userId);
            return getHttpStatus(new Response(resellerService.findOne(userId)));
        } catch (Exception e) {
            return getHttpStatus(new Response(e.getMessage()));
        }
    }
}
