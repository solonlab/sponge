package org.noear.trackapi.dso;

import org.noear.solon.core.event.EventBus;
import org.noear.water.utils.EventPipeline;
import org.noear.wood.DataItem;
import org.noear.trackapi.dso.db_sponge_track.DbTrackApi;

import java.util.List;


/**
 * 写入时，先写到队列
 * 提交时，每次提交100条；消费完后暂停0.5秒
 *
 * */
public class TrackPipeline extends EventPipeline<DataItem> {
    private static TrackPipeline singleton = new TrackPipeline();

    public static TrackPipeline singleton() {
        return singleton;
    }

    private TrackPipeline() {
        super();
    }

    @Override
    protected void handle(List<DataItem> list) {
        try {
            DbTrackApi.addUrlLogAll("short_redirect_log_30d", list);
        } catch (Exception ex) {
            EventBus.publishAsync(ex);
        }
    }
}
