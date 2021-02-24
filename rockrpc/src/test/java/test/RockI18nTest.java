package test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.noear.rock.RockClient;
import org.noear.rock.model.AppCodeCollection;
import org.noear.solon.test.SolonJUnit4ClassRunner;

import java.sql.SQLException;

/**
 * @author noear 2021/2/24 created
 */
@RunWith(SolonJUnit4ClassRunner.class)
public class RockI18nTest {
    @Test
    public void test0() throws SQLException {
        System.out.println(RockClient.getServiceCode("demoapi",200));
    }

    @Test
    public void test2() throws SQLException {
        AppCodeCollection coll = RockClient.getServiceCodes("demoapi");

        System.out.println(coll.get(200));

        System.out.println(coll);
    }

    @Test
    public void test10() throws SQLException {
        System.out.println(RockClient.getServiceI18n("demoapi","title"));
    }

    @Test
    public void test12() throws SQLException {
        System.out.println(RockClient.getServiceI18ns("demoapi"));
    }
}
