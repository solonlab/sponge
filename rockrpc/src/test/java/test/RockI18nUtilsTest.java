package test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.noear.rock.utils.I18nUtils;
import org.noear.solon.test.SolonJUnit4ClassRunner;

import java.sql.SQLException;

/**
 * @author noear 2021/2/24 created
 */
@RunWith(SolonJUnit4ClassRunner.class)
public class RockI18nUtilsTest {
    @Test
    public void test0() throws Exception {

        System.out.println(I18nUtils.getByCode(200, ""));
        System.out.println(I18nUtils.getByName("title", ""));

        Thread.sleep(1000 * 30);


        //System.out.println(RockClient.getServiceCode("demoapi",200));
    }
}
