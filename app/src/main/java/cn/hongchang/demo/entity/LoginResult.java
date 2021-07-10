package cn.hongchang.demo.entity;

/**
 * @author Bob
 */
public class LoginResult {

    /**
     * code : 200
     * message : 登录成功！
     * data : {"user_type":0,"phone":"bob","loginName":null,"userId":13}
     */

    public int code;
    public String message;
    public DataBean data;

    public static class DataBean {
        /**
         * user_type : 0
         * phone : bob
         * loginName : null
         * userId : 13
         */

        public int user_type;
        public String phone;
        public Object loginName;
        public int userId;
    }
}
