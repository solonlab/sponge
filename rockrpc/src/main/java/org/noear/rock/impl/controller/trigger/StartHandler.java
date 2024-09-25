package org.noear.rock.impl.controller.trigger;

import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Filter;
import org.noear.solon.core.handle.FilterChain;

public class StartHandler implements Filter {

    @Override
    public void doFilter(Context ctx, FilterChain chain) throws Throwable {
        ctx.attrSet("_start", System.currentTimeMillis());

        chain.doFilter(ctx);
    }
}
