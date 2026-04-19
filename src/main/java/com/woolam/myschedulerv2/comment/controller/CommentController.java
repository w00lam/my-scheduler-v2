package com.woolam.myschedulerv2.comment.controller;

import com.woolam.myschedulerv2.facade.UserActionFacade;
import com.woolam.myschedulerv2.auth.dto.LoginUserDto;
import com.woolam.myschedulerv2.comment.dto.CommentCreateRequest;
import com.woolam.myschedulerv2.comment.dto.CommentCreateResponse;
import com.woolam.myschedulerv2.comment.dto.CommentGetResponse;
import com.woolam.myschedulerv2.comment.dto.CommentUpdateRequest;
import com.woolam.myschedulerv2.comment.service.CommentService;
import com.woolam.myschedulerv2.common.response.CommonApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>댓글 관련 HTTP 요청을 처리하는 REST 컨트롤러입니다.
 * "/api/schedules" 경로로 들어오는 요청을 담당합니다.</p>
 *
 * @author woolam
 * @version 2.0
 * @since 2026-04-17
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;
    private final UserActionFacade userActionFacade;

    @PostMapping
    public ResponseEntity<CommonApiResponse<CommentCreateResponse>> createComment(
            @SessionAttribute(name = "loginUser") LoginUserDto loginUser,
            @Valid @RequestBody CommentCreateRequest request) {
        CommentCreateResponse response = userActionFacade.createComment(loginUser.userId(), request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonApiResponse.success("댓글 생성에 성공했습니다.", response));
    }

    @GetMapping
    public ResponseEntity<CommonApiResponse<List<CommentGetResponse>>> getAllComments(
            @SessionAttribute(name = "loginUser") LoginUserDto loginUser,
            @RequestParam Long scheduleId) {
        List<CommentGetResponse> response = userActionFacade.getCommentsBySchedule(loginUser.userId(), scheduleId);

        return ResponseEntity.ok(CommonApiResponse.success("댓글 조회에 성공했습니다.", response));
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<CommonApiResponse<CommentGetResponse>> updateComment(
            @PathVariable Long commentId,
            @SessionAttribute(name = "loginUser") LoginUserDto loginUser,
            @Valid @RequestBody CommentUpdateRequest request) {
        CommentGetResponse response = commentService.update(commentId, loginUser.userId(), request);

        return ResponseEntity.ok(CommonApiResponse.success("댓글 수정에 성공했습니다.", response));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommonApiResponse<Void>> deleteComment(
            @PathVariable Long commentId,
            @SessionAttribute(name = "loginUser") LoginUserDto loginUser) {
        commentService.delete(loginUser.userId(), commentId);

        return ResponseEntity.ok(CommonApiResponse.success("댓글을 삭제했습니다."));
    }
}
