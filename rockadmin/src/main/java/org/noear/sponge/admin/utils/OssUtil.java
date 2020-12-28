package org.noear.sponge.admin.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.http.util.TextUtils;
import org.noear.solon.core.handle.UploadedFile;
import org.noear.sponge.admin.Config;
import org.noear.sponge.admin.dso.IDUtil;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;

import java.io.*;
import java.util.Properties;

public class OssUtil {

    private static OSSClient ossClient;

    private static final String ossH= Config.cfg("ossH").value;
    private static final String ossL= Config.cfg("ossL").value;
    private static  String accessKeyId;
    private static  String secretAccessKey;
    private static  String endpoint;

    public static final String OSS_IMAGE_URL = "https://img.kdz6.cn/";

    private static void tryInit() {
        if (accessKeyId == null) {
            Properties prop = Config.cfg("oss").getProp();

            accessKeyId = prop.getProperty("accessKeyId");
            secretAccessKey = prop.getProperty("secretAccessKey");
            endpoint = prop.getProperty("endpoint");
        }
    }

    public static String uploadStreamH(InputStream in,String uuid,long open_id,String source) throws IOException {
        tryInit();

        ossClient = new OSSClient(endpoint, accessKeyId, secretAccessKey);
        String filename = "";
        if (TextUtils.isEmpty(source)) {
            filename=TimeUtil.getCreateDateName();

            filename=open_id+"/"+uuid+"/"+filename+".txt";
        } else if ("angel".equals(source)) {
            filename="angel/"+open_id+"/"+uuid+".txt";
        }

        try {
            // 创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(in.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Prama", "no-cache");
            objectMetadata.setContentType(".txt");
            objectMetadata.setContentDisposition("inline;filename=" + filename);
            PutObjectResult res= ossClient.putObject(ossH, filename, in, objectMetadata);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            ossClient.shutdown();
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return filename;
    }

    public static String getOssObject(String key,int type) throws IOException {
        tryInit();

        String bucketName =ossH;
        if(type==1){
            bucketName =ossL;
        }

        // 创建OSSClient实例
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, secretAccessKey);
        OSSObject ossObject = ossClient.getObject(bucketName, key);
        // 读Object内容
        BufferedReader reader = new BufferedReader(new InputStreamReader(ossObject.getObjectContent()));
        StringBuilder builder=new StringBuilder();
        while (true) {
            String line = reader.readLine();
            if (line == null) break;
            builder.append(line);
        }
        reader.close();
        // 关闭client
        ossClient.shutdown();

        return builder.toString();
    }

    public static String getOssObjectFormal(String key,int type) throws IOException {
        tryInit();

        String bucketName ="zm-51kb-hdata";
        if(type==1){
            bucketName ="zm-51kb-ldata";
        }

        // 创建OSSClient实例
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, secretAccessKey);
        OSSObject ossObject = ossClient.getObject(bucketName, key);
        // 读Object内容
        System.out.println("Object content:");
        BufferedReader reader = new BufferedReader(new InputStreamReader(ossObject.getObjectContent()));
        StringBuilder builder=new StringBuilder();
        while (true) {
            String line = reader.readLine();
            if (line == null) break;
            builder.append(line);
        }
        reader.close();
        // 关闭client
        ossClient.shutdown();

        return builder.toString();
    }

    public static String upload(UploadedFile file) throws IOException {
        return upload(file, null);
    }

    public static String upload(UploadedFile  file, String prefix) throws IOException {
        tryInit();

        ossClient = new OSSClient(endpoint, accessKeyId, secretAccessKey);
        InputStream in = file.content;

        String filename = (StringUtils.isNotBlank(prefix) ? prefix.replaceAll("/+$", "") + "/" : "")
                + IDUtil.buildGuid() + "." + file.name.replaceAll("^.*\\.", "");//ts + file.getOriginalFilename();

        try {
            // 创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(in.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Prama", "no-cache");
            objectMetadata.setContentType(file.contentType);
            objectMetadata.setContentDisposition("inline;filename=" + filename);

            // 上传
            ossClient.putObject("zm-51kb-img", filename, in, objectMetadata);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            ossClient.shutdown();
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return OSS_IMAGE_URL + filename;
    }

    /**
     * java 字符串 转义特殊字符 to js
     * @return
     */
    public static String stringToJson(String json){
        return json;
    }
    public static String load(String key){
        try {
            if(key != null && !"".equals(key)){
                return stringToJson(OssUtil.getOssObject(key,0));
            }
        }catch (Exception e){
        }
        return null;
    }
}
