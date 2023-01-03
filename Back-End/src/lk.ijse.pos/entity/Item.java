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

public class Item {
    private String code;
    private String name;
    private int qty;
    private double unitPrice;
}
