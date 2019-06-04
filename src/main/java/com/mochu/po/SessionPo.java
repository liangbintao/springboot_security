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
@Table(name = "session")
public class SessionPo {

    /**
     * id
     */
    @Id
    private String id;

    /**
     * userid
     */
    private String userid;

    /**
     * createtime
     */
    private Date createtime;

    /**
     * failtime
     */
    private Date failtime;
}
