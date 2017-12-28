package cn.chenzd.duerosdemo.devicemodule.screen.extend.card.message;


import java.io.Serializable;

import cn.chenzd.duerosdemo.framework.message.Payload;

public class RenderAirQualityPayload extends Payload implements Serializable {
    public String city;
    public String currentTemperature;
    public String pm25;
    public String airQuality;
    public String day;
    public String date;
    public String dateDescription;
    public String tips;
}
