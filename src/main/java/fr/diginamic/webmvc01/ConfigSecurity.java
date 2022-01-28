package fr.diginamic.webmvc01;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Enrichit la config du ContextSpring et donne le droit de modifier la
 * sécurité, avec surcharge de la sécurité par défaut (extends
 * WebSecurityConfigurer)
 * 
 * @author Christian
 *
 */
@Configuration
@EnableWebSecurity
public class ConfigSecurity extends WebSecurityConfigurerAdapter {

	/**
	 * http.csrf().disable().authorizeRequests().anyRequest().permitAll() désactive toute la sécurité.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		/**
		 * Désactivation totale de Spring Boot Security
		 */
		http.csrf().disable().authorizeRequests().anyRequest().permitAll();
		/**
		 * Site inacessible, tout est vérouillé.
		 */
//		http.csrf().disable().authorizeRequests().anyRequest().authenticated();
		
		/**
		 * httpBasic() -> API Rest avec leur propre sécurité
		 */
//		http.csrf().disable()
//        .formLogin().loginProcessingUrl("/login").and()
//        .logout().logoutUrl("/logout").invalidateHttpSession(true).and()
//        .authorizeRequests()
//        .antMatchers("/login").permitAll()
//        .antMatchers("/logout").permitAll()
//        .anyRequest().authenticated().and().httpBasic();
	}
}
