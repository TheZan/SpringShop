package OnlineShopProject.OnlineShop.controllers;

import OnlineShopProject.OnlineShop.dao.AccountDAO;
import OnlineShopProject.OnlineShop.dao.OrderDAO;
import OnlineShopProject.OnlineShop.dao.ProductDAO;
import OnlineShopProject.OnlineShop.entity.Product;
import OnlineShopProject.OnlineShop.form.ProductForm;
import OnlineShopProject.OnlineShop.form.RegistrationForm;
import OnlineShopProject.OnlineShop.model.OrderInfo;
import OnlineShopProject.OnlineShop.validator.ProductFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Transactional
public class AdminController {

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private ProductFormValidator productFormValidator;

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @InitBinder
    public void myInitBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }

        if (target.getClass() == ProductForm.class) {
            dataBinder.setValidator(productFormValidator);
        }
    }

    @RequestMapping(value = { "/admin/login" }, method = RequestMethod.GET)
    public String login(Model model) {
        return "login";
    }

    @RequestMapping(value = { "/admin/registration" }, method = RequestMethod.GET)
    public String registration(Model model) {
        return "registration";
    }

    @RequestMapping(value = { "/admin/registration" }, method = RequestMethod.POST)
    public String registration(Model model,
                               @ModelAttribute("registrationForm") @Validated RegistrationForm registrationForm,
                               BindingResult result) {

        if (result.hasErrors()) {
            return "registration";
        }
        try {
            registrationForm.setPassword(passwordEncoder.encode(registrationForm.getPassword()));
            if(!accountDAO.addAccount(registrationForm.ConvertToAccount())){
                return "registration";
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "registration";
        }

        return "redirect:/admin/login";
    }

    @RequestMapping(value = { "/admin/accountInfo" }, method = RequestMethod.GET)
    public String accountInfo(Model model) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("userDetails", userDetails);
        return "accountInfo";
    }

    @RequestMapping(value = { "/admin/orderList" }, method = RequestMethod.GET)
    public String orderList(Model model) {
        var orders = orderDAO.listOrder();

        model.addAttribute("orders", orders);
        return "orderList";
    }

    @RequestMapping(value = { "/admin/product" }, method = RequestMethod.GET)
    public String product(Model model, @RequestParam(value = "code", defaultValue = "") String code) {
        ProductForm productForm = null;

        if (code != null && code.length() > 0) {
            Product product = productDAO.findProduct(code);
            if (product != null) {
                productForm = new ProductForm(product);
            }
        }
        if (productForm == null) {
            productForm = new ProductForm();
            productForm.setNewProduct(true);
        }
        model.addAttribute("productForm", productForm);
        return "product";
    }

    @RequestMapping(value = { "/admin/productRemove" }, method = RequestMethod.GET)
    public String productRemove(Model model, @RequestParam(value = "code", defaultValue = "") String code) {
        if (code != null && code.length() > 0) {
            Product product = productDAO.findProduct(code);
            if (product != null) {
                productDAO.removeProduct(product);
            }
        }
        return "redirect:/productList";
    }

    @RequestMapping(value = { "/admin/product" }, method = RequestMethod.POST)
    public String productSave(Model model, //
                              @ModelAttribute("productForm") @Validated ProductForm productForm, //
                              BindingResult result, //
                              final RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "product";
        }
        try {
            productDAO.save(productForm);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "");
            return "product";
        }

        return "redirect:/productList";
    }

    @RequestMapping(value = { "/admin/order" }, method = RequestMethod.GET)
    public String orderView(Model model, @RequestParam("orderId") String orderId) {
        OrderInfo orderInfo = null;
        if (orderId != null) {
            orderInfo = this.orderDAO.getOrderInfo(orderId);
        }
        if (orderInfo == null) {
            return "redirect:/admin/orderList";
        }

        var details = orderDAO.listOrderDetails(orderId);
        orderInfo.setDetails(details);

        model.addAttribute("orderInfo", orderInfo);

        return "order";
    }

}
