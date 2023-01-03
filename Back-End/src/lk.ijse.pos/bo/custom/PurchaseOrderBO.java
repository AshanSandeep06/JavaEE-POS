package lk.ijse.pos.bo.custom;

import lk.ijse.pos.bo.SuperBO;
import lk.ijse.pos.dto.OrderDTO;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author : Ashan Sandeep
 * @date : 1/2/2023
 * @since : 0.1.0
 **/

public interface PurchaseOrderBO extends SuperBO {
    boolean placeOrder(Connection connection, OrderDTO dto) throws SQLException, ClassNotFoundException;
}
