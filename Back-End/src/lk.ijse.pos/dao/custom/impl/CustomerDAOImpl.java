package lk.ijse.pos.dao.custom.impl;

import lk.ijse.pos.dao.custom.CustomerDAO;
import lk.ijse.pos.entity.Customer;
import lk.ijse.pos.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Ashan Sandeep
 * @date : 1/3/2023
 * @since : 0.1.0
 **/

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public ArrayList<Customer> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute(connection, "SELECT * FROM Customer");
        ArrayList<Customer> allCustomers = new ArrayList<>();
        while (result.next()) {
            allCustomers.add(new Customer(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getDouble(4)
            ));
        }
        return allCustomers;
    }

    @Override
    public boolean save(Connection connection, Customer entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(connection, "INSERT INTO Customer VALUES(?, ?, ?, ?)", entity.getCustomerId(), entity.getCustomerName(), entity.getAddress(), entity.getSalary());
    }

    @Override
    public boolean update(Connection connection, Customer entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(connection, "UPDATE Customer SET customerName=?, address=?, salary=? WHERE customerId=?", entity.getCustomerName(), entity.getAddress(), entity.getSalary(), entity.getCustomerId());
    }

    @Override
    public boolean delete(Connection connection, String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(connection, "DELETE FROM Customer WHERE customerId=?", id);
    }

    @Override
    public Customer search(Connection connection, String s) throws SQLException, ClassNotFoundException {
        return null;
    }
}
