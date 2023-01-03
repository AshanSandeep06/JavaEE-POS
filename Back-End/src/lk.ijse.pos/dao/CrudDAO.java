package lk.ijse.pos.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Ashan Sandeep
 * @date : 1/3/2023
 * @since : 0.1.0
 **/

public interface CrudDAO<T, ID> extends SuperDAO {
    ArrayList<T> getAll(Connection connection) throws SQLException, ClassNotFoundException;

    boolean save(Connection connection, T entity) throws SQLException, ClassNotFoundException;

    boolean update(Connection connection, T entity) throws SQLException, ClassNotFoundException;

    boolean delete(Connection connection, ID id) throws SQLException, ClassNotFoundException;

    T search(Connection connection, ID id) throws SQLException, ClassNotFoundException;
}
