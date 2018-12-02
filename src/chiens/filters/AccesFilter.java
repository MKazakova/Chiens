package chiens.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(urlPatterns="/administrateur/*")
public class AccesFilter implements Filter {
	
	public static final String ACCES_PUBLIC="/user/page_d_accueil.jsp";
	public static final String ADMINISTRATEUR="administrateur";
	
	/*restreint l'accès à tous les jsp du fichier administrateur*/
    public void init(FilterConfig config) throws ServletException{
    }
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
            HttpServletRequest request = (HttpServletRequest)req;
            
            HttpServletResponse response = (HttpServletResponse) resp;
            
            HttpSession session = request.getSession();
            if(session.getAttribute(ADMINISTRATEUR)==null)
            {
            	response.sendRedirect(request.getContextPath()+ACCES_PUBLIC);
            }
            else {
            	chain.doFilter(request,  response);	
            }
            
	}
	
	public void destroy() {
		
	}

}
