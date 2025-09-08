package org.example.taskflow.domain.comment.controller;

import lombok.RequiredArgsConstructor;
import org.example.taskflow.common.dto.CommonResponse;
import org.example.taskflow.common.utils.ResponseUtil;
import org.example.taskflow.domain.comment.dto.*;
import org.example.taskflow.domain.comment.enums.ResponseCode;
import org.example.taskflow.domain.comment.service.CommentService;
import org.example.taskflow.domain.user.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks/{taskId}/comments")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;

    @PostMapping
    public CommonResponse<CommentResponse> createComment(
            @PathVariable Long taskId,
            @RequestBody CommentCreateRequest commentCreateRequest
    ) {
        CommentResponse commentResponse = commentService.createComment(taskId, userService.getUserId(), commentCreateRequest);

        return ResponseUtil.success(commentResponse, ResponseCode.COMMENT_CREATED_RESPONSE.getMessage());
    }

    @GetMapping
    public CommonResponse<CommentPageResponse<CommentGetAllResponse>> getComments(
            @PathVariable Long taskId,
            @RequestParam(defaultValue = "newest") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        CommentPageResponse<CommentGetAllResponse> commentResponses = commentService.getComments(taskId, sort, page, size);

        return ResponseUtil.success(commentResponses, ResponseCode.COMMENT_FINDS_RESPONSE.getMessage());
    }

    @PutMapping("/{commentId}")
    public CommonResponse<CommentResponse> updateComment(
            @PathVariable Long taskId,
            @PathVariable Long commentId,
            @RequestBody CommentUpdateRequest commentUpdateRequest
    ) {
        CommentResponse commentResponse = commentService.updateComment(taskId, commentId, commentUpdateRequest);

        return ResponseUtil.success(commentResponse, ResponseCode.COMMENT_UPDATED_RESPONSE.getMessage());
    }

    @DeleteMapping("/{commentId}")
    public CommonResponse<Void> deleteComment(
            @PathVariable Long taskId,
            @PathVariable Long commentId
    ) {
        boolean isParent = commentService.deleteComment(taskId, commentId);

        if (isParent) {
            return ResponseUtil.success(null, ResponseCode.COMMENT_DELETED_WITH_CHILD_RESPONSE.getMessage());
        }
        return ResponseUtil.success(null, ResponseCode.COMMENT_DELETED_RESPONSE.getMessage());
    }
}
