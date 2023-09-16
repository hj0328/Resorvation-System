package kr.or.connect.reservation.domain.comment.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CommentDto {
	private String comment;
	private int commentId;
	private String createDate;
	private String modifyDate;
	private int productId;
	private String reservationDate;
	private String reservationEmail;
	private int reservationInfoId;
	private String reservationName;
	private String reservationTelephone;
	private int score;
	private List<CommentImageDto> commentImages;
}
