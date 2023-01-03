package lk.ijse.pos.entity;

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

public class Order_Detail {
    private String orderId;
    private String code;
    private double price;
    private int qty;
}
