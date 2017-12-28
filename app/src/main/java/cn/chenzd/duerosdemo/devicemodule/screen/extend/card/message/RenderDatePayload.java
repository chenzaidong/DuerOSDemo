package cn.chenzd.duerosdemo.devicemodule.screen.extend.card.message;


import java.io.Serializable;

import cn.chenzd.duerosdemo.framework.message.Payload;

public class RenderDatePayload extends Payload implements Serializable {
    public String datetime;
    public String timeZoneName;
    public String day;
}
