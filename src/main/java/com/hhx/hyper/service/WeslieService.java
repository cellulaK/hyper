package com.hhx.hyper.service;

import com.alibaba.fastjson2.JSON;
import com.asiainfo.a4.core.common.util.AsiaCipher;
import com.hhx.hyper.vo.CheckAiuapTokenSoap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @author kai·yang
 * @Date 2023/1/3 16:12
 */
@Service
@Slf4j
public class WeslieService {


    public String simAuthen(String enParams, HttpServletRequest request, HttpServletResponse response ){
        String apikey = request.getHeader("apikey");
        String md5Apikey = DigestUtils.md5Hex("3521z25176e1314a2ct61we25adaba0875bdggpd");
        String decrypt = AsiaCipher.dencrypt(enParams, apikey);
        Map<String, Object> result = new HashMap<>();
        result.put("code", "0");
        result.put("msg", "success");
        result.put("taskId", "7f24bhjhgfrpo45454");
        result.put("mainAcct", "zhangsan");
        result.put("mobile", "18702003243");
        List<Map<String, String>> subaccts = new ArrayList<>();
        Map<String, String> item = new HashMap<>();
        item.put("subbacct", AsiaCipher.encrypt("zhangsan", md5Apikey));
        subaccts.add(item);
        result.put("subaccts", subaccts);
        String json = JSON.toJSONString(result);

        String enJson = AsiaCipher.encrypt(json, apikey);
        String rrr = "6c22a51222449f07O9OOPCjPMpKXOV+bVni+a6UZ90vV9puV6nqjRSoNWoNAcarKNcefmjy7HXx3XYXNBEq79el2oh8R6L/FSfs66fZ1ETHu1v+ZPY1NK35AFJvfk/+/sguxYuIZEr+vEnQq1549XTvAQWBj/v6OqnC5tiMQ+iD9C9QyVOahvNazXmM=";
        return rrr;
    }


    public String callback(){
        String md5Apikey = DigestUtils.md5Hex("3521z25176e1314a2ct61we25adaba0875bdggpd");
        Map<String, Object> result = new HashMap<>();
        result.put("result", "1");
        result.put("resultDes", "success");

        Map<String, Object> item = new HashMap<>();
        item.put("phone", "18702003243");
        item.put("state", 0);
        result.put("phoneSendStat", item);
        String json = JSON.toJSONString(result);
        String enJson = AsiaCipher.encrypt(json, md5Apikey);
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity(enJson, httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost:8771/fourA/callback", entity, String.class);
        if (HttpStatus.OK.value() == responseEntity.getStatusCode().value()){
            String body = responseEntity.getBody();
            log.info("4A simAuthen response body [{}]", body);
        }
        return enJson;
    }


    /**
     *
     * @return
     */
    public String checkAiuapToken(String parmas){
        CheckAiuapTokenSoap checkAiuapTokenSoap = new CheckAiuapTokenSoap();
        CheckAiuapTokenSoap.Head head = new CheckAiuapTokenSoap.Head();
        CheckAiuapTokenSoap.Body body = new CheckAiuapTokenSoap.Body();
        head.setCode("000");
        head.setSid("000");
        head.setTimestamp("202301051409");
        head.setServiceid("serviceId");
        body.setRsp("1");
        body.setAppAcctId("weslie@qq.com");
        body.setMainAcct("725859549313028");
        checkAiuapTokenSoap.setBody(body);
        checkAiuapTokenSoap.setHead(head);
        StringWriter sw = new StringWriter();
        //读取并解析xml文件
//        SAXReader reader = new SAXReader();
//        try {
//            Document document = reader.read(new ByteArrayInputStream(parmas.getBytes(Charset.forName("utf-8"))));
//            Element rootElement = document.getRootElement();
//            Element userrsp = rootElement.element("USERRSP");
//            Element responseBody = userrsp.element("BODY");
//            String rsp = responseBody.elementTextTrim("RSP");
//            if(rsp.equals("0")){
//                String mainacctid = responseBody.elementTextTrim("MAINACCTID");
//                String appacctid = responseBody.elementTextTrim("APPACCTID");
//
//            }
//        } catch (DocumentException e) {
//            log.info("解析xml异常：{}", parmas);
//            e.printStackTrace();
//        }
//
        try {
            JAXBContext context = JAXBContext.newInstance(CheckAiuapTokenSoap.class);
            Marshaller marshaller = context.createMarshaller();
            //编码
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            //xml 输出格式化
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            marshaller.marshal(checkAiuapTokenSoap, sw);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return sw.toString();
    }


    public static void main(String[] args) {
        String decrypt = AsiaCipher.dencrypt("6c22a51222449f07O9OOPCjPMpKXOV+bVni+a6UZ90vV9puV6nqjRSoNWoNAcarKNcefmjy7HXx3XYXNBEq79el2oh8R6L/FSfs66fZ1ETHu1v+ZPY1NK35AFJvfk/+/sguxYuIZEr+vEnQq1549XTvAQWBj/v6OqnC5tiMQ+iD9C9QyVOahvNazXmM=",
                "8b6d28d636b83579920fdcec07157b48");
        System.out.println(decrypt);
    }

}
