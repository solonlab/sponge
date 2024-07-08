package test;

import org.junit.jupiter.api.Test;
import org.noear.rock.i18n.I18nUtils;
import org.noear.solon.test.SolonTest;

/**
 * @author noear 2021/2/24 created
 */
@SolonTest
public class RockI18nUtilsTest {
    @Test
    public void test0() throws Exception {

        System.out.println(I18nUtils.getByCode(200, ""));
        System.out.println(I18nUtils.getByName("title", ""));

        Thread.sleep(1000 * 30);


        //System.out.println(RockClient.getServiceCode("demoapi",200));
    }
}
