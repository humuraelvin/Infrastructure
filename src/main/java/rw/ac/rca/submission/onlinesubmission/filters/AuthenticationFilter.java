package rw.ac.rca.submission.onlinesubmission.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    public static final List<String> PUBLIC_PATHS = Arrays.asList(
            "/login",
            "/register",
            "/assets",
            "/css",
            "/js",
            "/images"
    );

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        if (isPublicPath(path)){
            chain.doFilter(request, response);
            return;
        }

        if (session == null || session.getAttribute("user") == null) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            return;
        }

        String userRole = (String) session.getAttribute("userRole");
        if (!hasAccess(path, userRole)){
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
            return;
        }

        chain.doFilter(request, response);

    }

    private boolean isPublicPath(String path) {
        return PUBLIC_PATHS.stream().anyMatch(path::startsWith) ||
                path.equals("/") ||
                path.endsWith(".css") ||
                path.endsWith(".js");
    }

    private boolean hasAccess(String path, String role) {
        if (role == null) return false;

        if (path.startsWith("/teacher") && !role.equals("TEACHER")) {
            return false;
        }

        if (path.startsWith("/student") && !role.equals("STUDENT")) {
            return false;
        }

        return true;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}

}
