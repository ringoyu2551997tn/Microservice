package com.sha.microserviceusermanagement.config;

import com.sha.microserviceusermanagement.service.UserDetailServicelmpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
// Tùy chỉnh cấu hình bảo mật.
//b29 sau khi tạo ra webSecurityConfig cần extends WebSercurityConfigurerAdapter( ghi đè phương thức config)
//cần 2 chú thích @Configuration và //@EnableWebSecurity

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //B32 gọi UserDetailsService và @Autowired để DI dữ liệu. sau đó quay lại B31 viết method bên trong
   @Autowired
    private UserDetailServicelmpl userDetailService;


    //B33 cấu hình PasswordEncoder theo bộ mã hóa mật khẩu B-Crypt và Với bộ mã hóa này,
    // bạn có thể tạo mật khẩu được mã hóa duy nhất cho mỗi lần.
    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }

    //B30 Override phương thức
    //vì làm việc với các cổng khác nhau nên sử dụng service  protected void configure(HttpSecurity http) throws Exception
    //trong WebSecurityConfigurerAdapter
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().and()
                // cho phép tất cả các request
                .authorizeRequests()

                // đây là các đường dẫn sẽ tự động dẫn tới không cần xác thực.
                //Nếu có bất kỳ kết quả phù hợp nào với các mục này như "tài nguyên /", "/ lỗi" và "/ dịch vụ" thì cho phép tất cả chúng.
                .antMatchers("/resources/**","/error","/service/**").permitAll()

                //nhưn đường dẫn này sẽ được truy cập công khai voi moi người.
                .anyRequest().fullyAuthenticated()
                .and()
                .logout().permitAll()
                .logoutRequestMatcher(new AntPathRequestMatcher("/service/logout","POST"))
                //"/ logout" sẽ đăng xuất người dùng bằng cách làm mất hiệu lực Phiên HTTP và đường dẫn đăng xuất sẽ là "/ service / logout"
                //Và đường dẫn đăng nhập sẽ là "/ service / login"
                //
                //Và kích hoạt xác thực cơ bản
                .and()

                .formLogin().loginPage("/service/login").and()
                .httpBasic().and()
                //Tắt tính năng giả mạo yêu cầu trang web chéo. Cross Site Request Forgery là một cuộc tấn công quan trọng nên chúng ta sẽ nói về sau.
                .csrf().disable();
    }



    //B31
    //Phương pháp thứ hai  sẽ là định nghĩa UserDetailsService.
    //Trong các bước trước, chúng tôi đã tùy chỉnh UserDetailsService.
    // chúng tôi sẽ định nghĩa nó trong lớp cấu hình bảo mật.
    //
    //Để làm điều đó, chúng tôi sẽ ghi đè phương thức cấu hình.
    //Phương pháp cấu hình này là để ghi đè AuthenticationManagerBuilder mặc định.
    //trước khi viết cấu hình thực hiện bước B32

    //sau khi viết phương thức bên trong  viết bean passwordEncoder, ở bước 33
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
    }

    //B34
    //lớp này là một lớp cấu hình.
    // Vì vậy, chúng tôi xác định bean mới hoặc chúng tôi có thể tùy chỉnh các cấu hình springboot khác.
    //@Bean. Bạn biết rằng spring bean là một phiên bản tạo mới và các phiên bản mặc định là singleton.
    //
    //Vì vậy, điều này sẽ được tạo một lần và chúng tôi có thể truy cập nó trong suốt ứng dụng.
    //B35 quay lại file cau hình
    @Bean
    public WebMvcConfigurer corsConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }

}
