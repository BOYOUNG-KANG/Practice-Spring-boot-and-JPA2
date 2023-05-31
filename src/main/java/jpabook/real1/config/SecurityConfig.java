package jpabook.real1.config;


import jpabook.real1.config.jwt.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity //스프링 시큐리티 필터가 스프링 필터 체인에 등록
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public SecurityConfig(TokenProvider tokenProvider, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtAccessDeniedHandler jwtAccessDeniedHandler) {
        this.tokenProvider = tokenProvider;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

        @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() //csrf 비활성
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint) //만든것들로 에러처리
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //세션 사용 안함
                .and()

                .formLogin()
                .loginPage("/login")//사용자가 권한 없는 페이지로 이동 시, 로그인페이지로 이동시키는 방법
                .defaultSuccessUrl("/home")//성공 후 이동 페이지
                .failureUrl("/findId")
                .and()

                .authorizeRequests() // httpservletrequest를 사용하는 요청들에 대한 접근 제한 설정
                    .antMatchers("/users/**").authenticated() //이렇게 들어오는 주소들은 인증이 필요해
                    .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")//이렇게 들어오는 주소들은 어드민이나 매니저 권한이 있어야 들어올 수 있어(인가)
                    .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') ") //이렇게 들어오면 어드민 권한이 있어야돼(인가)
                    .anyRequest().permitAll() //다른 요청은 모두 permit
                    .and()


                .apply(new JwtSecurityConfig(tokenProvider)); //jwtsecurityconfig 등록
    }
}
