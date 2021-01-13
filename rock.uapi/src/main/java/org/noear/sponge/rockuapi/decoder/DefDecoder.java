package org.noear.sponge.rockuapi.decoder;

import org.noear.sponge.rock.models.AppModel;
import org.noear.solon.core.handle.Context;

public class DefDecoder implements Decoder {
    @Override
    public String tryDecode(Context context, AppModel app, String text) throws Exception {
        return text;
    }
}
