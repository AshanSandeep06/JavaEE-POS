package lk.ijse.pos.dao.custom.impl;

import lk.ijse.pos.dao.custom.ItemDAO;
import lk.ijse.pos.entity.Customer;
import lk.ijse.pos.entity.Item;
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

public class ItemDAOImpl implements ItemDAO {

    @Override
    public ArrayList<Item> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute(connection, "SELECT * FROM Item");
        ArrayList<Item> allItems = new ArrayList<>();
        while (result.next()) {
            allItems.add(new Item(
                    result.getString(1),
                    result.getString(2),
                    result.getInt(3),
                    result.getDouble(4)
            ));
        }
        return allItems;
    }

    @Override
    public boolean save(Connection connection, Item entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(connection, "INSERT INTO Item VALUES(?, ?, ?, ?)", entity.getCode(), entity.getName(), entity.getQty(), entity.getUnitPrice());
    }

    @Override
    public boolean update(Connection connection, Item entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(connection, "UPDATE Item SET name=?, qty=?, unitPrice=? WHERE code=?", entity.getName(), entity.getQty(), entity.getUnitPrice(), entity.getCode());
    }

    @Override
    public boolean delete(Connection connection, String code) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(connection, "DELETE FROM Item WHERE code=?", code);
    }

    @Override
    public Item search(Connection connection, String code) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute(connection, "SELECT * FROM Item WHERE code=?", code);
        if (result.next()) {
            return new Item(result.getString(1), result.getString(2), result.getInt(3), result.getDouble(4));
        }
        return null;
    }
}
