package lk.ijse.pos.dao.custom.impl;

import lk.ijse.pos.dao.custom.ItemDAO;
import lk.ijse.pos.dao.custom.OrderDAO;
import lk.ijse.pos.entity.Order;
import lk.ijse.pos.util.CrudUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Ashan Sandeep
 * @date : 1/3/2023
 * @since : 0.1.0
 **/

public class OrderDAOImpl implements OrderDAO {

    @Override
    public ArrayList<Order> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(Connection connection, Order entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(connection, "INSERT INTO `Order` VALUES(?, ?, ?)", entity.getOrderId(), entity.getCusId(), entity.getDate());
    }

    @Override
    public boolean update(Connection connection, Order entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Connection connection, String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Order search(Connection connection, String s) throws SQLException, ClassNotFoundException {
        return null;
    }
}
