package kg.attractor.edufood.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CartMergeRedirectFilter cartMergeRedirectFilter() {
        return new CartMergeRedirectFilter();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   CartMergeRedirectFilter cartMergeRedirectFilter,
                                                   CustomLogoutSuccessHandler logoutSuccessHandler)
            throws Exception {
        http
                .sessionManagement(
                        session ->
                                session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .formLogin(login -> login
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/auth/login")
                        .defaultSuccessUrl("/")
                        .failureUrl("/auth/login?error=true")
                        .permitAll())
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .invalidateHttpSession(false)
                        .clearAuthentication(true)
                        .logoutSuccessHandler(logoutSuccessHandler)
                        .permitAll())
                .authorizeHttpRequests(
                        authorizeRequests -> authorizeRequests
                                .requestMatchers("/auth/**").permitAll()
                                .requestMatchers("/api/**").permitAll()
                                .requestMatchers("/profile/**", "/cart/checkout", "/cart/merge-options", "/cart/merge").authenticated()
                                .requestMatchers(HttpMethod.GET, "/").permitAll()
                                .anyRequest().permitAll()
                )
                .addFilterAfter(cartMergeRedirectFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}