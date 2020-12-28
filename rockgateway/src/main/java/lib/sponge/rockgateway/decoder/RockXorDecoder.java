package lib.sponge.rockgateway.decoder;

import lib.sponge.rock.models.AppModel;
import org.noear.solon.core.handle.Context;
import org.noear.water.utils.EncryptUtils;

import java.net.URLDecoder;

public class RockXorDecoder implements RockDecoder {
    @Override
    public String tryDecode(Context context, AppModel app, String text) throws Exception {
        if (text.indexOf("{") < 0 && text.indexOf("<") < 0) {
            if (text.indexOf('%') >= 0) {
                text = new String(URLDecoder.decode(text, "UTF-8"));
            }

            return EncryptUtils.xorDecode(text, app.app_key);
        }

        return text;
    }
}
