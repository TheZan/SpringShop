package OnlineShopProject.OnlineShop.dao;

import OnlineShopProject.OnlineShop.entity.Account;
import OnlineShopProject.OnlineShop.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class AccountDAO {
    @Autowired
    private AccountRepository accountRepository;

    @Transactional(rollbackFor = Exception.class)
    public Boolean addAccount(Account account) {
        if(account != null){
            Boolean isDuplicated = accountRepository.findAccountByUserName(account.getUserName()) != null;

            if(!isDuplicated){
                accountRepository.save(account);
                return true;
            }
        }

        return false;
    }

}