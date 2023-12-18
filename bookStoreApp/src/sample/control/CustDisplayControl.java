package sample.control;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.Main;
import sample.base.Book;
import sample.base.Cust;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustDisplayControl implements Initializable {

    @FXML
    private Label headerLabel;
    @FXML
    private TableView<Book> table;
    @FXML
    private TableColumn<Book, String> nameCol;
    @FXML
    private TableColumn<Book, String> priceCol;
    @FXML
    private TableColumn select;
    @FXML
    private Button buyButton;
    @FXML
    private Button redeemButton;
    @FXML
    private Button logoutButton;

    ObservableList<Book> dataList;

    public static ArrayList<Book> purchasedBooks;
    public static Stage costStage;

    @Override
    public void initialize(URL location, ResourceBundle src) {
        try {
            readFileData();
            String tempVal = "Gold";
            if (LoginDisplayControl.cust.getPoints() <= 1000) {
                tempVal = "Silver";
            }
            headerLabel.setText("Welcome " + LoginDisplayControl.cust.getUsername() + ". You have " + LoginDisplayControl.cust.getPoints() + " points.\nYour status is " + tempVal + ".");

            logoutButton.setOnAction(val -> {
                try {
                    LoginDisplayControl.custStage.close();
                    Main.Login();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            buyButton.setOnAction(val -> {
                purchasedBooks = new ArrayList<>();
                int tempBookVal = 0;
                for (Book book : dataList) {
                    if (book.isCheckbox()) {
                        purchasedBooks.add(book);
                        tempBookVal++;
                    }
                }
                if (tempBookVal == 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Status");
                    alert.setHeaderText("Please select a book.");
                    alert.show();
                } else {
                    LoginDisplayControl.custStage.close();
                    setCustCostDisplay();
                    try {
                        for (int i = 0; i < purchasedBooks.size(); i++) {
                            Book book = table.getSelectionModel().getSelectedItem();
                            ArrayList<Book> tempData = new ArrayList<>();
                            Scanner scanner1 = new Scanner(new FileReader("books.txt"));
                            while (scanner1.hasNext()) {
                                String[] line = scanner1.nextLine().split(",");
                                Book tempBook = new Book();
                                tempBook.setName(line[0]);
                                tempBook.setPrice(line[1]);
                                if (!(purchasedBooks.get(i).getName().equals(tempBook.getName()) && purchasedBooks.get(i).getPrice().equals(tempBook.getPrice()))) {
                                    tempData.add(tempBook);
                                }
                            }
                            try {
                                FileWriter tempWrite1 = new FileWriter("books.txt");
                                for (Book tempBook : tempData) {
                                    tempWrite1.write(tempBook.getName() + "," + tempBook.getPrice() + "\n");
                                }
                                tempWrite1.close();
                            } catch (IOException ex) {
                                Logger.getLogger(CustDisplayControl.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            readFileData();
                        }

                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(CustDisplayControl.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            });

            redeemButton.setOnAction(val -> {
                purchasedBooks = new ArrayList<>();
                int purchaseTotal = 0;
                int tempBookValRedeem = 0;
                for (Book book : dataList) {
                    if (book.isCheckbox()) {
                        purchasedBooks.add(book);
                        tempBookValRedeem += 1;
                    }
                }
                if (tempBookValRedeem == 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Status");
                    alert.setHeaderText("Please select a book.");
                    alert.show();
                } else {
                    for (Book book : purchasedBooks) {
                        purchaseTotal += Integer.parseInt(book.getPrice());
                    }
                    purchaseTotal = purchaseTotal * 100;
                    if (LoginDisplayControl.cust.getPoints() < purchaseTotal) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Status");
                        alert.setHeaderText("Not enough points.");
                        alert.show();
                    } else {

                        LoginDisplayControl.cust.setPoints(LoginDisplayControl.cust.getPoints() - purchaseTotal);
                        ArrayList<Cust> tempData = new ArrayList<>();

                        try {
                            Scanner scanner = new Scanner(new FileReader("customers.txt"));
                            while (scanner.hasNext()) {
                                String[] line = scanner.nextLine().split(",");
                                if (!(LoginDisplayControl.cust.getUsername().equals(line[0]) && LoginDisplayControl.cust.getPassword().equals(line[1]))) {
                                    Cust tempCust = new Cust();
                                    tempCust.setUsername(line[0]);
                                    tempCust.setPassword(line[1]);
                                    tempCust.setPoints(Integer.parseInt(line[2]));
                                    tempData.add(tempCust);
                                }
                            }
                            tempData.add(LoginDisplayControl.cust);
                            FileWriter writer = new FileWriter("customers.txt");
                            for (Cust c : tempData) {
                                writer.write(c.getUsername() + "," + c.getPassword() + "," + c.getPoints() + "\n");
                            }
                            writer.close();
                            Alert tempAlert = new Alert(Alert.AlertType.INFORMATION);
                            tempAlert.setTitle("Purchase Status");
                            tempAlert.setHeaderText("Successfully Purchased");
                            tempAlert.show();
                            String tempVal2 = "Gold";
                            if (LoginDisplayControl.cust.getPoints() <= 1000) {
                                tempVal2 = "Silver";
                            }
                            headerLabel.setText("Welcome " + LoginDisplayControl.cust.getUsername() + ". You have " + LoginDisplayControl.cust.getPoints() + " points. Your status is " + tempVal2 + ".");
                            try {
                                for (int i = 0; i < purchasedBooks.size(); i++) {
                                    Book book = table.getSelectionModel().getSelectedItem();
                                    ArrayList<Book> tempData1 = new ArrayList<>();
                                    Scanner scanner1 = new Scanner(new FileReader("books.txt"));
                                    while (scanner1.hasNext()) {
                                        String[] line = scanner1.nextLine().split(",");
                                        Book tempBook = new Book();
                                        tempBook.setName(line[0]);
                                        tempBook.setPrice(line[1]);
                                        if (!(purchasedBooks.get(i).getName().equals(tempBook.getName()) && purchasedBooks.get(i).getPrice().equals(tempBook.getPrice()))) {
                                            tempData1.add(tempBook);
                                        }
                                    }
                                    try {
                                        FileWriter tempWrite1 = new FileWriter("books.txt");
                                        for (Book tempBook : tempData1) {
                                            tempWrite1.write(tempBook.getName() + "," + tempBook.getPrice() + "\n");
                                        }
                                        tempWrite1.close();
                                    } catch (IOException ex) {
                                        Logger.getLogger(CustDisplayControl.class.getName()).log(Level.SEVERE, null, ex);
                                    }

                                    readFileData();
                                }

                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(CustDisplayControl.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readFileData() throws FileNotFoundException {
        this.table.setItems(null);
        dataList = FXCollections.observableArrayList();
        Scanner scanner = new Scanner(new FileReader("books.txt"));
        while (scanner.hasNext()) {
            String[] line = scanner.nextLine().split(",");
            Book book = new Book();
            book.setName(line[0]);
            book.setPrice(line[1]);
            dataList.add(book);
        }
        select.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Book, CheckBox>, ObservableValue<CheckBox>>) tempArg -> {
            Book book = tempArg.getValue();
            CheckBox checkBox = new CheckBox();
            checkBox.selectedProperty().setValue(false);
            checkBox.selectedProperty().addListener((ov, old_val, new_val) -> book.setCheckbox(new_val));
            return new SimpleObjectProperty<>(checkBox);
        });
        this.nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        this.table.setItems(dataList);
    }

    public static void setCustCostDisplay() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(LoginDisplayControl.class.getResource("../viewStage/custCost.fxml"));
            BorderPane pane = loader.load();
            costStage = new Stage();
            costStage.setTitle("Customer");
            costStage.setScene(new Scene(pane));
            costStage.show();
            Main.mainStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setCustMainDisplay() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(LoginDisplayControl.class.getResource("../viewStage/custDisplay.fxml"));
            BorderPane pane = loader.load();
            LoginDisplayControl.custStage = new Stage();
            LoginDisplayControl.custStage.setTitle("Customer");
            LoginDisplayControl.custStage.setScene(new Scene(pane));
            LoginDisplayControl.custStage.show();
            Main.mainStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
