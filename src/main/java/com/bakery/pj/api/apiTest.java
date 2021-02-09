package com.bakery.pj.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Api(value = "apitTest")
@RequestMapping("/api/test")
public class apiTest {

    @GetMapping(value = "/board")
    public Map<String, String> selectOneBoard(@ApiParam(value = "게시판번호", required = true, example = "1") @RequestParam String no) {
        Map<String, String> result = new HashMap<>();
        result.put("author", "victolee");
        result.put("content", "V1 API 내용");
        return result;
    }

    @PostMapping(value = "/board")
    public Map<String, String> insertBoard(@ApiParam(value = "게시판번호", required = true, example = "1") @RequestParam String no,
                                           @ApiParam(value = "제목", required = true, example = "제목입니다.") @RequestParam String title,
                                           @ApiParam(value = "작성자", required = true, example = "test@naver.com") @RequestParam String email) {
        Map<String, String> result = new HashMap<>();
        result.put("no", no);
        result.put("title", title);
        result.put("email", email);

        return result;
    }
}
