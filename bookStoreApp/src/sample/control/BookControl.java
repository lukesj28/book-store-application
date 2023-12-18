package sample.control;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.FileWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.net.URL;
import javafx.fxml.FXML;
import java.util.ArrayList;
import sample.base.Book;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class BookControl implements Initializable {

    @FXML
    private TableView<Book> table;
    @FXML
    private Button addBookButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button backButton;
    @FXML
    private TableColumn<Book, String> bookNameCol;
    @FXML
    private TableColumn<Book, String> bookPriceCol;
    @FXML
    private TextField nameField;
    @FXML
    private TextField priceField;

    ObservableList<Book> dataList;

    @Override
    public void initialize(URL location, ResourceBundle src) {
        try {
            readFileData();
            backButton.setOnAction(val -> {
                HomeDisplayControl.bookStage.close();
                LoginDisplayControl.setHome();
            });

            addBookButton.setOnAction(val -> {
                try {
                    if (nameField.getText().equals("") || priceField.getText().equals("")) {
                        Alert tempAlert = new Alert(Alert.AlertType.ERROR);
                        tempAlert.setTitle("User Error");
                        tempAlert.setHeaderText("One of the input fields are Blank. Please try again.");
                        tempAlert.show();
                    } else {
                        ArrayList<Book> tempData = new ArrayList<>();
                        try {
                            Scanner scanner = new Scanner(new FileReader("books.txt"));
                            while (scanner.hasNext()) {
                                String[] line = scanner.nextLine().split(",");
                                Book tempBook = new Book();
                                tempBook.setName(line[0]);
                                tempBook.setPrice(line[1]);
                                tempData.add(tempBook);
                            }
                            int tempCheck = 0;
                            for (int i = 0; i < tempData.size(); i++) {
                                if (nameField.getText().equals(tempData.get(i).getName())) {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Error Status");
                                    alert.setHeaderText("Book already exists.");
                                    alert.show();
                                    tempCheck++;
                                }
                            }

                            if (tempCheck == 0) {
                                writeFileData();
                                readFileData();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            deleteButton.setOnAction(val -> {
                try {
                    if (table.getSelectionModel().getSelectedItem() != null) {
                        Book book = table.getSelectionModel().getSelectedItem();
                        ArrayList<Book> tempData = new ArrayList<>();
                        Scanner scanner = new Scanner(new FileReader("books.txt"));
                        while (scanner.hasNext()) {
                            String[] line = scanner.nextLine().split(",");
                            Book tempBook = new Book();
                            tempBook.setName(line[0]);
                            tempBook.setPrice(line[1]);
                            if (!(book.getName().equals(tempBook.getName()) && book.getPrice().equals(tempBook.getPrice()))) {
                                tempData.add(tempBook);
                            }
                        }
                        FileWriter tempWrite = new FileWriter("books.txt");
                        for (Book tempBook : tempData) {
                            tempWrite.write(tempBook.getName() + "," + tempBook.getPrice() + "\n");
                        }
                        tempWrite.close();
                        readFileData();
                        Alert tempAlert = new Alert(Alert.AlertType.INFORMATION);
                        tempAlert.setTitle("Information Update");
                        tempAlert.setHeaderText("Book Deleted.");
                        tempAlert.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
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
            String[] tempSplit = scanner.nextLine().split(",");
            Book tempBook = new Book();
            tempBook.setName(tempSplit[0]);
            tempBook.setPrice(tempSplit[1]);
            dataList.add(tempBook);
        }
        this.table.setItems(dataList);
        this.bookNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.bookPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    private void writeFileData() throws IOException {
        dataList = FXCollections.observableArrayList();
        FileWriter tempWrite = new FileWriter("books.txt", true);
        tempWrite.write(nameField.getText() + "," + priceField.getText() + "\n");
        tempWrite.close();
    }
}
