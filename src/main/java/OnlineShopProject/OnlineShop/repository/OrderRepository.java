package OnlineShopProject.OnlineShop.repository;

import OnlineShopProject.OnlineShop.entity.Account;
import OnlineShopProject.OnlineShop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    @Query(value = "Select max(o.order_num) from orders o", nativeQuery = true)
    Integer findMaxOrderNum();
}
