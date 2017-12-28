package cn.chenzd.duerosdemo.devicemodule.screen.message;

import cn.chenzd.duerosdemo.framework.message.Payload;

import java.io.Serializable;
import java.util.List;

public class RenderHintPayload extends Payload implements Serializable {
    public List<String> cueWords;
}
