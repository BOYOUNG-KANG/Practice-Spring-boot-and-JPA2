package jpabook.real1;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import jpabook.real1.domain.Address;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Real1Application {

		public static void main(String[] args) {

		SpringApplication.run(Real1Application.class, args);
	}
	@Bean
	Hibernate5Module hibernate5Module() {
			Hibernate5Module hibernate5Module = new Hibernate5Module();
			//hibernate5Module.configure(Hibernate5Module.Feature.FORCE_LAZY_LOADING, true);
			return hibernate5Module;
	}

}
