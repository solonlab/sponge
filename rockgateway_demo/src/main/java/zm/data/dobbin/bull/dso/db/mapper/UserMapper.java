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

@Db("dobbin")
@Namespace("zm.data.dobbin.bull.dso.db.mapper.UserMapper")
public interface UserMapper{
    //根据user_id获取用户
    UserModel get_user_by_user_id(long user_id) throws SQLException;

    //根据lkey获取用户
    UserModel get_user_by_lkey(String lkey) throws SQLException;

    //根据用户ID获取用户认证信息
    UserValidateModel get_user_validate(long user_id) throws SQLException;
}
