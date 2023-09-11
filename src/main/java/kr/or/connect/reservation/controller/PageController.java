package kr.or.connect.reservation.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class PageController {

	@GetMapping("/")
	public String getMainPage(HttpSession session, ModelMap model) {
		log.info("GET /");
		String reservationEmail = (String) session.getAttribute("reservationEmail");
		if (reservationEmail != null) {
			model.addAttribute("reservationEmail", reservationEmail);
		}

		return "mainpage";
	}

	@GetMapping("/detail")
	public String getDetailPage(HttpSession session, ModelMap model) {
		log.info("GET /detail");
		String reservationEmail = (String) session.getAttribute("reservationEmail");
		if (reservationEmail != null) {
			model.addAttribute("reservationEmail", reservationEmail);
		}

		return "detail";
	}

	@GetMapping("/review")
	public String getReview() {
		log.info("GET /review");
		return "review";
	}

	@GetMapping("/my-reservation")
	public String getMyReservation(HttpSession session) {
		log.info("GET /my-reservation, reservationEmail={}", session.getAttribute("reservationEmail"));

		// 로그인이 되어 있어야 예약가능, 그렇지 않으면 로그인 화면으로 이동
		Object reservationEmail = session.getAttribute("reservationEmail");
		if (reservationEmail == null) {
			return "bookinglogin";
		}

		return "myreservation";
	}

	@GetMapping("/booking-login")
	public String getBookinglogin() {
		log.info("GET /booking-login");
		return "bookinglogin";
	}

	@PostMapping("/booking-login")
	public String postBookingLogin(@RequestParam String reservationEmail, HttpSession session) {
		log.info("POST /booking-login, reservationEmail={}", reservationEmail);

		if (reservationEmail.isEmpty()) {
			return "redirect:/";
		}

		session.setAttribute("reservationEmail", reservationEmail);
		return "redirect:/my-reservation";
	}

	@GetMapping("/booking")
	public String getReserve() {
		log.info("GET /booking");
		return "reserve";
	}

}
