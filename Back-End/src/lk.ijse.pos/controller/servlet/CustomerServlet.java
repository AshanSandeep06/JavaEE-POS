package lk.ijse.pos.controller.servlet;

import lk.ijse.pos.bo.BOFactory;
import lk.ijse.pos.bo.SuperBO;
import lk.ijse.pos.bo.custom.CustomerBO;
import lk.ijse.pos.dto.CustomerDTO;
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

@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {
    private final CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    @Resource(name = "java:comp/env/jdbc/pool")
    DataSource dataSource;

    JsonObjectBuilder responseObject = null;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = dataSource.getConnection()) {
            ArrayList<CustomerDTO> dtoArrayList = customerBO.getAllCustomers(connection);

            JsonArrayBuilder allCustomers = Json.createArrayBuilder();
            for (CustomerDTO c1 : dtoArrayList) {
                JsonObjectBuilder customer = Json.createObjectBuilder();
                customer.add("id", c1.getId());
                customer.add("name", c1.getName());
                customer.add("address", c1.getAddress());
                customer.add("salary", c1.getSalary());
                allCustomers.add(customer.build());
            }
            responseObject = ResponseUtil.getInstance().getResponseObject("Success", "Successfully Loaded..!", allCustomers);

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
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String address = req.getParameter("address");
        double salary = Double.parseDouble(req.getParameter("salary"));

        try (Connection connection = dataSource.getConnection()) {
            if (customerBO.saveCustomer(connection, new CustomerDTO(id, name, address, salary))) {
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
        String id = req.getParameter("id");

        try (Connection connection = dataSource.getConnection()) {
            if (customerBO.deleteCustomer(connection, id)) {
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

        String id = customer.getString("id");
        String name = customer.getString("name");
        String address = customer.getString("address");
        double salary = Double.parseDouble(customer.getString("salary"));

        try (Connection connection = dataSource.getConnection()) {
            if (customerBO.updateCustomer(connection, new CustomerDTO(id, name, address, salary))) {
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
