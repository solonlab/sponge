package org.noear.rock.impl.controller.job;

import lombok.extern.slf4j.Slf4j;
import org.noear.solon.annotation.Component;
import org.noear.solon.cloud.CloudJobHandler;
import org.noear.solon.cloud.annotation.CloudJob;
import org.noear.solon.core.handle.Context;

/**
 * @author noear 2021/5/26 created
 */
@Slf4j
@Component
public class JOB_hello_test2 {
    @CloudJob(value = "hello_test2", cron7x = "0 * * * * ? *", description = "异常测试")
    public void handle(Context ctx) throws Throwable {
        throw new IllegalArgumentException("异常测试");
    }
}
