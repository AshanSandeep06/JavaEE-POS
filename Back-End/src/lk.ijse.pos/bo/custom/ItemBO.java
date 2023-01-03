package lk.ijse.pos.bo.custom;

import lk.ijse.pos.bo.SuperBO;
import lk.ijse.pos.dto.CustomerDTO;
import lk.ijse.pos.dto.ItemDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Ashan Sandeep
 * @date : 1/2/2023
 * @since : 0.1.0
 **/

public interface ItemBO extends SuperBO {
    ArrayList<ItemDTO> getAllItems(Connection connection) throws SQLException, ClassNotFoundException;

    boolean saveItem(Connection connection, ItemDTO dto) throws SQLException, ClassNotFoundException;

    boolean updateItem(Connection connection, ItemDTO dto) throws SQLException, ClassNotFoundException;

    boolean deleteItem(Connection connection, String code) throws SQLException, ClassNotFoundException;
}
