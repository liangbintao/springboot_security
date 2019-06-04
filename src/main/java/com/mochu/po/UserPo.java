package com.mochu.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserPo {

    /**
     * id
     */
    @Id
    private Integer id;

    /**
     * username
     */
    private String username;

    /**
     * password
     */
    private String password;

    /**
     * status
     */
    private Integer status;

    /**
     * 花名
     */
    private String nickname;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * sex
     */
    private Integer sex;

    /**
     * age
     */
    private Integer age;

    /**
     * createtime
     */
    private Date createtime;

    /**
     * updatetime
     */
    private Date updatetime;
}
