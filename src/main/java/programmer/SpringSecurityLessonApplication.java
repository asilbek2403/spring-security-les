package programmer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpringSecurityLessonApplication {

	public static void main(String[] args) {

//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		String encryptKey = encoder.encode("Barg");
//		System.out.println(encryptKey);


		SpringApplication.run(SpringSecurityLessonApplication.class, args);
	}

}
