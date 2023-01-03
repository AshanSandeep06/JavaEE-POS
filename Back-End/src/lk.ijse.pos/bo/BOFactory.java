package lk.ijse.pos.bo;

import lk.ijse.pos.bo.custom.impl.CustomerBOImpl;
import lk.ijse.pos.bo.custom.impl.ItemBOImpl;
import lk.ijse.pos.bo.custom.impl.PurchaseOrderBOImpl;

/**
 * @author : Ashan Sandeep
 * @date : 1/2/2023
 * @since : 0.1.0
 **/

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory(){

    }

    public static BOFactory getBoFactory(){
        return boFactory==null ? boFactory=new BOFactory() : boFactory;
    }

    public enum BOTypes{
        CUSTOMER, ITEM, PURCHASEORDERBO
    }

    public SuperBO getBO(BOTypes types){
        switch (types){
            case CUSTOMER:
                return new CustomerBOImpl();
            case ITEM:
                return new ItemBOImpl();
            case PURCHASEORDERBO:
                return new PurchaseOrderBOImpl();
            default:
                return null;
        }
    }


}
