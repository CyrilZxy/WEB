package entity;

import lombok.Data;

/**
 * @author ：ZXY
 * @date ：Created in 2020/5/12 22:16
 * @description：    商品
 */

@Data
public class Goods {
    private Integer id;
    private String name;
    private String introduce;
    private Integer stock;
    private String unit;
    private Integer price;      //12.32--》1232
    private Integer discount;



    private Integer buyGoodsNum;


    public double getPrice(){
        return price * 1.0 / 100;
    }

    //返回整数
    public int getPriceInt(){
        return price;
    }

}

