package OnlineShopProject.OnlineShop.dao;

import OnlineShopProject.OnlineShop.entity.Order;
import OnlineShopProject.OnlineShop.entity.OrderDetail;
import OnlineShopProject.OnlineShop.entity.Product;
import OnlineShopProject.OnlineShop.model.CartInfo;
import OnlineShopProject.OnlineShop.model.CartLineInfo;
import OnlineShopProject.OnlineShop.model.CustomerInfo;
import OnlineShopProject.OnlineShop.model.OrderInfo;
import OnlineShopProject.OnlineShop.repository.OrderDetailRepository;
import OnlineShopProject.OnlineShop.repository.OrderRepository;
import OnlineShopProject.OnlineShop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Transactional
@Repository
public class OrderDAO {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    private int getMaxOrderNum() {
        Integer value = orderRepository.findMaxOrderNum();
        if (value == null) {
            return 0;
        }
        return value;
    }

    public void saveOrder(CartInfo cartInfo) {
        int orderNum = this.getMaxOrderNum() + 1;
        Order order = new Order();

        order.setId(UUID.randomUUID().toString());
        order.setOrderNum(orderNum);
        order.setOrderDate(new Date());
        order.setAmount(cartInfo.getAmountTotal());

        CustomerInfo customerInfo = cartInfo.getCustomerInfo();
        order.setCustomerName(customerInfo.getName());
        order.setCustomerEmail(customerInfo.getEmail());
        order.setCustomerPhone(customerInfo.getPhone());
        order.setCustomerAddress(customerInfo.getAddress());

        orderRepository.save(order);

        List<CartLineInfo> lines = cartInfo.getCartLines();

        for (CartLineInfo line : lines) {
            OrderDetail detail = new OrderDetail();
            detail.setId(UUID.randomUUID().toString());
            detail.setOrder(order);
            detail.setAmount(line.getAmount());
            detail.setPrice(line.getProductInfo().getPrice());
            detail.setQuanity(line.getQuantity());

            String code = line.getProductInfo().getCode();
            Product product = productRepository.findProductByCode(code);
            detail.setProduct(product);

            orderDetailRepository.save(detail);
        }

        cartInfo.setOrderNum(orderNum);
    }

    public List<Order> listOrder(){
        return orderRepository.findAll();
    }

    public List<OrderDetail> listOrderDetails(String orderId){
        return orderDetailRepository.findByOrderId(orderId);
    }

    public OrderInfo getOrderInfo(String orderId) {
        Order order = orderRepository.findById(orderId).get();
        if (order == null) {
            return null;
        }
        return new OrderInfo(order.getId(), order.getOrderDate(), //
                order.getOrderNum(), order.getAmount(), order.getCustomerName(), //
                order.getCustomerAddress(), order.getCustomerEmail(), order.getCustomerPhone());
    }
}