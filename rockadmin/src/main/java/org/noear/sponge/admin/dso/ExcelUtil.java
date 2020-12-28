package org.noear.sponge.admin.dso;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.apache.http.util.TextUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.UploadedFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:Fei.chu
 * @Date:Created in 14:14 2019/03/29
 * @Description:excel处理工具类
 */
@Data
public class ExcelUtil {
    //多个sheet
    private String fileName;// sheet的名称
    private String[] handers;// sheet里的标题
    private List<Object[]> dataset;// sheet里的数据集
    public ExcelUtil(String fileName, String[] handers, List<Object[]> dataset) {
        this.fileName = fileName;
        this.handers = handers;
        this.dataset = dataset;
    }


    //单个sheet
    //显示的导出表的标题
    private String title;
    //导出表的列名
    private String[] rowName;
    //生成的文件名
    private String excelName;

    private List<Object[]>  dataList = new ArrayList<Object[]>();

    //构造方法，传入要导出的数据 excelName:文件名  title:表格标题  rowName:列名 dataList:数据行
    public ExcelUtil(String excelName ,String title,String[] rowName,List<Object[]>  dataList){
        this.dataList = dataList;
        this.rowName = rowName;
        this.title = title;
        this.excelName = excelName;
    }


    //导出多sheet表格
    public static void exportExcel(String file, List<ExcelUtil> mysheets){

        HSSFWorkbook wb = new HSSFWorkbook();//创建工作薄
        List<ExcelUtil> sheets = mysheets;

        HSSFCellStyle style = getStyle(wb);
        HSSFCellStyle columnTopStyle = getColumnTopStyle(wb);

        for(ExcelUtil excel: sheets){
            //新建一个sheet
            HSSFSheet sheet = wb.createSheet(excel.getFileName());//获取该sheet名称

            String[] handers = excel.getHanders();//获取sheet的标题名
            HSSFRow rowFirst = sheet.createRow(0);//第一个sheet的第一行为标题
            //写标题
            for(int i=0;i<handers.length;i++){
                //获取第一行的每个单元格
                HSSFCell cell = rowFirst.createCell(i);
                //往单元格里写数据
                cell.setCellValue(handers[i]);
                cell.setCellStyle(style); //加样式
                sheet.setColumnWidth(i, 4000); //设置每列的列宽
            }

            //写数据集
            List<Object[]> dataset = excel.getDataset();
            for(int i=0;i<dataset.size();i++){
                Object[] data = dataset.get(i);//获取该对象
                //创建数据行
                HSSFRow row = sheet.createRow(i+1);
                for(int j=0;j<data.length;j++){
                    //设置对应单元格的值
                    row.createCell(j).setCellValue(data[j]!=null?data[j].toString():"");
                    row.setRowStyle(columnTopStyle);
                }
            }

            int columnNum = handers.length;
            //让列宽随着导出的列长自动适应
            for (int colNum = 0; colNum < columnNum; colNum++) {
                int columnWidth = sheet.getColumnWidth(colNum) / 256;
                for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                    HSSFRow currentRow;
                    //当前行未被使用过
                    if (sheet.getRow(rowNum) == null) {
                        currentRow = sheet.createRow(rowNum);
                    } else {
                        currentRow = sheet.getRow(rowNum);
                    }
                    if (currentRow.getCell(colNum) != null) {
                        HSSFCell currentCell = currentRow.getCell(colNum);
                        if (currentCell.getCellTypeEnum() == CellType.STRING) {
                            int length = currentCell.getStringCellValue().getBytes().length;
                            if (columnWidth < length) {
                                columnWidth = length;
                            }
                        }
                    }
                }
                if(colNum == 0){
                    sheet.setColumnWidth(colNum, (columnWidth-2) * 256);
                }else{
                    sheet.setColumnWidth(colNum, (columnWidth+4) * 256);
                }
            }
        }

        if(wb!=null){
            try
            {
                String fileName = file + ".xls";
                String headStr = "attachment; filename=\"" + fileName + "\"";

                Context resp = Context.current();
                resp.charset("utf-8");
                resp.contentType("multipart/form-data");
                resp.headerSet("Content-Disposition", headStr);
                OutputStream out = resp.outputStream();
                wb.write(out);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }


    //导出单sheet表格
    public void exportExcel() throws Exception{
        try{
            HSSFWorkbook workbook = new HSSFWorkbook();                        // 创建工作簿对象
            HSSFSheet sheet = workbook.createSheet(title);                     // 创建工作表

            // 产生表格标题行
            HSSFRow rowm = sheet.createRow(0);
            HSSFCell cellTiltle = rowm.createCell(0);

            //sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方法 - 在下面  - 可扩展】
            HSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook);//获取列头样式对象
            HSSFCellStyle style = this.getStyle(workbook);                    //单元格样式对象


            // 定义所需列数
            int columnNum = rowName.length;
            // 在索引0的位置创建行(最顶端的行开始的第一行)
            HSSFRow rowRowName = sheet.createRow(0);

            // 将列头设置到sheet的单元格中
            for(int n=0;n<columnNum;n++){
                //创建列头对应个数的单元格
                HSSFCell  cellRowName = rowRowName.createCell(n);
                //设置列头单元格的数据类型
                cellRowName.setCellType(CellType.STRING);
                HSSFRichTextString text = new HSSFRichTextString(rowName[n]);
                //设置列头单元格的值
                cellRowName.setCellValue(text);
                //设置列头单元格样式
                cellRowName.setCellStyle(columnTopStyle);
            }

            //将查询出的数据设置到sheet对应的单元格中
            for(int i=0;i<dataList.size();i++){
                //遍历每个对象
                Object[] obj = dataList.get(i);
                //创建所需的行数
                HSSFRow row = sheet.createRow(i+1);

                for(int j=0; j<obj.length; j++){
                    //设置单元格的数据类型
                    HSSFCell  cell = null;
                    cell = row.createCell(j,CellType.STRING);
                    if(!"".equals(obj[j]) && obj[j] != null){
                        //设置单元格的值
                        cell.setCellValue(obj[j].toString());
                    }
                    //设置单元格样式
                    cell.setCellStyle(style);
                }
            }
            //让列宽随着导出的列长自动适应
            for (int colNum = 0; colNum < columnNum; colNum++) {
                int columnWidth = sheet.getColumnWidth(colNum) / 256;
                for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                    HSSFRow currentRow;
                    //当前行未被使用过
                    if (sheet.getRow(rowNum) == null) {
                        currentRow = sheet.createRow(rowNum);
                    } else {
                        currentRow = sheet.getRow(rowNum);
                    }
                    if (currentRow.getCell(colNum) != null) {
                        HSSFCell currentCell = currentRow.getCell(colNum);
                        if (currentCell.getCellTypeEnum() == CellType.STRING) {
                            int length = currentCell.getStringCellValue().getBytes().length;
                            if (columnWidth < length) {
                                columnWidth = length;
                            }
                        }
                    }
                }
                if(colNum == 0){
                    sheet.setColumnWidth(colNum, (columnWidth-2) * 256);
                }else{
                    sheet.setColumnWidth(colNum, (columnWidth+4) * 256);
                }
            }

            if(workbook !=null){
                try
                {
                    String fileName = excelName + ".xls";
                    String headStr = "attachment; filename=\"" + fileName + "\"";

                    Context resp = Context.current();
                    resp.charset("utf-8");
                    resp.contentType("multipart/form-data");
                    resp.headerSet("Content-Disposition", headStr);
                    OutputStream out = resp.outputStream();
                    workbook.write(out);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    /*
     * 列头单元格样式
     */
    public static HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook) {

        // 设置字体
        HSSFFont font = workbook.createFont();
        //设置字体大小
        font.setFontHeightInPoints((short)11);
        //字体加粗
        font.setBold(true);
        //设置字体名字
        font.setFontName("Courier New");
        //设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
//        //设置底边框;
//        style.setBorderBottom(BorderStyle.THIN);
//        //设置底边框颜色;
//        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
//        //设置左边框;
//        style.setBorderLeft(BorderStyle.THIN);
//        //设置左边框颜色;
//        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
//        //设置右边框;
//        style.setBorderRight(BorderStyle.THIN);
//        //设置右边框颜色;
//        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
//        //设置顶边框;
//        style.setBorderTop(BorderStyle.THIN);
//        //设置顶边框颜色;
//        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HorizontalAlignment.CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        return style;

    }

    /*
     * 列数据信息单元格样式
     */
    public static HSSFCellStyle getStyle(HSSFWorkbook workbook) {
        // 设置字体
        HSSFFont font = workbook.createFont();
        //设置字体大小
        //font.setFontHeightInPoints((short)10);
        //字体加粗
        //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //设置字体名字
        font.setFontName("Courier New");
        //设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
//        //设置底边框;
//        style.setBorderBottom(BorderStyle.THIN);
//        //设置底边框颜色;
//        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
//        //设置左边框;
//        style.setBorderLeft(BorderStyle.THIN);
//        //设置左边框颜色;
//        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
//        //设置右边框;
//        style.setBorderRight(BorderStyle.THIN);
//        //设置右边框颜色;
//        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
//        //设置顶边框;
//        style.setBorderTop(BorderStyle.THIN);
//        //设置顶边框颜色;
//        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HorizontalAlignment.CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        return style;

    }

    //读取excel
    public static JSONArray readExcel(UploadedFile file) throws Exception{
        JSONArray out = new JSONArray();

        // 工作表
        Workbook workbook = WorkbookFactory.create(file.content);

        // 表个数。
        int numberOfSheets = workbook.getNumberOfSheets();

        // 遍历表。
        for (int i = 0; i < numberOfSheets; i++) {
            Sheet sheet = workbook.getSheetAt(i);

            // 行数。
            int rowNumbers = sheet.getLastRowNum() + 1;

            // Excel第一行。
            Row temp = sheet.getRow(0);
            if (temp == null) {
                continue;
            }

            int cells = temp.getPhysicalNumberOfCells();

            List<String> head = new ArrayList<>();
            // 读数据。
            for (int row = 0; row < rowNumbers; row++) {
                boolean ok = false;
                Row r = sheet.getRow(row);
                JSONObject obj = new JSONObject();
                for (int col = 0; col < cells; col++) {
                    if (row==0){
                        head.add(r.getCell(col)+"");
                    } else {
                        try {
                            Cell cell = r.getCell(col);
                            if (cell!=null){
                                r.getCell(col).setCellType(CellType.STRING);
                            }
                            String var = r.getCell(col)==null?"":r.getCell(col).getStringCellValue()+"";

                            obj.put(head.get(col),var);
                            if (!TextUtils.isEmpty(var)){
                                ok = true;
                            }
                        } catch (Exception ex){

                        }

                    }
                }
                if (row>0){
                    if (ok)
                        out.add(obj);
                }
            }
        }
        return out;
    }

}
