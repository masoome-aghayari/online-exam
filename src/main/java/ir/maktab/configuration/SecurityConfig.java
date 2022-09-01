package ir.maktab.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String AUTHORITY_QUERY = "Select user.email, role.roleName from user, role where email = ? and user.role_id = role.id";
    private static final String USERNAME_QUERY = "Select email, password, 1 from user where email = ?";

    @Autowired
    DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/loginProcess")
                .usernameParameter("email")
                .passwordParameter("password")
                .successForwardUrl("/successProcess")
                .failureUrl("/loginError")
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/register").permitAll()
                .antMatchers("/registerProcess").permitAll()
                .antMatchers("/verify-account").permitAll()
                .antMatchers("/resend").permitAll()
                .antMatchers("/resendProcess").permitAll()
                .antMatchers("/error").permitAll()
                .antMatchers("/").permitAll()
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery(USERNAME_QUERY)
                .authoritiesByUsernameQuery(AUTHORITY_QUERY)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }
}
