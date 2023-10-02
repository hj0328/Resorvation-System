package kr.or.connect.reservation.domain.comment.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

@Getter @Setter
public class CommentRequest {
    @Size(max = 100)
    private String comment;
    @Max(9)
    private Float score;
}
