package OnlineShopProject.OnlineShop.repository;

import OnlineShopProject.OnlineShop.entity.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface AccountRepository extends Repository<Account, String> {
    @Query("SELECT ac FROM Account acc WHERE acc.userName = :username")
    Account findAccountByUserName(String username);
}
