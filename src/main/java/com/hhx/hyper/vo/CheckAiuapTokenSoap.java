package com.hhx.hyper.vo;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author kai·yang
 * @Date 2023/1/5 14:00
 */
@Data
@XmlRootElement(name = "USERRSP")
@XmlAccessorType(XmlAccessType.FIELD)
public class CheckAiuapTokenSoap {


    @XmlElement(name = "HEAD")
    Head head;

    @XmlElement(name = "BODY")
    Body body;


    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Head{
        /**
         * 消息标志
         */
        @XmlElement(name = "CODE", required = true)
        String code;

        /**
         * 消息序列号
         */
        @XmlElement(name = "SID", required = true)
        String sid;

        /**
         * 时间戳
         */
        @XmlElement(name = "TIMESTAMP", required = true)
        String timestamp;

        /**
         * 应用标识
         */
        @XmlElement(name = "SERVICEID", required = true)
        String serviceid;

    }

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Body{

        @XmlElement(name = "RSP")
        String rsp;

        /**
         * 主账号
         */
        @XmlElement(name = "MAINACCTID")
        String mainAcct;

        /**
         * 电话
         */
        @XmlElement(name = "APPACCTID")
        String appAcctId;


    }
}
