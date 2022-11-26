package OnlineShopProject.OnlineShop.repository;

import OnlineShopProject.OnlineShop.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    //@Query("SELECT ac FROM Account acc WHERE acc.userName = :username")
    Account findAccountByUserName(String username);
}
