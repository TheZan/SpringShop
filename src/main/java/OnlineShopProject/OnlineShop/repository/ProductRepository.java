package OnlineShopProject.OnlineShop.repository;

import OnlineShopProject.OnlineShop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository  extends JpaRepository<Product, String> {
    Product findProductByCode(String code);
    List<Product> findProductByName(String name);
}
