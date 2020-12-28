package zm.data.dobbin.bull.dso.db;

import org.noear.solon.annotation.Inject;
import org.noear.solon.extend.aspect.annotation.Service;
import zm.data.dobbin.bull.dso.db.mapper.UserMapper;
import zm.data.dobbin.bull.models.UserModel;
import zm.data.dobbin.bull.models.UserValidateModel;

import java.sql.SQLException;

@Service
public class UserService {
    @Inject
    UserMapper mapper;

    public UserModel getUser(String lkey) throws Exception {
        return mapper.get_user_by_lkey(lkey);
    }

    public UserModel get_user_by_user_id(long user_id) throws SQLException {
        return mapper.get_user_by_user_id(user_id);
    }

    //根据lkey获取用户
    public UserValidateModel get_user_validate(long user_id) throws SQLException {
        return mapper.get_user_validate(user_id);
    }
}
