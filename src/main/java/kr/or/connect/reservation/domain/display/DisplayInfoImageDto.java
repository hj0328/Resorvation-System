package kr.or.connect.reservation.domain.display;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DisplayInfoImageDto {
	private String contentType;
	private String createDate;
	private boolean deleteFlag;
	private int displayInfoId;
	private int displayInfoImageId;
	private int fileId;
	private String fileName;
	private String modifyDate;
	private String saveFileName;
}
