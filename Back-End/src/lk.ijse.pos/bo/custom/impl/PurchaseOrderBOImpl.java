package lk.ijse.pos.bo.custom.impl;

import lk.ijse.pos.bo.custom.CustomerBO;
import lk.ijse.pos.bo.custom.PurchaseOrderBO;
import lk.ijse.pos.dao.DAOFactory;
import lk.ijse.pos.dao.SuperDAO;
import lk.ijse.pos.dao.custom.CustomerDAO;
import lk.ijse.pos.dao.custom.ItemDAO;
import lk.ijse.pos.dao.custom.OrderDAO;
import lk.ijse.pos.dao.custom.OrderDetailDAO;
import lk.ijse.pos.dto.OrderDTO;
import lk.ijse.pos.dto.OrderDetailDTO;
import lk.ijse.pos.entity.Item;
import lk.ijse.pos.entity.Order;
import lk.ijse.pos.entity.Order_Detail;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author : Ashan Sandeep
 * @date : 1/2/2023
 * @since : 0.1.0
 **/

public class PurchaseOrderBOImpl implements PurchaseOrderBO {
    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    private final OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    private final OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAIL);

    @Override
    public boolean placeOrder(Connection connection, OrderDTO dto) throws SQLException, ClassNotFoundException {
        connection.setAutoCommit(false);

        Order order = new Order(dto.getOrderId(), dto.getCusId(), dto.getDate());

        boolean orderIsSaved = orderDAO.save(connection, order);
        if (!orderIsSaved) {
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }

        for (OrderDetailDTO detail : dto.getOrderDetails()) {
            boolean orderDetailIsSaved = orderDetailDAO.save(connection, new Order_Detail(detail.getOrderId(), detail.getCode(), detail.getPrice(), detail.getQty()));
            if (!orderDetailIsSaved) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            Item item = itemDAO.search(connection, detail.getCode());
            item.setQty(item.getQty() - detail.getQty());

            boolean updateItem = itemDAO.update(connection, item);
            if (!updateItem) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
        }

        connection.commit();
        connection.setAutoCommit(true);
        return true;
    }
}
