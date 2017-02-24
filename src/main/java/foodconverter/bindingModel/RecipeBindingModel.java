package foodconverter.bindingModel;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by Iliya on 7.1.2017 г..
 */
public class RecipeBindingModel {

    @NotNull
    @Length(min = 1, max = 100)
    private String products;

    @NotNull
    @Length(min = 3, max = 100)
    private String title;

    @NotNull
    @Length(min = 1, max = 100)
    private double price;

    @NotNull
    @Length(min = 1, max = 300)
    private String summary;

    public RecipeBindingModel() {
        super();
    }

    public RecipeBindingModel(String products, String title, double price, String summary) {
        this.products = products;
        this.title = title;
        this.price = price;
        this.summary = summary;
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

    public String getProducts() {

        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
