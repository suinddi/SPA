package com.memsys.web.controllers;

import com.memsys.web.Constants;
import com.memsys.web.services.CounterService;
import com.memsys.web.vos.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@RestController
@RequestMapping(value = "/admin/apis/counter/")
public class CounterApiController extends StandardRestController {
    private final CounterService counterService;

    @Autowired
    public CounterApiController(CounterService counterService) {
        this.counterService = counterService;
    }

    @RequestMapping(value = "get-member-count")
    public void getTotalMemberCount(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int[] countArray;
        HttpSession session = request.getSession();
        Object userVoObject = session.getAttribute(Constants.USER_VO_SESSION_ATTRIBUTE_NAME);
        if (userVoObject instanceof UserVo && ((UserVo) userVoObject).isAdmin()) {
            countArray = this.counterService.getMemberCount();
        } else {
            countArray = new int[] {-1, -1, -1, -1};
        }
        response.getWriter().print(String.format("%d,%d,%d,%d", countArray[0], countArray[1], countArray[2], countArray[3]));
        response.getWriter().close();
    }
}
