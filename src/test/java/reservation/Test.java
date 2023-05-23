package reservation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Test {

	int a = 0; 
	public static void main(String[] args) {
		LocalDateTime now = LocalDateTime.now();
		System.out.println(now);
		
		
		String format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(now);
		System.out.println(format);
		
	}
	
}


