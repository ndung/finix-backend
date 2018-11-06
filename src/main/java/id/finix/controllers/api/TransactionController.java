package id.finix.controllers.api;

import id.finix.Application;
import id.finix.controllers.Response;
import java.util.List;

import id.finix.domain.Product;
import id.finix.services.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Application.API_PATH + "/transaction")
public class TransactionController extends BaseController{

    @Autowired
    TransactionService transactionService;

    @RequestMapping(value = "/get-all-products", method = RequestMethod.GET)
    public ResponseEntity<Response> getAllProducts(){//(@RequestHeader(Application.AUTH) String token) {
        String userId = "081322245545";//getUserId(token);
        if (userId==null){
            return FORBIDDEN;
        }
        try {
            List<Product> list = transactionService.getAllProducts(userId);
            return getHttpStatus(new Response(list));
        } catch (Exception e) {
            e.printStackTrace();
            return getHttpStatus(new Response(e.getMessage()));
        }
    }

    @RequestMapping(value = "/get-products-by-category/{id}", method = RequestMethod.GET)
    public ResponseEntity<Response> getProductsByCategory(@RequestHeader(Application.AUTH) String token,
                                                          @PathVariable("id") int id) {
        String userId = getUserId(token);
        if (userId==null){
            return FORBIDDEN;
        }
        try {
            List<Product> list = transactionService.getProductsByCategory(userId, id);
            return getHttpStatus(new Response(list));
        } catch (Exception e) {
            e.printStackTrace();
            return getHttpStatus(new Response(e.getMessage()));
        }
    }

    @RequestMapping(value = "/get-products-by-biller/{id}", method = RequestMethod.GET)
    public ResponseEntity<Response> getProductsByBiller(@RequestHeader(Application.AUTH) String token,
                                                          @PathVariable("id") String id) {
        String userId = getUserId(token);
        if (userId==null){
            return FORBIDDEN;
        }
        try {
            List<Product> list = transactionService.getProductsByBiller(userId, id);
            return getHttpStatus(new Response(list));
        } catch (Exception e) {
            e.printStackTrace();
            return getHttpStatus(new Response(e.getMessage()));
        }
    }
}
