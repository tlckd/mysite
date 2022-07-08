package com.douzone.config.web;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {
	
	
	//뷰리절버 
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");

		return viewResolver;
	}
	
	
	//Message Converter
	@Bean
	public StringHttpMessageConverter stringHttpMessageConverter() {
		StringHttpMessageConverter messageConverter = new StringHttpMessageConverter();
		//List<MediaType> list; 이런식으로 하지말고 자바스크립트처럼 
		
		messageConverter.setSupportedMediaTypes(
				Arrays.asList(
						new MediaType("text","html",Charset.forName("utf-8")) // 더 넣고싶으면 , 찍고 더 적어주면됨
						// 캐릭터셋 객체로 만들어준 이유 어레이의 유틸성 파라미터를 어레이리스토로 만들어서 반환해주는 유틸메소드
						) // list만들어서 add 하는거랑 같음, ()안에 객체넣어주면 그 객체들을 어레이리스트로 만들어서 넘겨줌, new Object(), new Object... 이런식으로 객체들을 매개변수로 넣어주면 add해서 만들어주는거   
				
				);
		
		//스트림함수 비슷한거 JS에 이런거 많음 
		
		return messageConverter;
	}
	
	
	@Bean //bean태그 달아줘야함 
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		
		//사용법 빌더보고 메시지 컨버터 하라고 하면됨 빌더에 옵션값줘서 잭슨에 메시지 컨버터에 옵션줄수 있음 
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder()
				.indentOutput(true) // 아웃풋에 옵션주겠다 
				.dateFormat(new SimpleDateFormat("yyyy-mm-dd")); // 날짜 포맷 vo안에 잭슨이 컨버팅하기전에 그 스트링에 관한 포맷팅 설정할 수 있음 join-Date 부분에 아작스ㅇㅇ 날짜부분에 포맷팅 줄 수 있음
				//옵션 예제 
		
		MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter(builder.build());
		
		messageConverter.setSupportedMediaTypes(
				Arrays.asList(
						new MediaType("application","json",Charset.forName("utf-8"))
							
						)   	
				);
		//추가설정 해줘야하는거 json을 보낼때 스트링임 "{...}" 이렇게 메시지컨버터가 바꿔주는거 
		//
		return messageConverter;
	}
	
	//<!-- 서블릿 컨테이너의 디폴트 서블릿 위임 핸들러, 4번까지는 디폴트 서블릿핸들러 작동함 5번은 web.xml 없어져서 디폴트서블릿 작동안함 -->
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		// TODO Auto-generated method stub
		configurer.enable();
	}
	

	//메시지 컨버터 설정 해주기 괄호 감싸는 부분 ㅇㅇ 
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(stringHttpMessageConverter());
		converters.add(mappingJackson2HttpMessageConverter());
	}
	
}
