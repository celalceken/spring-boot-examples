package cc.ssd.jwt.security.jwt;

import cc.ssd.jwt.service.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {
	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
			String jwt = parseJwt(request);
			if (jwt != null){
				if(jwtUtils.validateJwtToken(jwt)) {
					logger.info("İstek geldi ve doğrulandı. jwt:"+jwt);


						String username = jwtUtils.getUserNameFromJwtToken(jwt);
						//List<String> roller1 = jwtUtils.getRolesFromJwtToken(jwt);
						//logger.info("ilk rol:"+roller1.get(0));

						//List<SimpleGrantedAuthority> roller = new ArrayList<>();

						//roller1.forEach(rol ->logger.info("Roller:"+rol));
						//roller1.forEach(rol ->roller.add(new SimpleGrantedAuthority(rol)));


						//roller.add(new SimpleGrantedAuthority("Administrator"));
						// authentication nesnesi oluşturulurken vt den almak yerine jwt içerisindeki veriler kullanılsın
						//User user1=new User(username,"","","","");
						UserDetails userDetails = userDetailsService.loadUserByUsername(username);
						//logger.info(userDetails.toString());
						// Rol bilgisi her istekte veritabanından alınmaktan ziyade, istekle birlikte gelen token dan alınıyor.- hız
						//UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						//		user1, null, roller);
						UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
								userDetails, null, userDetails.getAuthorities());
						authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

						SecurityContextHolder.getContext().setAuthentication(authentication);
						response.addHeader("jwt","0");
						//logger.info(SecurityContextHolder.getContext().getAuthentication().getPrincipal().);

				}else{
					// yanıt gitmeden önce, istekle gelen jwt süresi dolmuş ise, istemcide uygun mesajı göstermek için "jwt":1
					// bilgisi gönderiliyor.
					response.addHeader("jwt","1");
					//response.setHeader("Cache-Control", "x");
				}
			}else{
				logger.info("İstek geldi, içerisinde jwt bulunmuyor...");
			}


		filterChain.doFilter(request, response);
	}

	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");

		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7, headerAuth.length());
		}

		return null;
	}
}
