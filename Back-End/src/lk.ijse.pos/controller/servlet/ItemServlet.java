package lk.ijse.pos.controller.servlet;

import lk.ijse.pos.bo.BOFactory;
import lk.ijse.pos.bo.custom.CustomerBO;
import lk.ijse.pos.bo.custom.ItemBO;
import lk.ijse.pos.dto.CustomerDTO;
import lk.ijse.pos.dto.ItemDTO;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(urlPatterns = "/item")
public class ItemServlet extends HttpServlet {
    private final ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);

    @Resource(name = "java:comp/env/jdbc/pool")
    DataSource dataSource;

    JsonObjectBuilder responseObject = null;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = dataSource.getConnection()) {
            ArrayList<ItemDTO> dtoArrayList = itemBO.getAllItems(connection);

            JsonArrayBuilder allItems = Json.createArrayBuilder();
            for (ItemDTO i1 : dtoArrayList) {
                JsonObjectBuilder item = Json.createObjectBuilder();
                item.add("code", i1.getCode());
                item.add("description", i1.getName());
                item.add("qtyOnHand", i1.getQty());
                item.add("unitPrice", i1.getUnitPrice());
                allItems.add(item.build());
            }
            responseObject = ResponseUtil.getInstance().getResponseObject("Success", "Successfully Loaded..!", allItems);

        } catch (SQLException e) {
            responseObject = ResponseUtil.getInstance().getResponseObject("Error", e.getLocalizedMessage(), "");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        } catch (ClassNotFoundException e) {
            responseObject = ResponseUtil.getInstance().getResponseObject("Error", e.getLocalizedMessage(), "");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        resp.getWriter().print(responseObject.build());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");
        String description = req.getParameter("description");
        int qtyOnHand = Integer.parseInt(req.getParameter("qtyOnHand"));
        double unitPrice = Double.parseDouble(req.getParameter("unitPrice"));

        try (Connection connection = dataSource.getConnection()) {
            if (itemBO.saveItem(connection, new ItemDTO(code, description, qtyOnHand, unitPrice))) {
                responseObject = ResponseUtil.getInstance().getResponseObject("Success", "Successfully Added..!", "");
            }

        } catch (SQLException e) {
            responseObject = ResponseUtil.getInstance().getResponseObject("Error", e.getLocalizedMessage(), "");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        } catch (ClassNotFoundException e) {
            responseObject = ResponseUtil.getInstance().getResponseObject("Error", e.getLocalizedMessage(), "");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        resp.getWriter().print(responseObject.build());
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");

        try (Connection connection = dataSource.getConnection()) {
            if (itemBO.deleteItem(connection, code)) {
                responseObject = ResponseUtil.getInstance().getResponseObject("Success", "Successfully Deleted..!", "");
            } else {
                throw new RuntimeException("There is no such customer for that ID..!");
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

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject customer = reader.readObject();

        String code = customer.getString("code");
        String description = customer.getString("description");
        int qtyOnHand = Integer.parseInt(customer.getString("qtyOnHand"));
        double unitPrice = Double.parseDouble(customer.getString("unitPrice"));

        try (Connection connection = dataSource.getConnection()) {
            if (itemBO.updateItem(connection, new ItemDTO(code, description, qtyOnHand, unitPrice))) {
                responseObject = ResponseUtil.getInstance().getResponseObject("Success", "Successfully Updated..!", "");
            } else {
                throw new RuntimeException("Wrong ID, Please Check The ID..!");
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
