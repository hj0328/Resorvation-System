package kr.or.connect.reservation.domain.comment;

import kr.or.connect.reservation.domain.comment.dto.Comment;
import kr.or.connect.reservation.domain.comment.dto.CommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    /**
     *  예약 상품 댓글 등록
     *  댓글은 최대 1개만 등록 가능
     */
    @PostMapping("/reservation-comments/{reservationInfoId}")
    public Comment createComment(@PathVariable Integer reservationInfoId,
                                 @RequestBody CommentRequest commentRequest) {
        return commentService.setComment(reservationInfoId, commentRequest);
    }

    /**
     *  예약 상품 댓글 수정
     */
    @PutMapping("/{commentId}")
    public Comment modifyComment(@PathVariable Integer commentId,
                                 @RequestBody CommentRequest commentRequest) {
        return commentService.modifyComment(commentId, commentRequest);
    }

    /**
     * 예약 상품 댓글 삭제
     */
    @DeleteMapping("/{commentId}")
    public Comment deleteComment(@PathVariable Integer commentId) {

        Comment returnComment = commentService.getComment(commentId);
        commentService.deleteComment(commentId);
        return returnComment;
    }
}
