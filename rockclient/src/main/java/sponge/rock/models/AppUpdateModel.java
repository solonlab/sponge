package sponge.rock.models;

import org.noear.weed.GetHandlerEx;
import org.noear.weed.IBinder;

import java.util.Date;

/// <summary>
/// 生成:2017/08/08 12:03:44
/// 
/// </summary>
public class AppUpdateModel
{
    public AppUpdateModel(AppVersionModel version){
        this.version = version;
        this.type = UpdateType.none;
    }
    public AppVersionModel version;
    public UpdateType type;
}