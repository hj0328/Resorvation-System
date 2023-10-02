package kr.or.connect.reservation.domain.comment;

import kr.or.connect.reservation.domain.comment.dto.Comment;
import kr.or.connect.reservation.domain.comment.dto.CommentRequest;
import kr.or.connect.reservation.domain.comment.dto.CommentResponse;

import java.util.List;

public interface CommentService {
	List<CommentResponse> getComments(int displayInfoId);

	CommentResponse getCommentResponse(int commentId);

	Comment setComment(Integer reservationInfoId, CommentRequest commentRequest);

	Comment modifyComment(Integer commentId, CommentRequest commentRequest);

	Comment getComment(Integer commentId);

	void deleteComment(Integer commentId);
}
