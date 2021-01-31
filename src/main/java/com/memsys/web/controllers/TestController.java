package com.memsys.web.controllers;


import com.mysql.cj.xdevapi.JsonArray;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class TestController {
    @RequestMapping(value = "/test")
    public void test(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // JSON 을 활용하면 많은 양의 데이터를 백앤드와 프론트를 이동하며 다룰 수 있다.
        // JSONObject  : {키 : 값, 키 : 값, 키 : 값}
        // JSONObject.getValue("키") -> 값
        // JSONArray   : {값, 값, 값, 값, 값}
        // JSONArray.get(0) -> 값

        JSONArray jsonArray = new JSONArray();
        jsonArray.put("관리자1");
        jsonArray.put("관리자2");
        jsonArray.put("관리자3");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("startPage", "1");
        jsonObject.put("endPage", "4");
        jsonObject.put("requestPage", "1");
        jsonObject.put("users", jsonArray);

        response.getWriter().print( jsonObject.toString(2));

    }
}
