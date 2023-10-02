package kr.or.connect.reservation.domain.comment;

import kr.or.connect.reservation.config.exception.CustomException;
import kr.or.connect.reservation.config.exception.CustomExceptionStatus;
import kr.or.connect.reservation.domain.comment.dto.Comment;
import kr.or.connect.reservation.domain.comment.dto.CommentImage;
import kr.or.connect.reservation.domain.comment.dto.CommentRequest;
import kr.or.connect.reservation.domain.comment.dto.CommentResponse;
import kr.or.connect.reservation.domain.reserve.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentDao commentDao;
    private final ReservationService reservationService;

    @Override
    public List<CommentResponse> getComments(int displayInfoId) {
        List<Comment> commentList = commentDao.selectComments(displayInfoId);
        List<CommentResponse> responseList = new ArrayList<>();
        for (Comment comment : commentList) {
            CommentResponse commentResponse = convertCommentResponse(comment);
            responseList.add(commentResponse);
        }

        return responseList;
    }

    private CommentResponse convertCommentResponse(Comment comment) {
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setCommentId(comment.getId());
        commentResponse.setModifyDate(comment.getModifyDate());
        commentResponse.setCreateDate(comment.getCreateDate());
        commentResponse.setProductId(comment.getProductId());
        commentResponse.setScore(comment.getScore());
        commentResponse.setReservationInfoId(comment.getReservationInfoId());
        commentResponse.setComment(comment.getComment());
        CommentImage image = commentDao.findCommentImageById(comment.getId())
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.COMMENT_IMAGE_NOT_FOUND));

        commentResponse.setCommentImage(image);
        return commentResponse;
    }

    @Override
    public CommentResponse getCommentResponse(int commentId) {
        Comment comment = commentDao.findCommentById(commentId)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.COMMENT_NOT_FOUND));

        CommentImage image = commentDao.findCommentImageById(commentId)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.COMMENT_IMAGE_NOT_FOUND));

        CommentResponse commentResponse = convertCommentResponse(comment, image);
        return commentResponse;
    }

    private CommentResponse convertCommentResponse(Comment comment, CommentImage image) {
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setComment(comment.getComment());
        commentResponse.setScore(comment.getScore());
        commentResponse.setCommentImage(image);
        return commentResponse;
    }

    @Override
    public Comment setComment(Integer reservationInfoId, CommentRequest commentRequest) {
        // 이미 댓글을 달았다면 예외처리
        Optional<Integer> optionValue = commentDao.countByReservationInfoId(reservationInfoId);
        if (optionValue.isPresent() && optionValue.get() > 1) {
            throw new CustomException(CustomExceptionStatus.MULTIPLICITY_COMMENTS_VIOLATION);
        }

        Comment comment = createComment(reservationInfoId, commentRequest);
        return comment;
    }

    @Transactional
    private Comment createComment(Integer reservationInfoId, CommentRequest commentRequest) {
        Integer productId = reservationService.getProductIdById(reservationInfoId);

        int commentId = commentDao.saveComment(productId, reservationInfoId, commentRequest)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.COMMENT_SAVING_FAIL));

        Comment comment = commentDao.findCommentById(commentId)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.COMMENT_NOT_FOUND));
        return comment;
    }

    @Override
    @Transactional
    public Comment modifyComment(Integer commentId, CommentRequest commentRequest) {

        commentDao.updateComment(commentId, commentRequest);
        Comment comment = commentDao.findCommentById(commentId)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.COMMENT_NOT_FOUND));
        return comment;
    }

    @Override
    public Comment getComment(Integer commentId) {
        Comment comment = commentDao.findCommentById(commentId)
                .orElseThrow(() -> new CustomException(CustomExceptionStatus.COMMENT_NOT_FOUND));
        return comment;
    }

    @Override
    @Transactional
    public void deleteComment(Integer commentId) {
        commentDao.deleteCommentById(commentId);
    }
}
