package kr.or.connect.reservation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 *  개인적으로 추가로 넣은 코드입니다.
 *  기획에서 '예약하기' 이후 디비에 저장되지 않지만, 
 *  '예약확인'기능을 바로 확인하고자 Post, Redirect, Get 패턴으로 메인화면으로 이동하도록 하였습니다.
 */
@Controller
@RequestMapping(path="/api")
public class ReservationController {

	@PostMapping("/ticketing")
	public String setReservation() {
		return "redirect:/";
	}
}
