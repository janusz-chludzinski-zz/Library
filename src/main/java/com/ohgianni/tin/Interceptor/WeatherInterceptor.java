package com.ohgianni.tin.Interceptor;

import com.ohgianni.tin.Weather.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.NOT_MODIFIED;
import static org.springframework.http.HttpStatus.OK;

public class WeatherInterceptor extends HandlerInterceptorAdapter{

    private RestTemplate restTemplate;

    @Autowired
    public WeatherInterceptor() {
        this.restTemplate = new RestTemplate();
    }

//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
//                           @Nullable ModelAndView modelAndView) throws Exception {
//
//        ResponseEntity<Weather> responseEntity = restTemplate.getForEntity("http://api.openweathermap.org/data/2.5/weather?q=Pruszków&appid=197d15f3cc9590316822e7894a6f1e40&units=metric", Weather.class);
//        Weather weather = responseEntity.getBody();
//
//        if(isResponseOk(responseEntity) && nonNull(modelAndView) && nonNull(weather) ) {
//            System.out.println(weather);
//            modelAndView.addObject("weather", weather);
//        }
//    }

    private boolean isResponseOk(ResponseEntity responseEntity) {
        HttpStatus responseCode = responseEntity.getStatusCode();
        return responseCode.equals(OK) || responseCode.equals(NOT_MODIFIED);
    }
}
