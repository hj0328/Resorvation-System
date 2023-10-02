package kr.or.connect.reservation.domain.comment.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class CommentResponse {
	private String comment;
	private Integer commentId;
	private CommentImage commentImage;
	private LocalDateTime createDate;
	private LocalDateTime modifyDate;
	private Integer productId;
	private Integer reservationInfoId;
	private Float score;
}
