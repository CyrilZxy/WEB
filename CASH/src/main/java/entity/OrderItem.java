package entity;

import lombok.Data;

/**
 * @author ：ZXY
 * @date ：Created in 2020/5/12 22:16
 * @description：
 */

@Data
public class OrderItem {
    private Integer id;
    private String order_id;
    private Integer goods_id;
    private String goods_name;
    private String goods_introduce;
    private Integer goods_num;
    private String goods_unit;
    private Integer goods_price;
    private Integer goods_discount;

    public double getGoodsPrice() {
        return goods_price * 1.0 / 100;
    }

    public int getGoodsPriceInt() {
        return goods_price;
    }

}
