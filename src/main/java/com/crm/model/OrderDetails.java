package com.crm.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

@Entity
@Table(name = "order_details", schema = "public", catalog = "crm")
public class OrderDetails implements Serializable {
    private Integer ordersId;
    private Integer productsId;
    private Integer qty;
    private BigDecimal price; // ?
    private BigDecimal total; // ?

    @Basic
    @Column(name = "orders_id", unique = true, nullable = false)
    public int getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(int ordersId) {
        this.ordersId = ordersId;
    }

    @Basic
    @Column(name = "products_id", nullable = false)
    public int getProductsId() {
        return productsId;
    }

    public void setProductsId(int productsId) {
        this.productsId = productsId;
    }

    @Basic
    @Column(name = "qty", nullable = false)
    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Basic
    @Column(name = "price", nullable = false, precision = 2)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Basic
    @Column(name = "total", nullable = true, precision = 2)
    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
