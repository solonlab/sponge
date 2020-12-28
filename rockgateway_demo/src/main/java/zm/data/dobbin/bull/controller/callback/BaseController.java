package zm.data.dobbin.bull.controller.callback;

import org.noear.solon.annotation.Inject;
import zm.data.dobbin.bull.dso.db.BullOrderService;
import zm.data.dobbin.bull.dso.db.CoProductService;

public abstract class BaseController {
    @Inject
    CoProductService coProductService;

    @Inject
    BullOrderService bullOrderService;
}
