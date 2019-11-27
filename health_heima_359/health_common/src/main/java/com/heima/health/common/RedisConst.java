package com.heima.health.common;

/**
 * @author 王立腾
 * @created 2019/11/22 15:56.
 * @description
 */
public class RedisConst {
//    用户登录名称 wangliteng@1588448321997699.onaliyun.com
//    AccessKey ID LTAI4FiHy1VpMCZj6J5WaztD
//    AccessKeySecret f6WrGqthGIi3a9IODEx4fdcdo8wUzg

    //套餐图片所有图片名称
    public static final String SETMEAL_PIC_RESOURCES = "setmealPicResources";
    //套餐图片保存在数据库中的图片名称
    public static final String SETMEAL_PIC_DB_RESOURCES = "setmealPicDbResources";


    public static final String SENDTYPE_ORDER = "001";//用于缓存体检预约时发送的验证码
    public static final String SENDTYPE_LOGIN = "002";//用于缓存手机号快速登录时发送的验证码
    public static final String SENDTYPE_GETPWD = "003";//用于缓存找回密码时发送的验证码
}
