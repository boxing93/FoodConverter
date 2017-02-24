package foodconverter.entity;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Iliya on 6.1.2017 г..
 */
@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "date", nullable = false)
    @DateTimeFormat
    private Date date = new Date();

    @Column(name = "product", nullable = false)
    private String product;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "summary", nullable = false)
    private String summary;

    @ManyToOne
    @JoinColumn(nullable = false, name = "authorId")
    private User author;

    public Recipe(String title, Date date, String product, double price, String summary, User author) {
        this.title = title;
        this.date = date;
        this.product = product;
        this.price = price;
        this.summary = summary;
        this.author = author;
    }

    public Recipe(String product, double price, String summary, String title) {
        this.product = product;
        this.price = price;
        this.summary = summary;
        this.title = title;
    }

    public Recipe(String products, double price, String summary, String title, User userEntity) {
        this.product = products;
        this.price = price;
        this.summary = summary;
        this.title = title;
        this.author = userEntity;
    }

    public Recipe() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent(){
        return this.getSummary().substring(0, this.getContent().length() / 2) + "...";
    }
}
