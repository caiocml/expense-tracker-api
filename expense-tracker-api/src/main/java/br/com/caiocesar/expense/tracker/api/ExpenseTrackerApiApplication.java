package br.com.caiocesar.expense.tracker.api;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import br.com.caiocesar.expense.tracker.api.filters.AuthFilter;
import br.com.caiocesar.expense.tracker.api.repository.UserRepository;

@SpringBootApplication
public class ExpenseTrackerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpenseTrackerApiApplication.class, args);
		System.out.println("Aplication Started");
	}
	
	@Autowired
	UserRepository userRepository;
	
	@Bean
	public FilterRegistrationBean<AuthFilter> filterRegistrationBean(){
		
		FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<AuthFilter>();
		AuthFilter auth = new AuthFilter(userRepository);
		registrationBean.setFilter(auth);
		registrationBean.addUrlPatterns("/api/categories/*");
		return registrationBean;
		
	}
	
	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter(){
		
		FilterRegistrationBean<CorsFilter> filter = new FilterRegistrationBean<>();
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		
		//can use this config to allow only a few hosts
		//config.addAllowedOrigin("http://localhost:5500");
		
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		
		source.registerCorsConfiguration("/**", config);
		
		CorsFilter cors = new CorsFilter(source);
		filter.setFilter(cors);
		
		filter.setOrder(0);
		
		return filter;
	}
	
	 private static final String dateFormat = "yyyy-MM-dd";
	 private static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
		
	@Bean
	public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
		return builder -> {
			builder.simpleDateFormat(dateFormat);
			builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(dateFormat)));
			builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimeFormat)));
			builder.deserializers(new LocalDateDeserializer(DateTimeFormatter.ofPattern(dateFormat)));
			builder.deserializers(new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(dateTimeFormat)));
		};
	}

}
