package org.noear.sponge.track.track.dso;

import org.noear.water.utils.EventPipeline;
import org.noear.weed.DataItem;
import org.noear.sponge.track.track.dso.db_sponge_track.DbTrackApi;


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
        super((list) -> {
            try {
                DbTrackApi.addUrlLogAll("short_redirect_log_30d", list);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}
