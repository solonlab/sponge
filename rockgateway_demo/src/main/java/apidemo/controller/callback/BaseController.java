package apidemo.controller.callback;

import org.noear.solon.annotation.Inject;
import apidemo.dso.db.BullOrderService;
import apidemo.dso.db.CoProductService;

public abstract class BaseController {
    @Inject
    CoProductService coProductService;

    @Inject
    BullOrderService bullOrderService;
}
