package com.example.lab3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class adminController implements Initializable {

    public TextField iname;
    public TextField iemail;
    public TextField ipassword;
    public TextField iid;

    @FXML
    private TableView<Admin> tableView;
    @FXML
    private TableColumn<Admin, Integer> id;
    @FXML
    private TableColumn<Admin, String> name;
    @FXML
    private TableColumn<Admin, String> email;
    @FXML
    private TableColumn<Admin, String> password;

    ObservableList<Admin> list = FXCollections.observableArrayList();

    private String jdbcUrl = "jdbc:mysql://localhost:3306/db_ems";
    private String dbUser = "root";
    private String dbPassword = "";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        password.setCellValueFactory(new PropertyValueFactory<>("password"));
        tableView.setItems(list);

        populateTable();
    }

    public void populateTable() {
        list.clear();
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "SELECT * FROM tbl_admin";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name"); // Assuming column name is 'name'
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                list.add(new Admin(id, name, email, password));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(ActionEvent actionEvent) {
        String name = iname.getText();
        String email = iemail.getText();
        String password = ipassword.getText();

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "INSERT INTO tbl_admin (name, email, password) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            preparedStatement.executeUpdate();

            populateTable(); // Refresh the table
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(ActionEvent actionEvent) {
        Admin selectedRecord = tableView.getSelectionModel().getSelectedItem();
        if (selectedRecord == null) {
            showAlert("No record selected", "Please select a record to update.");
            return;
        }

        int selectedId = selectedRecord.getId();
        String name = iname.getText();
        String email = iemail.getText();
        String password = ipassword.getText();

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "UPDATE tbl_admin SET name = ?, email = ?, password = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            preparedStatement.setInt(4, selectedId);
            preparedStatement.executeUpdate();

            populateTable(); // Refresh the table
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(ActionEvent actionEvent) {
        Admin selectedRecord = tableView.getSelectionModel().getSelectedItem();
        if (selectedRecord == null) {
            showAlert("No record selected", "Please select a record to delete.");
            return;
        }

        int selectedId = selectedRecord.getId();

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "DELETE FROM tbl_admin WHERE id = ?";
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
    }

    public void onHelloButtonClick(ActionEvent actionEvent) {
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
}


