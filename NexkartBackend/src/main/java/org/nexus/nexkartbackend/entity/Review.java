//package org.nexus.nexkartbackend.entity;
//
//import jakarta.persistence.*;
//
//import java.util.Date;
//
//@Entity
//@Table(name = "reviews")
//public class Review  {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(nullable = false)
//    private Long id;
//
//    @Column(length = 128, nullable = false)
//    private String headline;
//
//    @Column(length = 300, nullable = false)
//    private String comment;
//
//    private int rating;
//
//    @Column(nullable = false)
//    private Date reviewTime;
//
//    @ManyToOne
//    @JoinColumn(name = "product_id")
//    private Product product;
//
//    @ManyToOne
//    @JoinColumn(name = "customer_id")
//    private Customer customer;
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getHeadline() {
//        return headline;
//    }
//
//    public void setHeadline(String headline) {
//        this.headline = headline;
//    }
//
//    public String getComment() {
//        return comment;
//    }
//
//    public void setComment(String comment) {
//        this.comment = comment;
//    }
//
//    public int getRating() {
//        return rating;
//    }
//
//    public void setRating(int rating) {
//        this.rating = rating;
//    }
//
//    public Date getReviewTime() {
//        return reviewTime;
//    }
//
//    public void setReviewTime(Date reviewTime) {
//        this.reviewTime = reviewTime;
//    }
//
//    public Product getProduct() {
//        return product;
//    }
//
//    public void setProduct(Product product) {
//        this.product = product;
//    }
//
//    public Customer getCustomer() {
//        return customer;
//    }
//
//    public void setCustomer(Customer customer) {
//        this.customer = customer;
//    }
//
//    @Override
//    public String toString() {
//        return "Review [headline=" + headline + ", rating=" + rating + ", reviewTime=" + reviewTime + ", product="
//                + product.getShortName() + ", customer=" + customer.getFullName() + "]";
//    }
//
//
//}