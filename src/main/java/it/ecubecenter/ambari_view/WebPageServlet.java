package it.ecubecenter.ambari_view;

import org.apache.ambari.view.ViewContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by gaido on 12/09/2016.
 */
public class WebPageServlet extends HttpServlet {

    private final static String GOOGLE_KEY_PROPERTY = "google.key";
    private ViewContext viewContext;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        ServletContext context = config.getServletContext();
        viewContext = (ViewContext) context.getAttribute(ViewContext.CONTEXT_ATTRIBUTE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("google_key", viewContext.getProperties().get(GOOGLE_KEY_PROPERTY));
        req.getRequestDispatcher("WEB-INF/index.jsp").forward(req, resp);
    }
}
