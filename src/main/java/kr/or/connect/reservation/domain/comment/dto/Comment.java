package kr.or.connect.reservation.domain.comment.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class Comment {
    private Integer id;
    private Integer productId;
    private Integer reservationInfoId;
    private Float score;
    private String comment;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
}
