package com.example.lab3;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class employeeController implements Initializable {

    public TextField iname;
    public TextField iaddress;
    public TextField isalary;
    public TextField iid;

    @FXML
    private TableView<Employee> tableView;
    @FXML
    private TableColumn<Employee, Integer> idColumn;
    @FXML
    private TableColumn<Employee, String> nameColumn;
    @FXML
    private TableColumn<Employee, String> addressColumn;
    @FXML
    private TableColumn<Employee, Double> salaryColumn;

    ObservableList<Employee> list = FXCollections.observableArrayList();

    private String jdbcUrl = "jdbc:mysql://localhost:3306/db_ems";
    private String dbUser = "root";
    private String dbPassword = "";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        tableView.setItems(list);

        populateTable();
    }

    public void populateTable() {
        list.clear();
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "SELECT * FROM tbl_employee";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                double salary = resultSet.getDouble("salary");
                list.add(new Employee(id, name, address, salary));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(ActionEvent actionEvent) {
        String name = iname.getText();
        String address = iaddress.getText();
        double salary = Double.parseDouble(isalary.getText());
        String Fsalary = String.valueOf(calculateSalary(salary));

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "INSERT INTO tbl_employee (name, address, salary) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, address);
            preparedStatement.setDouble(3, salary);
            preparedStatement.executeUpdate();

            populateTable(); // Refresh the table
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(ActionEvent actionEvent) {
        Employee selectedRecord = tableView.getSelectionModel().getSelectedItem();
        if (selectedRecord == null) {
            showAlert("No record selected", "Please select a record to update.");
            return;
        }

        int selectedId = selectedRecord.getId();
        String name = iname.getText();
        String address = iaddress.getText();
        double salary = Double.parseDouble(isalary.getText());
        String Fsalary = String.valueOf(calculateSalary(salary));
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "UPDATE tbl_employee SET name = ?, address = ?, salary = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, address);
            preparedStatement.setDouble(3, salary);
            preparedStatement.setInt(4, selectedId);
            preparedStatement.executeUpdate();

            populateTable(); // Refresh the table
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(ActionEvent actionEvent) {
        Employee selectedRecord = tableView.getSelectionModel().getSelectedItem();
        if (selectedRecord == null) {
            showAlert("No record selected", "Please select a record to delete.");
            return;
        }

        int selectedId = selectedRecord.getId();

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "DELETE FROM tbl_employee WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, selectedId);
            preparedStatement.executeUpdate();

            populateTable(); // Refresh the table
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void viewdata(ActionEvent actionEvent) {
        populateTable();
    }

    public void onHelloButtonClick(ActionEvent actionEvent) {
        // Define additional actions here if needed
    }

    public void BackClick(ActionEvent actionEvent) {

        try {
            Parent secondScene = FXMLLoader.load(getClass().getResource("dashboard.fxml"));

            Stage secondStage = new Stage();
            secondStage.setTitle("login");
            secondStage.setScene(new Scene(secondScene));
            Stage firstSceneStage = (Stage) iid.getScene().getWindow();
            firstSceneStage.close();


            secondStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        public Double calculateSalary (Double Salary){
            Double yearly = 12 * Salary;
            return yearly;
        }
    }






