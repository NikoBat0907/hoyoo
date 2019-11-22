package cn.wuyiz.travel.bean;

import java.io.Serializable;

public class User implements Serializable {
    /**
     * `uid` int(11) NOT NULL AUTO_INCREMENT,
     * `username` varchar(100) NOT NULL,
     * `password` varchar(32) NOT NULL,
     * `name` varchar(100) DEFAULT NULL,
     * `birthday` date DEFAULT NULL,
     * `sex` char(1) DEFAULT NULL,
     * `telephone` varchar(11) DEFAULT NULL,
     * `email` varchar(100) DEFAULT NULL,
     * `status` char(1) DEFAULT NULL,
     * `code` varchar(50) DEFAULT NULL,
     */
    private int uid;    //用户id
    private String username;   //用户名
    private String password;    //密码
    private String name;    //姓名（Unique）
    private String birthday;    //出生日期
    private String sex; //性别
    private String telephone;   //手机号码
    private String email;   //邮箱
    private String status;  //激活状态      激活：Y     未激活：N
    private String code;    //激活码（Unique）

    public User() {
    }

    public User(int uid, String username, String password, String name, String birthday, String sex, String telephone, String email, String status, String code) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.name = name;
        this.birthday = birthday;
        this.sex = sex;
        this.telephone = telephone;
        this.email = email;
        this.status = status;
        this.code = code;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
