import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class GetIPFilter implements javax.servlet.Filter {
    private static Logger logger;
    private static Marker marker;
    private static HashMap<String, Long> users = new HashMap<String, Long>();
    private static final int limit = 5000;

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
            String ipAddress = request.getHeader("X-FORWARDED-FOR");
            if (ipAddress == null) {
                ipAddress = request.getRemoteAddr();
            }

            Long time = new Date().getTime();
            boolean isBlocked = false;

            if (users.containsKey(ipAddress)) {
                long previousVisit = users.get(ipAddress);
                long distance = time - previousVisit;
                if (distance < limit) {
                    logger.info(marker, String.format("BLOCKED %s WAIT: %d milisecs", ipAddress, distance));
                    RequestDispatcher rd = req.getRequestDispatcher("index.html");
                    rd.include(req, resp);
                    HttpServletResponse response = (HttpServletResponse) resp;
                    Long waitingTime = limit - distance;
                    response.sendError(400, String.format("Your request was blocked. You should wait %f seconds then retry", waitingTime.floatValue() / 1000));
                    isBlocked = true;
                } else {
                    users.put(ipAddress, time);
                }
            } else {
                users.put(ipAddress, time);
            }
            if (!isBlocked) chain.doFilter(req, resp);
        }

    }

    @Override
    public void destroy() {
    }

}