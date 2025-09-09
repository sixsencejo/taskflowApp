package org.example.taskflow.domain.activity.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class ActivityPageResponse<T> {
    private final List<T> content; // 현재 페이지의 데이터 목록
    private final long totalElements; // 전체 데이터 수
    private final int totalPages;    // 전체 페이지 수
    private final int size;          // 페이지 크기
    private final int number;        // 현재 페이지 번호 (0부터 시작)

    public ActivityPageResponse(Page<T> page) {
        this.content = page.getContent();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.size = page.getSize();
        this.number = page.getNumber();
    }
}
