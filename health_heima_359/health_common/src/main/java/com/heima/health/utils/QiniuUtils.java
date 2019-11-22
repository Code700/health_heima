package com.heima.health.utils;


import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

/**
 * @author 王立腾
 * @created 2019/11/21 19:26.
 * @description
 */
public class QiniuUtils {
    //项目的Key
    public static String accessKey = "yMl2WvHuW1oXUHG6rBH1-DvoFxfg20o0HFrMduS1";
    //密钥认证KEY
    public static String secretKey = "_-YpUD1D_Pp1GVLpsn57T-Zeh-iB-w1fP69RZ7kv";
    //项目名
    public static String bucket = "health-app";

    /**
     * 直接文件上传
     *
     * @param filePath
     * @param fileNmae
     */
    public static void upload2Qiniu(String filePath, String fileNmae) {
        //构造一个带指定zone对象的配置类，zone是选择的大区地址，这里注册使用的华东
        Configuration cfg = new Configuration(Zone.zone0());
        //创建文件管理器对象
        UploadManager uploadManager = new UploadManager(cfg);
        //七牛认证
        Auth auth = Auth.create(accessKey, secretKey);
        //根据项目名生成一个token
        String upToken = auth.uploadToken(bucket);

        try {
            Response response = uploadManager.put(filePath, fileNmae, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);

        } catch (QiniuException e) {
            e.printStackTrace();
            Response res = e.response;
            try {
                System.err.println(res.bodyString());
            } catch (QiniuException e1) {
                e1.printStackTrace();
            }
        }

    }

    //上传文件
    public static void upload2Qiniu(byte[] bytes, String fileName) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        UploadManager uploadManager = new UploadManager(cfg);

        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = fileName;
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(bytes, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }

    /**
     * 删除文件
     *
     * @param fileName
     */
    public static void deleteFileFromQiniu(String fileName) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        String key = fileName;
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
    }

}
