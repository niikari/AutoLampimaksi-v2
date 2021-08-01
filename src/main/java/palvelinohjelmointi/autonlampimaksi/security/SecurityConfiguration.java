package palvelinohjelmointi.autonlampimaksi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        // Ei päästetä käyttäjää mihinkään sovelluksen resurssiin ilman
        // kirjautumista. Tarjotaan kuitenkin lomake kirjautumiseen, mihin
        // pääsee vapaasti. Tämän lisäksi uloskirjautumiseen tarjotaan
        // mahdollisuus kaikille.
		
		http.csrf().disable();
		http.headers().frameOptions().sameOrigin();
		
        http.authorizeRequests()
        		.antMatchers("/").permitAll()
        		.antMatchers("/h2-console","/h2-console/**").permitAll()
        		.antMatchers("/js", "/js/**").permitAll()
        		.antMatchers("/cars", "/cars/**").permitAll()
        		.antMatchers("/addnewenterprise", "/addnewenterprise/**").permitAll()
        		.antMatchers("/addnewcustomer", "/addnewcustomer/**").permitAll()
        		.antMatchers("/enterprise", "enterprise/**").permitAll()
        		.antMatchers("/list", "/list/**").permitAll()
        		.antMatchers("/reguser", "/reguser/**").permitAll()
        		.antMatchers("/rekisterointi").permitAll()
        		.antMatchers("/customerlogged", "/customerlogged/*").hasRole("customer")
        		.antMatchers("/enterpriselogged", "/enterpriselogged/*").hasRole("enterprise")
                .anyRequest().authenticated().and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/", true).permitAll().and()
                .logout().logoutSuccessUrl("/").permitAll();
    }

	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    

}
