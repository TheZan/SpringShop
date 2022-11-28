package OnlineShopProject.OnlineShop.repository;

import OnlineShopProject.OnlineShop.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    Account findAccountByUserName(String username);
}
