package sample.base;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Book {

    public StringProperty name;
    public StringProperty price;
    public boolean checkbox;

    public Book() {
        checkbox = false;
        name = new SimpleStringProperty();
        price = new SimpleStringProperty();
    }

    public String getName() {
        return name.get();
    }

    public StringProperty getNameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public boolean isCheckbox() {
        return checkbox;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }

    public String getPrice() {
        return price.get();
    }

    public StringProperty getPriceProperty() {
        return price;
    }

    public void setPrice(String price) {
        this.price.set(price);
    }
}
