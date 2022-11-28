package OnlineShopProject.OnlineShop.form;

import OnlineShopProject.OnlineShop.entity.Account;

public class RegistrationForm {
    private String username;
    private String password;
    private final String role = "ROLE_EMPLOYEE";
    private final Boolean isActive = true;

    public RegistrationForm(){}

    public RegistrationForm(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public Boolean getActive() {
        return isActive;
    }

    public Account ConvertToAccount(){
        Account account = new Account();
        account.setUserName(getUsername());
        account.setEncrytedPassword(getPassword());
        account.setUserRole(getRole());
        account.setActive(getActive());

        return account;
    }
}
