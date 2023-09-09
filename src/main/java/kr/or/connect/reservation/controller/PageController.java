package kr.or.connect.reservation.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import kr.or.connect.reservation.service.ReservationService;

@Controller
public class PageController {

	private final ReservationService reservationService;

	public PageController(ReservationService reservationService) {
		this.reservationService = reservationService;
	}

	@GetMapping("/")
	public String getMainPage(HttpSession session, ModelMap model) {
		String reservationEmail = (String) session.getAttribute("reservationEmail");
		if (reservationEmail != null && reservationEmail.contains("=")) {
			model.addAttribute("reservationEmail", reservationEmail.split("=")[1].replaceAll("%40", "@"));
		}

		return "mainpage";
	}

	@GetMapping("/detail")
	public String getDetailPage(HttpSession session, ModelMap model) {
		String reservationEmail = (String) session.getAttribute("reservationEmail");
		if (reservationEmail != null && reservationEmail.contains("=")) {
			model.addAttribute("reservationEmail", reservationEmail.split("=")[1].replaceAll("%40", "@"));
		}

		return "detail";
	}

	@GetMapping("/review")
	public String getReview() {
		return "review";
	}

	@GetMapping("/myreservation")
	public String getMyReservation(HttpSession session) {

		// 로그인이 되어 있어야 예약가능, 그렇지 않으면 로그인 화면으로 이동
		Object loginMember = session.getAttribute("loginMember");
		if (loginMember == null) {
			return "bookinglogin";
		}

		return "myreservation";
	}

	@GetMapping("/bookinglogin")
	public String getBookinglogin() {
		return "bookinglogin";
	}

	@PostMapping("/bookinglogin")
	public String postBookinglogin(@RequestBody String reservationEmail, HttpSession session) {
		String[] split = reservationEmail.split("reservationEmail=");
		if (split.length < 2) {
			return "redirect:/";
		}

		session.setAttribute("reservationEmail", reservationEmail);
		return "redirect:/myreservation";
	}

	@GetMapping("/booking")
	public String getReserve() {
		return "reserve";
	}

}
