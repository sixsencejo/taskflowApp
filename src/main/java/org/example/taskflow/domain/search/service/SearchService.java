package org.example.taskflow.domain.search.service;


import org.example.taskflow.domain.search.dto.SearchResponse;
import org.example.taskflow.domain.task.dto.TaskPageResponse;
import org.example.taskflow.domain.task.dto.TaskResponse;

public interface SearchService {
    /**
     * 통합 검색
     * @param query 검색어
     * @return 통합 검색 결과
     */
    SearchResponse search(String query);

    /**
     * 작업 검색 (페이징)
     * @param query 검색어
     * @param page 페이지 번호
     * @param size 페이지 크기
     * @return 작업 검색 결과 (페이징)
     */
    TaskPageResponse<TaskResponse> searchTasks(String query, int page, int size);

}
