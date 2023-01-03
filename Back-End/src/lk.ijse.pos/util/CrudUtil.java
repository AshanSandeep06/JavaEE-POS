package lk.ijse.pos.util;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author : Ashan Sandeep
 * @date : 1/2/2023
 * @since : 0.1.0
 **/

public class CrudUtil {
    public static <T> T execute(Connection connection, String sql, Object... params) throws SQLException {
        PreparedStatement stm = connection.prepareStatement(sql);

        for (int i = 0; i < params.length; i++) {
            stm.setObject((i+1), params[i]);
        }

        if(sql.toUpperCase().startsWith("SELECT")){
            return (T)stm.executeQuery();
        }else{
            return (T) (Boolean)(stm.executeUpdate()>0);
        }
    }
}
