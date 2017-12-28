package cn.chenzd.duerosdemo.devicemodule.screen.extend.card.message;


import java.io.Serializable;

import cn.chenzd.duerosdemo.framework.message.Payload;

public class RenderStockPayload extends Payload implements Serializable {
    public double changeInPrice;
    public double changeInPercentage;
    public double marketPrice;
    public String marketStatus;
    public String marketName;
    public String name;
    public String datetime;
    public double openPrice;
    public double previousClosePrice;
    public double dayHighPrice;
    public double dayLowPrice;
    public double priceEarningRatio;
    public long marketCap;
    public long dayVolume;
}
