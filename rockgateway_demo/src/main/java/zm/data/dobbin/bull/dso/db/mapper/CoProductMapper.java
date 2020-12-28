package zm.data.dobbin.bull.dso.db.mapper;

import java.math.*;
import java.sql.SQLException;
import java.time.*;
import java.util.*;

import org.noear.weed.BaseMapper;
import org.noear.weed.DataItem;
import org.noear.weed.DataList;
import org.noear.weed.annotation.Db;
import org.noear.weed.xml.Namespace;
import zm.data.dobbin.bull.models.*;

@Db("sponge_sugar")
@Namespace("zm.data.dobbin.bull.dso.db.mapper.CoProductMapper")
public interface CoProductMapper{
    //获取产品详情
    CoProductModel get_co_product(long product_id) throws SQLException;

    //获取产品列表
    List<CoProductModel> list_co_product() throws SQLException;
}
