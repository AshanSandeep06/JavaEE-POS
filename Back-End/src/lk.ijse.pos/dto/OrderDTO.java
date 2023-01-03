package lk.ijse.pos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * @author : Ashan Sandeep
 * @date : 1/2/2023
 * @since : 0.1.0
 **/

@NoArgsConstructor
@AllArgsConstructor
@Data

public class OrderDTO {
    private String orderId;
    private String cusId;
    private String date;
    private ArrayList<OrderDetailDTO> orderDetails;
}
