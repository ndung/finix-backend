package id.finix.controllers.api;

import id.finix.Application;
import id.finix.controllers.Response;
import id.finix.domain.BankAccount;
import id.finix.domain.Deposit;
import id.finix.services.topup.AddBalanceService;
import id.finix.services.topup.TopupRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(Application.API_PATH + "/topup")
public class TopupController extends BaseController{

    @Autowired
    private AddBalanceService addBalanceService;
    Logger logger = Logger.getLogger(TopupController.class);

    @RequestMapping(value = "/banks", method = RequestMethod.GET)
    public ResponseEntity<Response> getBankAccounts(@RequestHeader(Application.AUTH) String token) {
        try {
            String userId = getUserId(token);
            if (userId==null){
                return FORBIDDEN;
            }
            List<BankAccount> bankAccounts = addBalanceService.getBankAccounts();
            return getHttpStatus(new Response(bankAccounts));

        } catch (Exception e) {
            return getHttpStatus(new Response(e.getMessage()));
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<Response> addBalance(@RequestHeader(Application.AUTH) String token,
                                               @RequestBody TopupRequest request) {
        try {
            String userId = getUserId(token);
            if (userId==null){
                return FORBIDDEN;
            }
            Deposit deposit = addBalanceService.saveDeposit(userId, request);
            return getHttpStatus(new Response(deposit));

        } catch (Exception e) {
            e.printStackTrace();
            return getHttpStatus(new Response(e.getMessage()));
        }
    }

    @RequestMapping(value = "/getUnapprovedDeposit", method = RequestMethod.GET)
    public ResponseEntity<Response> getLastDeposit(@RequestHeader(Application.AUTH) String token) {
        try {
            String userId = getUserId(token);
            if (userId==null){
                return FORBIDDEN;
            }
            List<Deposit> list = addBalanceService.getUnapprovedDeposit(userId);
            return getHttpStatus(new Response(list));

        } catch (Exception e) {
            e.printStackTrace();
            return getHttpStatus(new Response(e.getMessage()));
        }
    }

    @RequestMapping(value = "/upload/{id}", method = RequestMethod.POST)
    public ResponseEntity<Response> uploadProof(@RequestHeader(Application.AUTH) String token,
                                                @PathVariable("id") long id,
                                                @RequestParam("proof") MultipartFile proof) {
        String userId = getUserId(token);
        if (userId==null){
            return FORBIDDEN;
        }
        try {
            Deposit deposit = addBalanceService.uploadProof(id, proof);
            return getHttpStatus(new Response(deposit));
        } catch (Exception e) {
            return getHttpStatus(new Response(e.getMessage()));
        }
    }
}
