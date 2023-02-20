package kr.or.connect.reservation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.connect.reservation.dao.CommentDao;
import kr.or.connect.reservation.dto.CommentDto;
import kr.or.connect.reservation.dto.CommentImageDto;
import kr.or.connect.reservation.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	private final CommentDao commentDao;

	@Autowired
	public CommentServiceImpl(CommentDao commentDao) {
		this.commentDao = commentDao;
	}

	@Override
	public List<CommentDto> getComments(int displayInfoId) {
		List<CommentDto> commentList = commentDao.selectComment(displayInfoId);
		for (CommentDto commentDto : commentList) {
			List<CommentImageDto> commentImageList = commentDao
					.selectCommentImageByCommentId(commentDto.getCommentId());
			commentDto.setCommentImages(commentImageList);
		}

		return commentList;
	}

}
