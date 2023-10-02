package kr.or.connect.reservation.domain.comment.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class CommentImage {
	private String contentType;
	private Boolean deleteFlag;
	private Integer fileId;
	private String fileName;
	private Integer imageId;
	private LocalDateTime createDate;
	private LocalDateTime modifyDate;
	private Integer reservationInfoId;
	private Integer reservationUserCommentId;
	private String saveFileName;
}
