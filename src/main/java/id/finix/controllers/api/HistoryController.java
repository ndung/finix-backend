package id.finix.controllers.api;

import id.finix.Application;
import id.finix.controllers.Response;
import id.finix.domain.AccountLog;
import id.finix.domain.Deposit;
import id.finix.domain.ProfitSettlement;
import id.finix.services.transaction.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Application.API_PATH + "/history")
public class HistoryController extends BaseController{

    @Autowired
    private HistoryService historyService;

    @RequestMapping(value = "/get-account-logs/{date}", method = RequestMethod.GET)
    public ResponseEntity<Response> getAccountLogs(@RequestHeader(Application.AUTH) String token,
                                                   @PathVariable("date") String date) {
        String userId = getUserId(token);
        if (userId==null){
            return FORBIDDEN;
        }
        try {
            List<AccountLog> list = historyService.getAccountLogs(userId, date);
            return getHttpStatus(new Response(list));
        } catch (Exception e) {
            e.printStackTrace();
            return getHttpStatus(new Response(e.getMessage()));
        }
    }

    @RequestMapping(value = "/get-settlement-logs/", method = RequestMethod.GET)
    public ResponseEntity<Response> getSettlementLogs(@RequestHeader(Application.AUTH) String token) {
        String userId = getUserId(token);
        if (userId==null){
            return FORBIDDEN;
        }
        try {
            List<ProfitSettlement> list = historyService.getSettlementLogs(userId);
            return getHttpStatus(new Response(list));
        } catch (Exception e) {
            e.printStackTrace();
            return getHttpStatus(new Response(e.getMessage()));
        }
    }

    @RequestMapping(value = "/get-deposit-logs/{page}", method = RequestMethod.GET)
    public ResponseEntity<Response> getDepositLogs(@RequestHeader(Application.AUTH) String token,
                                                   @PathVariable("page") int page) {
        String userId = getUserId(token);
        if (userId==null){
            return FORBIDDEN;
        }
        try {
            List<Deposit> list = historyService.getDepositLogs(userId, page);
            return getHttpStatus(new Response(list));
        } catch (Exception e) {
            e.printStackTrace();
            return getHttpStatus(new Response(e.getMessage()));
        }
    }
}
