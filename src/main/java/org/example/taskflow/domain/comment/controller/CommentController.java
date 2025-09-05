package org.example.taskflow.domain.comment.controller;

import lombok.RequiredArgsConstructor;
import org.example.taskflow.common.dto.CommonResponse;
import org.example.taskflow.domain.comment.dto.CommentCreateRequest;
import org.example.taskflow.domain.comment.dto.CommentResponse;
import org.example.taskflow.domain.comment.service.CommentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks/{taskId}/comments")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    public CommonResponse<CommentResponse> createComment(
            @PathVariable Long taskId,
            @RequestBody CommentCreateRequest commentCreateRequest
    ) {
//        commentService.createComment(taskId, commentCreateRequest);
    }
}
