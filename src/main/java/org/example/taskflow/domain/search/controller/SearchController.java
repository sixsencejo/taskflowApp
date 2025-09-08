package org.example.taskflow.domain.search.controller;

import lombok.RequiredArgsConstructor;
import org.example.taskflow.common.dto.CommonResponse;
import org.example.taskflow.common.utils.ResponseUtil;
import org.example.taskflow.domain.search.dto.SearchResponse;
import org.example.taskflow.domain.search.service.SearchServiceImpl;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/search")
@RestController
@RequiredArgsConstructor
public class SearchController {

    private final SearchServiceImpl searchServiceImpl;

    /**
     * 통합 검색
     */
    @GetMapping
    public CommonResponse<SearchResponse> search(
            @RequestParam(required = false) String q) {

        if (q == null || q.trim().isEmpty()) {
            return ResponseUtil.fail("검색어를 입력해주세요.");
        }

        SearchResponse searchResult = searchServiceImpl.search(q);
        return ResponseUtil.success(searchResult, "검색 완료");
    }
}