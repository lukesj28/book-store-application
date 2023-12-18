package sample.control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.base.Cust;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class CustControl implements Initializable {

    @FXML
    private TableView<Cust> table;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TableColumn<Cust, String> usernameCol;
    @FXML
    private TableColumn<Cust, String> passwordCol;
    @FXML
    private TableColumn<Cust, Integer> pointsCol;
    @FXML
    private Button addCust;
    @FXML
    private Button delete;
    @FXML
    private Button back;
    ObservableList<Cust> dataList;

    @Override
    public void initialize(URL location, ResourceBundle src) {
        try {
            readFileData();
            back.setOnAction(val -> {
                HomeDisplayControl.bookStage.close();
                LoginDisplayControl.setHome();
            });

            addCust.setOnAction(val -> {
                try {
                    if (usernameField.getText().equals("") || passwordCol.getText().equals("")) {
                        Alert tempAlert = new Alert(Alert.AlertType.ERROR);
                        tempAlert.setTitle("User Error");
                        tempAlert.setHeaderText("One of the input fields are Blank. Please try again.");
                        tempAlert.show();
                    } else {
                        ArrayList<Cust> tempData = new ArrayList<>();
                        try {
                            Scanner scanner = new Scanner(new FileReader("customers.txt"));
                            while (scanner.hasNext()) {
                                String[] line = scanner.nextLine().split(",");
                                Cust tempCus = new Cust();
                                tempCus.setUsername(line[0]);
                                tempCus.setPassword(line[1]);
                                tempCus.setPoints(Integer.parseInt(line[2]));
                                tempData.add(tempCus);
                            }
                            int tempCheck = 0;
                            for (int i = 0; i < tempData.size(); i++) {
                                if (usernameField.getText().equals(tempData.get(i).getUsername())) {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Error Status");
                                    alert.setHeaderText("Customer already exists.");
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

            delete.setOnAction(val -> {
                if (table.getSelectionModel().getSelectedItem() != null) {
                    Cust selectCust = table.getSelectionModel().getSelectedItem();
                    ArrayList<Cust> tempData = new ArrayList<>();
                    try {
                        Scanner scanner = new Scanner(new FileReader("customers.txt"));
                        while (scanner.hasNext()) {
                            String[] line = scanner.nextLine().split(",");
                            Cust tempCus = new Cust();
                            tempCus.setUsername(line[0]);
                            tempCus.setPassword(line[1]);
                            tempCus.setPoints(Integer.parseInt(line[2]));
                            if (selectCust.getPassword().equals(tempCus.getPassword()) && !(selectCust.getUsername().equals(tempCus.getUsername()) && selectCust.getPoints() == tempCus.getPoints())) {
                                tempData.add(tempCus);
                            }
                        }
                        FileWriter tempWrite = new FileWriter("customers.txt");
                        for (Cust c : tempData) {
                            tempWrite.write(c.getUsername() + "," + c.getPassword() + "," + c.getPoints() + "\n");
                        }
                        tempWrite.close();
                        readFileData();
                        Alert tempAlert = new Alert(Alert.AlertType.INFORMATION);
                        tempAlert.setTitle("Information Update");
                        tempAlert.setHeaderText("Customer Deleted.");
                        tempAlert.show();
                    } catch (Exception e) {
                        e.printStackTrace();
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
        Scanner scanner = new Scanner(new FileReader("customers.txt"));
        while (scanner.hasNext()) {
            String[] tempSplit = scanner.nextLine().split(",");
            Cust Cust = new Cust();
            Cust.setUsername(tempSplit[0]);
            Cust.setPassword(tempSplit[1]);
            Cust.setPoints(Integer.parseInt(tempSplit[2]));
            dataList.add(Cust);
        }
        this.pointsCol.setCellValueFactory(new PropertyValueFactory<>("points"));
        this.usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        this.passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        this.table.setItems(dataList);
    }

    private void writeFileData() throws IOException {
        FileWriter writer = new FileWriter("customers.txt", true);
        writer.write(usernameField.getText() + "," + passwordField.getText() + ",0\n");
        writer.close();
    }

}
