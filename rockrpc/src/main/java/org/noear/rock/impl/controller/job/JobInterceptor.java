package org.noear.rock.impl.controller.job;

import lombok.extern.slf4j.Slf4j;
import org.noear.solon.annotation.Component;
import org.noear.solon.cloud.CloudClient;
import org.noear.solon.cloud.CloudJobHandler;
import org.noear.solon.cloud.CloudJobInterceptor;
import org.noear.solon.cloud.model.Job;

/**
 * @author noear 2022/4/6 created
 */
@Slf4j
@Component
public class JobInterceptor implements CloudJobInterceptor {
    @Override
    public void doIntercept(Job job, CloudJobHandler handler) throws Throwable {
        long start = System.currentTimeMillis();
        log.info("interceptor job: {} start", job.getName());

        try {
            handler.handle(job.getContext());
        } finally {
            log.info("interceptor job: {} end", job.getName());

            long timespan = System.currentTimeMillis() - start;
            CloudClient.metric().addCount("job", job.getName(), timespan);
        }
    }
}
