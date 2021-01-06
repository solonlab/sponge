package org.noear.sponge.admin.controller.common;

import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.UploadedFile;
import org.noear.sponge.admin.controller.ViewModel;
import org.noear.sponge.admin.controller.BaseController;
import org.noear.sponge.admin.utils.OssUtil;

/**
 * @Author:Fei.chu
 * @Date:Created in 15:29 2019/04/01
 * @Description:
 */
@Controller
public class FileController extends BaseController {

    @Mapping("file/upload_banklogo")
    public ViewModel upload(Context ctx, UploadedFile file) throws Exception {
        if (file == null) {
            return viewModel.set("code", 0).set("url", "");
        } else {
            return viewModel.set("code", 1).set("url", OssUtil.upload(file, "bank_icon"));
        }
    }
}
