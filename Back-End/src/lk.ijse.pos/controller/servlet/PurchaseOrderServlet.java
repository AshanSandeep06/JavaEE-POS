package lk.ijse.pos.controller.servlet;

import lk.ijse.pos.bo.BOFactory;
import lk.ijse.pos.bo.custom.ItemBO;
import lk.ijse.pos.bo.custom.PurchaseOrderBO;
import lk.ijse.pos.dto.ItemDTO;
import lk.ijse.pos.dto.OrderDTO;
import lk.ijse.pos.dto.OrderDetailDTO;
import lk.ijse.pos.util.ResponseUtil;

import javax.annotation.Resource;
import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(urlPatterns = "/purchase")
public class PurchaseOrderServlet extends HttpServlet {
    private final PurchaseOrderBO purchaseOrderBO = (PurchaseOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PURCHASEORDERBO);

    @Resource(name = "java:comp/env/jdbc/pool")
    DataSource dataSource;

    JsonObjectBuilder responseObject = null;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();

        String oid = jsonObject.getString("oid");
        String cusId = jsonObject.getString("cusID");
        String date = jsonObject.getString("date");
        JsonArray od_details = jsonObject.getJsonArray("orderDetails");

        ArrayList<OrderDetailDTO> orderDetails = new ArrayList<>();

        for (JsonValue orderDetail : od_details) {
            OrderDetailDTO od;
            orderDetails.add(od = new OrderDetailDTO(
                    oid,
                    orderDetail.asJsonObject().getString("code"),
                    Double.parseDouble(orderDetail.asJsonObject().getString("price")),
                    Integer.parseInt(orderDetail.asJsonObject().getString("qty"))
            ));
        }

        try (Connection connection = dataSource.getConnection()) {
            if (purchaseOrderBO.placeOrder(connection, new OrderDTO(oid, cusId, date, orderDetails))) {
                responseObject = ResponseUtil.getInstance().getResponseObject("Success", "Order Successfully Purchased..!", "");
            } else {
                responseObject = ResponseUtil.getInstance().getResponseObject("Error", "Order Placement was failed..!", "");
            }

        } catch (SQLException e) {
            responseObject = ResponseUtil.getInstance().getResponseObject("Error", e.getLocalizedMessage(), "");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        } catch (RuntimeException | ClassNotFoundException e) {
            responseObject = ResponseUtil.getInstance().getResponseObject("Error", e.getLocalizedMessage(), "");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        resp.getWriter().print(responseObject.build());
    }
}
