package test;

import org.noear.nami.Nami;
import org.noear.solon.cloud.impl.CloudLoadBalanceFactory;
import org.noear.rock.RockClient;
import org.noear.rock.model.AppModel;
import org.noear.rock.model.AppUpdateModel;
import org.noear.rock.protocol.RockRpc;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.noear.snack.ONode;
import org.noear.solon.test.SolonJUnit4ClassRunner;
import org.noear.water.WW;
import org.noear.water.WaterClient;

@RunWith(SolonJUnit4ClassRunner.class)
public class RockRpcTest {

    @Test
    public void test0() throws Exception {
        RockRpc rpc = Nami.builder().filterAdd(inv -> {
            inv.headers.put(WW.http_header_trace, WaterClient.waterTraceId());
            inv.headers.put(WW.http_header_from, WaterClient.localServiceHost());

            return inv.invoke();
        }).create(RockRpc.class);
//        RockRpc rpc = Fairy.builder().server("http://12.12.1.1").create(RockRpc.class);

        rpc.getAppsByGroup(1, 1);

        AppModel app = rpc.getAppByID(48);

        assert (app != null && app.app_id > 0);
        System.out.println("RockClient.getApp(48)::成功!!!");


        int cont = CloudLoadBalanceFactory.instance.get("rockrpc").getDiscovery().clusterSize();
        assert cont > 0;

        System.out.println("upstream>>" + cont);
    }

    @Test
    public void test1() throws Exception {
        AppModel app = RockClient.getAppByID(1);

        assert app != null;
        assert app.app_id == 1;


        System.out.println(ONode.stringify(app));

        Object obj = app.getClientSetting(1);

        assert obj != null;

        System.out.println(ONode.stringify(obj));
    }

    @Test
    public void test2() throws Exception {
        AppUpdateModel obj = RockClient.chkAppUpdate(1, 1, 1);

        System.out.println(obj);
    }

    @Test
    public void test3() throws Exception {
        String obj = RockClient.getAppCode(31, 1, "");

        System.out.println(obj);
    }

//    @Test
//    public void test4() throws Exception {
//        while (true) {
//            String obj = RockClient.getAppGroupSettingItem(999999, "demo.name").value;
//
//            System.out.println(obj);
//
//            Thread.sleep(1000);
//        }
//    }
}