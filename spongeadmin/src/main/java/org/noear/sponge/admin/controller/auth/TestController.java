//package webapp.controller.auth;
//
//import com.alibaba.excel.ExcelWriter;
//import com.alibaba.excel.metadata.Sheet;
//import com.alibaba.excel.metadata.Table;
//import com.alibaba.excel.support.ExcelTypeEnum;
//import org.noear.solon.annotation.Controller;
//import org.noear.solon.annotation.Mapping;
//import webapp.dao.Session;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.OutputStream;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//@Controller
//public class TestController {
//    @Mapping("auth/doExport")
//    public void export() {
//        try {
//            OutputStream out = null;
//            HttpServletResponse resp = (HttpServletResponse) Session.getContext().response();
//            out = resp.getOutputStream();
//            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
//
//            // 设置EXCEL名称
//            String fileName = new String(("testExcel").getBytes(), "UTF-8");
//
//            // 设置SHEET名称
//            String sheetName = "sheetTitle";
//
//            // 设置标题
//            Table table = new Table(1);
//            List<List<String>> titles = new ArrayList<List<String>>();
//            titles.add(Arrays.asList("头部标题1"));
//            titles.add(Arrays.asList("头部标题1"));
//            titles.add(Arrays.asList("头部标题1"));
//            table.setHead(titles);
//
//            Sheet sheet = new Sheet(0, 0);
//            sheet.setSheetName(sheetName);
//            List<List<String>> dataList = new ArrayList<>();
//            dataList.add(Arrays.asList("内容1","内容2","内容3"));
//            writer.write0(dataList, sheet, table);
//
//            String headStr = "attachment; filename=\"" + fileName + "\"";
//            resp.setCharacterEncoding("utf-8");
//            resp.setContentType("multipart/form-data");
//            resp.setHeader("Content-Disposition", headStr);
//            writer.finish();
//            out.flush();
//
//            //关闭资源
//            out.close();
//        } catch (Exception ex){
//            ex.printStackTrace();
//        }
//
//    }
//
//}
