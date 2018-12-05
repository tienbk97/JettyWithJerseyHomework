import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class LogFilter implements javax.servlet.Filter {
    private static Logger logger;
    private static Marker marker;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger = LoggerFactory.getLogger(this.getClass().getSimpleName() );
        marker = MarkerFactory.getMarker("");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {

        if (req instanceof HttpServletRequest) {
            HttpServletRequest request = (HttpServletRequest) req;
            logger.info( marker, String.format("From %s:%d  %s %s",
                    request.getRemoteAddr(), request.getRemotePort(), request.getMethod(), request.getRequestURI()
                    )
            );
        }
        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
    }

}