package kr.or.connect.reservation.service;

import java.util.List;

import kr.or.connect.reservation.dto.CommentDto;

public interface CommentService {
	List<CommentDto> getComments(int displayInfoId);
}
