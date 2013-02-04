package costrecord;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import costrecord.jdo.CostRecordUtils;
import costrecord.jdo.HouseManager;

@SuppressWarnings("serial")
public class HouseServlet extends HttpServlet {

    // private static final Logger log = Logger
    // .getLogger(RoleOperationServlet.class.getName());

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/plain");
        StringBuffer msg = new StringBuffer();
        boolean toIndex = false;
        try {
            String operation = req.getParameter("operation");
            String houseName = (String) req.getParameter("houseName");
            String password = (String) req.getParameter("password");
            if (CostRecordUtils.checkStringValue(operation)
                    && CostRecordUtils.checkStringValue(houseName)
                    && CostRecordUtils.checkStringValue(password)) {

                if ("addHouse".equalsIgnoreCase(operation)) {
                    boolean added = HouseManager.addHouse(houseName, password);
                    if (added) {
                        msg.append("House \"" + houseName + "\" created. "
                                + "You can login use this account.");
                        toIndex = true;
                    } else {
                        msg.append("House \"" + houseName + "\" NOT created. "
                                + "Please try to change a house name.");
                        toIndex = false;
                    }
                } else if ("login".equalsIgnoreCase(operation)) {
                    Long houseID = HouseManager
                            .verifyLogin(houseName, password);
                    if (houseID == -1) {
                        msg.append("\"" + houseName + "\" login failed.");
                    } else {
                        req.getSession().setAttribute("houseid", houseID);
                        req.getSession().setAttribute("house",
                                HouseManager.getHouse(houseID));
                        msg.append("\"" + houseName + "\" login success.");
                    }
                    toIndex = true;
                }
            } else {
                msg.append("Invalid house name or password.");
            }
        } catch (Exception ex) {
            msg.append("Exception occured:\n");
            msg.append(ex.getMessage());
        } finally {
            req.getSession().setAttribute("message", msg.toString());
            if (toIndex) {
                resp.sendRedirect("/index.jsp");
            } else {
                resp.sendRedirect("/house.jsp");
            }
        }
    }
}
