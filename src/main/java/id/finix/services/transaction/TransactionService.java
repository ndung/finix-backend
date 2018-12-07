package id.finix.services.transaction;

import id.finix.domain.Biller;
import id.finix.domain.Product;

import java.util.List;

import id.finix.domain.Reseller;
import id.finix.repositories.BillerRepository;
import id.finix.repositories.ProductRepository;
import id.finix.repositories.ResellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BillerRepository billerRepository;

    @Autowired
    private ResellerRepository resellerRepository;

    public List<Product> getAllProducts(String userId){
        Reseller rs = resellerRepository.findOne(userId);
        if (rs!=null){
            String sdId = rs.getSubdealerId();
            String rsId = sdId.replaceFirst("SD", "RS");
            String mdId = "MD000001";
            return productRepository.getProductList(rsId, sdId, mdId);
        }
        return null;
    }

    public List<Product> getProductsByCategory(String userId, int categoryId){
        Reseller rs = resellerRepository.findOne(userId);
        if (rs!=null){
            String sdId = rs.getSubdealerId();
            String rsId = sdId.replaceFirst("SD", "RS");
            String mdId = "MD000001";
            return productRepository.getProductListByCategory(rsId, sdId, mdId, categoryId);
        }
        return null;
    }

    public List<Product> getProductsByBiller(String userId, String billerId){
        Reseller rs = resellerRepository.findOne(userId);
        if (rs!=null){
            String sdId = rs.getSubdealerId();
            String rsId = sdId.replaceFirst("SD", "RS");
            String mdId = "MD000001";
            return productRepository.getProductListByBiller(rsId, sdId, mdId, billerId);
        }
        return null;
    }

    public List<Biller> getBillersByCategory(int categoryId){
        return billerRepository.getActiveBillers(categoryId);
    }
}
