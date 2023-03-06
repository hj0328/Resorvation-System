package kr.or.connect.reservation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "kr.or.connect.reservation.controller" })
public class WebMvcContextConfiguration extends WebMvcConfigurerAdapter {

	/*
	 * handler에게 controller외에 다른 url 매핑을 등록 application root 디렉토리 아래에 있는 폴더에서 리소스를
	 * 찾을 수 있게 한다. controller에서 해당 요청(/css.., /img.., /js..)을 하지 않는다.
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/**").addResourceLocations("/css/").setCachePeriod(31556926);
		registry.addResourceHandler("/img/**").addResourceLocations("/img/").setCachePeriod(31556926);
		registry.addResourceHandler("/js/**").addResourceLocations("/js/").setCachePeriod(31556926);
	}

	/*
	 * default servlet handler 사용하도록 설정 - 매핑정보가 없는 요청을 defaultServletHanlder가 처리하도록
	 * 함 - static 자원을 리턴
	 */
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

//	
//	  특정 url 요청을 controller 작성없이 바로 view로 볼 수 있게 설정 - 설정한 view는 내부적으로 viewResolver가
//	  view를 사용할 수 있게 한다.
//	  
//	  jsp 파일 생성 및 pageController를 만들면서 불필요하여 주석처리함
//	 
//	@Override
//	public void addViewControllers(final ViewControllerRegistry registry) {
//		 registry.addViewController("/").setViewName("/htmls/mainpage.html");
//		 registry.addViewController("/detail").setViewName("/htmls/detail.html");
//		 registry.addViewController("/bookinglogin").setViewName("/htmls/bookinglogin.html");
//		 registry.addViewController("/myreservation").setViewName("/htmls/myreservation.html");
//		 registry.addViewController("/review").setViewName("/htmls/review.html");
//		 registry.addViewController("/booking").setViewName("/WEB-INF/views/reserve.jsp");
//	}

	@Bean
	public InternalResourceViewResolver getInternalResourceViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

}
