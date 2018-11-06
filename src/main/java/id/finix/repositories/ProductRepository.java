package id.finix.repositories;

import id.finix.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface ProductRepository extends JpaRepository<Product, String> {

    @Query(value = "SELECT a.product_id as product_id, p.biller_id as biller_id, p.description as name, d.icon as icon, " +
            "(a.sd_selling_price_margin+b.md_selling_price_margin+c.md_buying_price) as buying_price, d.subcategory as category, " +
            "p.long_desc as description, a.rs_poin as poin, a.rs_cashback_price as cashback " +
            "FROM product p, product_sd_to_rs a, product_md_to_sd b, product_md c, biller d " +
            "WHERE a.reseller_id=:rsId AND b.subdealer_id=:sdId AND c.master_dealer_id=:mdId and a.product_id=b.product_id " +
            "AND a.product_id=c.product_id AND a.product_id=p.product_id AND b.product_id=p.product_id AND c.product_id=p.product_id " +
            "AND p.biller_id=d.biller_id AND p.status=1 AND d.subcategory=:category " +
            "order by a.product_id asc",
            nativeQuery = true)
    List<Product> getProductListByCategory(@Param("rsId") String rsId,
                                                           @Param("sdId") String sdId,
                                                           @Param("mdId") String mdId,
                                                           @Param("category") int category);

    @Query(value = "SELECT a.product_id as product_id, p.biller_id as biller_id, p.description as name, d.icon as icon, " +
            "(a.sd_selling_price_margin+b.md_selling_price_margin+c.md_buying_price) as buying_price, d.subcategory as category, " +
            "p.long_desc as description, a.rs_poin as poin, a.rs_cashback_price as cashback " +
            "FROM product p, product_sd_to_rs a, product_md_to_sd b, product_md c, biller d " +
            "WHERE a.reseller_id=:rsId AND b.subdealer_id=:sdId AND c.master_dealer_id=:mdId and a.product_id=b.product_id " +
            "AND a.product_id=c.product_id AND a.product_id=p.product_id AND b.product_id=p.product_id AND c.product_id=p.product_id " +
            "AND p.biller_id=d.biller_id AND p.status=1 AND d.biller_id =:billerId " +
            "order by a.product_id asc",
            nativeQuery = true)
    List<Product> getProductListByBiller(@Param("rsId") String rsId,
                                           @Param("sdId") String sdId,
                                           @Param("mdId") String mdId,
                                           @Param("billerId") String billerId);

    @Query(value = "SELECT a.product_id as product_id, p.biller_id as biller_id, p.description as name, d.icon as icon, " +
            "(a.sd_selling_price_margin+b.md_selling_price_margin+c.md_buying_price) as buying_price, d.subcategory as category, " +
            "p.long_desc as description, a.rs_poin as poin, a.rs_cashback_price as cashback " +
            "FROM product p, product_sd_to_rs a, product_md_to_sd b, product_md c, biller d " +
            "WHERE a.reseller_id=:rsId AND b.subdealer_id=:sdId AND c.master_dealer_id=:mdId and a.product_id=b.product_id " +
            "AND a.product_id=c.product_id AND a.product_id=p.product_id AND b.product_id=p.product_id AND c.product_id=p.product_id " +
            "AND p.biller_id=d.biller_id AND p.status=1 " +
            "order by a.product_id asc",
            nativeQuery = true)
    List<Product> getProductList(@Param("rsId") String rsId,
                                           @Param("sdId") String sdId,
                                           @Param("mdId") String mdId);
}
