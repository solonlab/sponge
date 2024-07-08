package test;

import org.junit.jupiter.api.Test;
import org.noear.rock.RockClient;
import org.noear.rock.model.AppModel;
import org.noear.snack.ONode;
import org.noear.solon.test.SolonTest;

/**
 * @author noear 2021/5/20 created
 */
@SolonTest
public class ApiTest {
    @Test
    public void test() throws Exception{
        assert  RockClient.getAppByID(4).app_id == 4;
        assert  RockClient.getAppGroup(4).agroup_id == 4;
        assert  RockClient.getAppSettingItemEx(4,"_img_pre_path").value != null;
        assert  RockClient.getAppSettingItem(4,"_img_pre_path").value == null;
        assert  RockClient.getAppGroupSettingItem(4,"_img_pre_path").value != null;

        assert  RockClient.getAppByKey("73f0759694a9441980562788a9e4256b").app_id == 4;
        assert  RockClient.getAppByKey("73f0759694a9441980562788a9e4256b").getSetting("_img_pre_path").value != null;
    }

    @Test
    public void test2() throws Exception {
        AppModel app = RockClient.getAppByID(1);

        System.out.println(ONode.stringify(app));

        assert app.app_id == 1;
    }
}
