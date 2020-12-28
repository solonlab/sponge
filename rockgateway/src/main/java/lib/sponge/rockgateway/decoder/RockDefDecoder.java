package lib.sponge.rockgateway.decoder;

import lib.sponge.rock.models.AppModel;
import org.noear.solon.core.handle.Context;

public class RockDefDecoder implements RockDecoder {
    @Override
    public String tryDecode(Context context, AppModel app, String text) throws Exception {
        return text;
    }
}
