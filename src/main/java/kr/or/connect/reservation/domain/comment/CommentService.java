package kr.or.connect.reservation.domain.comment;

import java.util.List;

import kr.or.connect.reservation.domain.comment.dto.CommentDto;

public interface CommentService {
	List<CommentDto> getComments(int displayInfoId);
}
