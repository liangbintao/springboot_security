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
@Table(name = "authorities")
public class AuthorityPo {

    /**
     * id
     */
    @Id
    private Integer id;

    /**
     * userid
     */
    private String userid;

    /**
     * username
     */
    private String username;

    /**
     * authority
     */
    private String authority;

    /**
     * createtime
     */
    private Date createtime;
}
