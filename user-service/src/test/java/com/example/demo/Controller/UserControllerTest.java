//package com.example.demo.Controller;
//
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.security.authentication.AuthenticationManager;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.provisioning.JdbcUserDetailsManager;
//import org.springframework.test.web.servlet.MockMvc;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockHttpServletRequest;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//
//import com.example.demo.Dao.Userdao;
//import com.example.demo.Model.Users;
//import com.example.demo.Util.Util;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.ObjectWriter;
//import com.fasterxml.jackson.databind.SerializationFeature;
//
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class UserControllerTest {
//
//	
//	@Autowired
//    private MockMvc mockMvc;
//	
//	@MockBean
//	public Userdao dao;
//
//	 @Autowired
//	public AuthenticationManager authenticationManager;
//	 
//	 @Autowired
//	public JdbcUserDetailsManager jdbcUserDetailsManager;
//
//	@Autowired
//	public BCryptPasswordEncoder passwordEncoder;
//	 
//	 @Autowired
//	public Util util;
//
//	
//	
//	@Test
//	void testRegisterUser() throws Exception {
//		Users Usr = new Users();
//		List<GrantedAuthority> authorities = new ArrayList<>();
//		authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
//		String encodededPassword = passwordEncoder.encode("ABC");
//		User user = new User("abc@gmail.com", encodededPassword, authorities);
//		jdbcUserDetailsManager.createUser(user);
//		Users usr = dao.findByUsername(user.getUsername()); 
//		usr.setContactNo("12345");
//		usr.setFirstname("abc");
//		usr.setLastname("abcd");
//		usr.setPan("12345AAAA1");
//		
//		Mockito.when(dao.saveAndFlush(usr)).
//        thenReturn(Usr);
//		
//		ObjectMapper mapper = new ObjectMapper();
//        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
//        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
//        String requestJson=ow.writeValueAsString(Usr);
//        
//        mockMvc.perform(post("/Signup")
//                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
//                .content(requestJson))
//                .andDo(print());
//	}
//
//	@Test
//	void testAllrecords() throws Exception {
//		Users users = new Users();
//		users.setUsername("abc");
//		users.setPassword("asdfg");
//		users.setEnabled(true);
//		users.setFirstname("asdfg");
//		users.setLastname("1asdf");
//		users.setPan("asdfghjkl");
//		users.setContactNo("12345");
//		
//
//		MockHttpServletRequest request = new MockHttpServletRequest();
//        request.addHeader("Authentication","eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJVU0VSX1NIT1BQSU5HIiwiU0VTU0lPTl9QQVNTIjoibWFudUAxMjMiLCJTRVNTSU9OX0VNQUlMIjoibWFudTExQGdtYWlsLmNvbSIsIlNFU1NJT05fVFlQRSI6ImFkbWluIiwiZXhwIjoxNTg5NDU1OTQxLCJpYXQiOjE1ODkzNjk1NDF9.Z_1BPh3XyTateJu88qzyuib10bISFl9ViD_iElTDGjlg5sG7g_30-3wnuPUu_F4KP65T-OqwjBmcL0S43ZX8XA");
//
//				
//        Mockito.when( dao.findAll())
//        .thenReturn((List<Users>) users);
//        ObjectMapper mapper = new ObjectMapper();
//		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
//		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
//		String requestJson=ow.writeValueAsString(users);
//
//		System.out.println(requestJson);
//
//		mockMvc.perform(post("/AllUserInfomation")
//				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
//				.content(requestJson))
//        	.andDo(print());
//	
//		
//	}
//
//}
