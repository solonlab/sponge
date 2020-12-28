package lib.sponge.rockgateway.encoder;

import lib.sponge.rock.models.AppModel;
import org.noear.solon.core.handle.Context;
import org.noear.water.utils.EncryptUtils;

public class RockAesEncoder implements  RockEncoder {
    @Override
    public String tryEncode(Context context, AppModel app, String text) throws Exception {
        return EncryptUtils.aesEncrypt(text, app.app_key, null);
    }
}
