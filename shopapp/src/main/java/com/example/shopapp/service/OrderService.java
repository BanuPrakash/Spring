package com.example.shopapp.service;

import com.example.shopapp.dao.CustomerDao;
import com.example.shopapp.dao.OrderDao;
import com.example.shopapp.dao.ProductDao;
import com.example.shopapp.dto.ProductRecord;
import com.example.shopapp.dto.Report;
import com.example.shopapp.entity.Customer;
import com.example.shopapp.entity.LineItem;
import com.example.shopapp.entity.Order;
import com.example.shopapp.entity.Product;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ProductDao productDao;
    private  final CustomerDao customerDao;
    private final OrderDao orderDao;

    /*
    total is computed,
    item amount has to computed
    order_date is system date
    order
        {
            "customer": {"email":"roger@gmail.com"},
            "items": [
                {
                    "product": {id: 3}, "qty": 2
                },
                {
                    "product": {id: 1}, "qty": 3
                }
            ]
        }
     */
    // Atomic operation
    @Transactional
    public String placeOrder(Order order) {
        double total = 0.0;
        List<LineItem> items = order.getItems();
        for(LineItem item : items) {
            Product p = productDao.findById(item.getProduct().getId()).get(); // PersistenceContext
            if(p.getQuantity() < item.getQty()) {
                throw  new IllegalArgumentException("Product " + p.getName() + " not is stock!!!");
            }
            // MANAGED
            p.setQuantity(p.getQuantity() - item.getQty()); // DIRTY CHECKING --> UPDATE SQL
            item.setAmount(p.getPrice() * item.getQty()); // add tax , discount, coupon
            total += item.getAmount();
        }
        order.setTotal(total);
        orderDao.save(order); // cascade takes care of saving line items
        return  "order placed Successfully!!!";
    }

    public List<Order> getOrders() {
        return orderDao.findAll();
    }
    public List<Report> getReport(Date date) {
        return orderDao.getReportForDate(date);
    }

    public Order getOrder(int id) {
        return orderDao.findById(id).get();
    }
    public long getProductCount() {
        return productDao.count();
    }
    public long getCustomerCount() {
        return customerDao.count();
    }

    public List<Product> findProductRange(double low, double high) {
        return productDao.findByPriceBetween(low, high);
    }
    public Customer findCustomerByEmail(String email) {
        return customerDao.findById(email).get();
    }

    public Product findProductById(int id) {
        Optional<Product> optionalProduct = productDao.findById(id);
        if(optionalProduct.isPresent()) {
            return  optionalProduct.get();
        }
        return null; // later change to Exception
    }

    public List<Product> getAllProducts() {
        return productDao.findAll();
    }

    public Product saveProduct(Product p) {
        return  productDao.save(p);
    }

    public Customer saveCustomer(Customer c) {
        return  customerDao.save(c);
    }

    public List<ProductRecord> getByRange(double low, double high) {
        return productDao.getByRange(low, high);
    }

    @Transactional
    public Product updateProduct(int id, double price) {
        productDao.updateProduct(price, id);
        return findProductById(id);
    }
}
