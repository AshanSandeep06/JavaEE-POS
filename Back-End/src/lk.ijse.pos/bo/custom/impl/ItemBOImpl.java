package lk.ijse.pos.bo.custom.impl;

import lk.ijse.pos.bo.custom.CustomerBO;
import lk.ijse.pos.bo.custom.ItemBO;
import lk.ijse.pos.dao.DAOFactory;
import lk.ijse.pos.dao.custom.CustomerDAO;
import lk.ijse.pos.dao.custom.ItemDAO;
import lk.ijse.pos.dto.CustomerDTO;
import lk.ijse.pos.dto.ItemDTO;
import lk.ijse.pos.entity.Customer;
import lk.ijse.pos.entity.Item;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Ashan Sandeep
 * @date : 1/2/2023
 * @since : 0.1.0
 **/

public class ItemBOImpl implements ItemBO {
    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);

    @Override
    public ArrayList<ItemDTO> getAllItems(Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<Item> list = itemDAO.getAll(connection);
        ArrayList<ItemDTO> allItems = new ArrayList<>();
        for (Item entity : list) {
            allItems.add(new ItemDTO(
                    entity.getCode(),
                    entity.getName(),
                    entity.getQty(),
                    entity.getUnitPrice()
            ));
        }
        return allItems;
    }

    @Override
    public boolean saveItem(Connection connection, ItemDTO dto) throws SQLException, ClassNotFoundException {
        return itemDAO.save(connection, new Item(dto.getCode(), dto.getName(), dto.getQty(), dto.getUnitPrice()));
    }

    @Override
    public boolean updateItem(Connection connection, ItemDTO dto) throws SQLException, ClassNotFoundException {
        return itemDAO.update(connection, new Item(dto.getCode(), dto.getName(), dto.getQty(), dto.getUnitPrice()));
    }

    @Override
    public boolean deleteItem(Connection connection, String code) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(connection, code);
    }
}
