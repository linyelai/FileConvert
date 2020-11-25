package com.linseven.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author Tyrion
 * @version 1.0
 * @date 2020/11/9 19:14
 */
@RestController
public class GoogleAuthController {

    @GetMapping("/Callback")
    public void token(HttpServletResponse response,HttpServletRequest request){

         Map<String,String[]> param = request.getParameterMap();

    }
}
