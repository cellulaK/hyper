package com.hhx.hyper.controller;

import com.hhx.hyper.service.WeslieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author kai·yang
 * @Date 2023/1/3 16:11
 */

@RestController
@CrossOrigin
@RequestMapping("/uac")
public class WeslieController {

    @Autowired
    WeslieService weslieService;



    @PostMapping(value = "/ws/auto/simauthen/simAuthen", consumes = MediaType.TEXT_PLAIN_VALUE)
    public String simAuthen(HttpServletRequest request, HttpServletResponse response, @RequestBody String params){
        return weslieService.simAuthen(params, request,response);
    }


    @PostMapping("/callback")
    public String callback(){
        String callback = weslieService.callback();
        return callback;
    }


    @PostMapping(value = "/CheckAiuapTokenSoap", consumes = MediaType.TEXT_PLAIN_VALUE)
    public String checkAiuapToken( @RequestBody String params){
        return weslieService.checkAiuapToken(params);
    }

}
