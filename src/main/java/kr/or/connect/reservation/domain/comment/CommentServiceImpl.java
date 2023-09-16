package kr.or.connect.reservation.domain.comment;

import kr.or.connect.reservation.domain.comment.dto.CommentDto;
import kr.or.connect.reservation.domain.comment.dto.CommentImageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentDao commentDao;

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
