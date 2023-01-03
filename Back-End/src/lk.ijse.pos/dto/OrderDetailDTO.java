package lk.ijse.pos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : Ashan Sandeep
 * @date : 1/2/2023
 * @since : 0.1.0
 **/

@NoArgsConstructor
@AllArgsConstructor
@Data

public class OrderDetailDTO {
    private String orderId;
    private String code;
    private double price;
    private int qty;
}
