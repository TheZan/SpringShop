package OnlineShopProject.OnlineShop.dao;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;

import OnlineShopProject.OnlineShop.entity.Product;
import OnlineShopProject.OnlineShop.form.ProductForm;
import OnlineShopProject.OnlineShop.model.ProductInfo;
import OnlineShopProject.OnlineShop.repository.ProductRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class ProductDAO {

    @Autowired
    private ProductRepository productRepository;

    public Product findProduct(String code) {
        try {
            var product = productRepository.findProductByCode(code);
            return product;
        } catch (NoResultException e) {
            return null;
        }
    }

    public ProductInfo findProductInfo(String code) {
        Product product = this.findProduct(code);
        if (product == null) {
            return null;
        }
        return new ProductInfo(product.getCode(), product.getName(), product.getPrice());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void save(ProductForm productForm) {
        String code = productForm.getCode();

        Product product = null;

        boolean isNew = false;
        if (code != null) {
            product = this.findProduct(code);
        }
        if (product == null) {
            isNew = true;
            product = new Product();
            product.setCreateDate(new Date());
        }
        product.setCode(code);
        product.setName(productForm.getName());
        product.setPrice(productForm.getPrice());

        if (productForm.getFileData() != null) {
            byte[] image = null;
            try {
                image = productForm.getFileData().getBytes();
            } catch (IOException e) {
            }
            if (image != null && image.length > 0) {
                product.setImage(image);
            }
        }
        if (isNew) {
            productRepository.save(product);
        }
    }

    public List<Product> queryProducts(int page, int maxResult, int maxNavigationPage, String likeName) {

        if(likeName.isEmpty()){
            return  productRepository.findAll();
        }
        else{
            return productRepository.findProductByName(likeName);
        }
    }

}