package br.com.hermes.api;

import br.com.hermes.api.security.CustomUserDetails;
import br.com.hermes.model.criterias.UserCriteria;
import br.com.hermes.model.entitys.User;
import br.com.hermes.model.services.UserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
public class Boot extends SpringBootServletInitializer {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(Boot.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(
            SpringApplicationBuilder application) {
        return application.sources(Boot.class);
    }

    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        //jogando a ordem lá para baixo, tem que rodar do filtro do security.
        bean.setOrder(-100000);
        return bean;
    }

    @Autowired
    public void authenticationManager(AuthenticationManagerBuilder builder, UserService service) throws Exception {
        //To-do : setar um usuario default para cao do banco estar vazio
        UserDetailsService user = userDetailsService(service);
        builder.userDetailsService(user);
        //.passwordEncoder(passwordEncoder);
    }

    /**
     * Método que retorna um Usuario do service
     *
     * @param service
     * @return
     */
    private UserDetailsService userDetailsService(final UserService service) {
        return username -> {
            try {
                Map<Long, Object> criteria = new HashMap<>();
                criteria.put(UserCriteria.NAME_EQ, username);
                List<User> userList = service.readByCriteria(criteria, null, null);
                User user = null;
                for (User userItem : userList) {
                    user = userItem;
                }
                return new CustomUserDetails(user);
            } catch (Exception ex) {
                Logger.getLogger(Boot.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        };
    }
}
