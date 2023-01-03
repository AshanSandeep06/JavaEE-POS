package lk.ijse.pos.dao.custom.impl;

import lk.ijse.pos.dao.custom.OrderDetailDAO;
import lk.ijse.pos.entity.Order_Detail;
import lk.ijse.pos.util.CrudUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Ashan Sandeep
 * @date : 1/3/2023
 * @since : 0.1.0
 **/

public class OrderDetailDAOImpl implements OrderDetailDAO {

    @Override
    public ArrayList<Order_Detail> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(Connection connection, Order_Detail entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(connection, "INSERT INTO Order_Detail VALUES(?, ?, ?, ?)", entity.getOrderId(), entity.getCode(), entity.getPrice(), entity.getQty());
    }

    @Override
    public boolean update(Connection connection, Order_Detail entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Connection connection, String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Order_Detail search(Connection connection, String s) throws SQLException, ClassNotFoundException {
        return null;
    }
}
