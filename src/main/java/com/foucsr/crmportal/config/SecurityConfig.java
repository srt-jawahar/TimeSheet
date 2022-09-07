package com.foucsr.crmportal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.foucsr.crmportal.security.CustomUserDetailsService;
import com.foucsr.crmportal.security.JwtAuthenticationEntryPoint;
import com.foucsr.crmportal.security.JwtAuthenticationFilter;

/**
 * Created by FocusR.
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	CustomUserDetailsService customUserDetailsService;

	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	 private static final String[] AUTH_WHITELIST = {

	            // -- swagger ui
	            "/swagger-resources/**",
	            "/swagger-ui.html",
	            "/v2/api-docs",
	            "/webjars/**"
	    };
	 
	 @Override
	 public void configure(WebSecurity web) throws Exception {
	     web.ignoring().antMatchers(AUTH_WHITELIST);
	 }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers("/", "/favicon.ico", "/**/*.png", "/**/*.gif", "/**/*.svg", "/**/*.jpg", "/**/*.html",
						"/**/*.css", "/**/*.js", "/*.css", "/*.js", "/webjars/**")
				.permitAll().antMatchers("/api/auth/**").permitAll().antMatchers("/index").permitAll()
				.antMatchers("/dashboard").permitAll().antMatchers("/openpo").permitAll().antMatchers("/closedpo")
				.permitAll().antMatchers("/rejectedpo").permitAll().antMatchers("/ack").permitAll()
				.antMatchers("/rescheduledpo").permitAll().antMatchers("/asn").permitAll()
				.antMatchers("/asnshipmentstatus").permitAll().antMatchers("/rtv").permitAll()
				.antMatchers("/buyer").permitAll()
				.antMatchers("/supplierCreation").permitAll()
				.antMatchers("/uploadinvoice").permitAll().antMatchers("/exportdetails").permitAll()
				.antMatchers("/statistics").permitAll().antMatchers("/buyeropenpo").permitAll()
				.antMatchers("/buyerapproval").permitAll().antMatchers("/mastercontrol").permitAll()
				.antMatchers("/createusers").permitAll().antMatchers("/viewerrorlogs").permitAll()
				.antMatchers("/definescreenaccess").permitAll()
				.antMatchers("/rfq").permitAll().antMatchers("/uploads/**").permitAll()
				.antMatchers("/quotation").permitAll()
				.antMatchers("/financeDashboard").permitAll()
				.antMatchers("/shipmentStatus").permitAll()
				.antMatchers("/financeCreditorAnalysis").permitAll()
				.antMatchers("/CashflowForecasting").permitAll()
				.antMatchers("/vendorInvoiceAndDoStatus").permitAll()
				.antMatchers("/register/**").permitAll()
				.antMatchers("/resetPwd/**").permitAll()
				.antMatchers("/invoicedetails").permitAll().antMatchers("/paymentdetails").permitAll()
				.antMatchers("/api/user/checkUsernameAvailability", "/api/user/checkEmailAvailability").permitAll()
				.antMatchers(AUTH_WHITELIST).permitAll()
				
				// TimeSheet and KPI/KRA routes
				.antMatchers("/auth").permitAll()
				.antMatchers("/dashboard/metrics").permitAll()
				.antMatchers("/dashboard/admin").permitAll() 
				.antMatchers("/dashboard/changePassword ").permitAll() 
				.antMatchers("/dashboard/admin/user-management").permitAll()
				.antMatchers("/dashboard/admin/user-management/user-create").permitAll()
				.antMatchers("/dashboard/admin/user-management/user-edit").permitAll()
				.antMatchers("/dashboard/admin/user-management/user-bulkupload").permitAll()
				.antMatchers("/dashboard/timesheet").permitAll()
				.antMatchers("/dashboard/timesheet/timesheet-entry").permitAll()
				.antMatchers("/dashboard/timesheet/add-timesheet").permitAll()
				.antMatchers("/dashboard/timesheet/edit-timesheet").permitAll()
				.antMatchers("/dashboard/timesheet/reports").permitAll()
				.antMatchers("/dashboard/timesheet/approval").permitAll()
				.antMatchers("/dashboard/timesheet/approvalList").permitAll()
				.antMatchers("/dashboard/timesheet/timesheet-manager-leave-approval").permitAll()
				.antMatchers("/dashboard/timesheet/timesheet-manager-leave-application").permitAll()
				.antMatchers("/dashboard/timesheet/timesheet-settings").permitAll()
				.antMatchers("/dashboard/timesheet/timesheet-kpi-kra-self-rating").permitAll()
				.antMatchers("/dashboard/timesheet/timesheet-kpi-kra-manager-rating").permitAll()
				.antMatchers("/dashboard/timesheet/timesheet-kpi-kra-user-rating-list").permitAll()
				.antMatchers("/dashboard/timesheet/timesheet-kpi-kra-config").permitAll()
				.antMatchers("/dashboard/timesheet/timesheet-kpi-kra-config/create").permitAll()
				.antMatchers("/dashboard/timesheet/timesheet-kpi-kra-config/edit").permitAll()
				.antMatchers("/dashboard/timesheet/timesheet-kpi-kra-master").permitAll()
				.antMatchers("/dashboard/timesheet/timesheet-kpi-kra-master/create").permitAll()
				.antMatchers("/dashboard/timesheet/timesheet-kpi-kra-master/edit").permitAll()
				.antMatchers("/dashboard/timesheet/timesheet-user-settings").permitAll()
				.antMatchers("/dashboard/timesheet/timesheet-user-settings/update").permitAll()
				.antMatchers("/auth/login").permitAll()
				.antMatchers("/auth/reset-password").permitAll()
				.antMatchers("/auth/resetpasswordpage/resetPwd/**").permitAll()
				.antMatchers("/auth/resetpasswordpage/**").permitAll()
				.antMatchers("/auth/resetpasswordpage").permitAll()
				.antMatchers("/auth/verify").permitAll()
				
				.antMatchers(HttpMethod.GET, "/api/polls/**", "/api/users/**").permitAll().anyRequest().authenticated();

		// Add our custom JWT security filter
		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

	}
}