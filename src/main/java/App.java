import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlets.DoSFilter;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

public class App {

    public static void main(String[] args){
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        // For log
        final EnumSet<DispatcherType> REQUEST_SCOPE = EnumSet.of(DispatcherType.REQUEST);
        context.addFilter( LogFilter.class, "/*", REQUEST_SCOPE );
        context.addFilter( GetIPFilter.class, "/*", REQUEST_SCOPE );
        // For limit
//        FilterHolder filterHolder = new FilterHolder( DoSFilter.class );
//        filterHolder.setInitParameter("maxRequestsPerSec", "1");  // max requests per second per client
//        filterHolder.setInitParameter("delayMs", "-1");           // millisec to delay excess requests. -1 means reject (for testing)
//        filterHolder.setInitParameter("remotePort", "false");     // true = track connections by remote ip+port
//        filterHolder.setInitParameter("enabled", "true");
//        filterHolder.setInitParameter("trackSessions", "true");
//        context.addFilter( filterHolder, "/*", REQUEST_SCOPE );

        String port = System.getenv("PORT") != null ? System.getenv("PORT") : "8080";

        Server jettyServer = new Server(Integer.parseInt(port));
        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        // Tells the Jersey Servlet which REST service/class to load.
        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.classnames",
                Handler.class.getName());

        try {
            jettyServer.start();
            jettyServer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jettyServer.destroy();
        }

    }
}
