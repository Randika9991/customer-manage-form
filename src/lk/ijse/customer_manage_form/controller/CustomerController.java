package lk.ijse.customer_manage_form.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class CustomerController {

    private static final String URL = "jdbc:mysql://localhost:3306/customeranditem";
    private static final Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    public TextField txtid;
    public TextField txtname;
    public TextField txtsalary;
    public TextField txtaddress;

    public void onsaveclick(ActionEvent actionEvent) throws SQLException {
        String custId = txtid.getText();
        String name = txtname.getText();
        String address = txtaddress.getText();
        double salary = Double.parseDouble(txtsalary.getText());

        try(Connection connection = DriverManager.getConnection(URL,props)){
            String sql = "INSERT INTO customer(custId,custName,address,salary)"+
                    "VALUES(?,?,?,?)";

            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1,custId);
            pstm.setString(2,name);
            pstm.setString(3,address);
            pstm.setDouble(4,salary);

            int affectedRows = pstm.executeUpdate();

            if(affectedRows>0){
                new Alert(Alert.AlertType.CONFIRMATION,"Customer Added!").show();

            }



        }
        txtid.setText("");
        txtname.setText("");
        txtaddress.setText("");
        txtsalary.setText("");
    }

    public void deleateclick(ActionEvent actionEvent) throws SQLException {
        String deleteid = txtid.getText();
        try(Connection connection = DriverManager.getConnection(URL,props)){
             String sql = "DELETE FROM Customer WHERE custId = ?";

             PreparedStatement pstm = connection.prepareStatement(sql);
             pstm.setString(1,deleteid);

             int affctedRows = pstm.executeUpdate();
            System.out.println(affctedRows > 0 ? "deleate!!" : "not deleate!!");
        }
      txtid.setText("");

    }

    public void updateclick(ActionEvent actionEvent) throws SQLException {
       String custid = txtid.getText();
       String custName = txtname.getText();
       String address = txtaddress.getText();
       double salary = Double.parseDouble(txtsalary.getText());

        try(Connection connection = DriverManager.getConnection(URL,props)){
            String sql = "UPDATE customer SET custID=?,custName=?,address=?,salary=? WHERE custId=?";

            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1,custid);
            pstm.setString(2,custName);
            pstm.setString(3,address);
            pstm.setDouble(4,salary);
            pstm.setString(5,onActionid);


            int affectedRows = pstm.executeUpdate();

            if(affectedRows>0){
                new Alert(Alert.AlertType.CONFIRMATION,"Customer Added!").show();

            }



        }

        txtid.setText("");
        txtname.setText("");
        txtaddress.setText("");
        txtsalary.setText("");
    }

    String onActionid;
    public void idOnAction(ActionEvent actionEvent) throws SQLException {
         onActionid = txtid.getText();
        try(Connection connection = DriverManager.getConnection(URL,props)){
            String sql = "SELECT* FROM Customer WHERE custId = ?";

            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1,onActionid);

            ResultSet resultSet = pstm.executeQuery();

            if (resultSet.next()){
                String tepmid = resultSet.getString(1);
                String tempName = resultSet.getString(2);
                String tempAddress = resultSet.getString(3);
                double tempSalary = resultSet.getDouble(4);

                txtid.setText(tepmid);
                txtname.setText(tempName);
                txtaddress.setText(tempAddress);
                txtsalary.setText(String.valueOf(tempSalary));

                System.out.println(tepmid + " - " + tempName + " - " + tempAddress + " - " + tempSalary);
            }
        }
    }

    private Stage stage;
    private Scene scene;
    private Parent root;
    public void onItemFormManager(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/lk/ijse/customer_manage_form/view/itemForm.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Items Management Form");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}
