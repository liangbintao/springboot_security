package com.mochu.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mobile")
public class MobilePo {

    /**
     * id
     */
    @Id
    private Integer id;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 验证码
     */
    private String code;

    /**
     * 用户id
     */
    private String userid;

    /**
     * 0 无效 1 有效
     */
    private Integer status;

    /**
     * 验证次数
     */
    private Integer verifytime;

    /**
     * 发送时间
     */
    private String sendtime;

    /**
     * validtime
     */
    private String validtime;

    /**
     * 当日发送次数
     */
    private Integer todaycount;

    /**
     * 说明
     */
    private String sendresult;

    /**
     * ip地址
     */
    private String ip;

    /**
     * 设备号
     */
    private String machine;

    /**
     * logintime
     */
    private String logintime;

    /**
     * createtime
     */
    private String createtime;

    /**
     * 验证码生成时间
     */
    private String updatetime;
}
