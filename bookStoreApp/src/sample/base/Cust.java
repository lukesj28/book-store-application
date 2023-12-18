package sample.base;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Cust {

    public State state;
    public StringProperty status;
    public StringProperty username;
    public StringProperty password;
    public IntegerProperty points;

    public Cust() {
        username = new SimpleStringProperty();
        password = new SimpleStringProperty();
        points = new SimpleIntegerProperty();
        status = new SimpleStringProperty();
    }

    public Cust(String username, String password, int points, State state) {
        setUsername(username);
        setPoints(points);
        setPassword(password);
        setState(state);
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty getStatusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty getUsernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty getPasswordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public int getPoints() {
        return points.get();
    }

    public IntegerProperty getPointsProperty() {
        return points;
    }

    public void setPoints(int points) {
        this.points.set(points);
    }
}
