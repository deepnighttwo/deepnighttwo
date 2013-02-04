package costrecord;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import costrecord.jdo.Cost;
import costrecord.jdo.CostRecordManager;
import costrecord.jdo.CostRecordUtils;
import costrecord.jdo.HouseManager;
import costrecord.jdo.RoleManager;

@SuppressWarnings("serial")
public class RoleOperationServlet extends HttpServlet {

    // private static final Logger log = Logger
    // .getLogger(RoleOperationServlet.class.getName());

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/plain");
        Long houseID = HouseManager.checkLogin(req, resp);
        if ((new Long("-1")).equals(houseID)) {
            return;
        }
        StringBuffer msg = new StringBuffer();
        try {
            String roleName = req.getParameter("roleName");
            String operation = req.getParameter("operation");
            if ("addrole".equalsIgnoreCase(operation)) {
                if (CostRecordUtils.checkStringValue(roleName)) {
                    RoleManager.addRole(houseID, roleName);
                    msg.append("Role \"" + roleName + "\" created.");
                } else {
                    msg.append("Invalid role name.");
                }
            } else if ("disablerole".equalsIgnoreCase(operation)) {
                String roleID = req.getParameter("roleID");
                if (CostRecordUtils.checkLongValue(roleID)) {
                    Long id = Long.valueOf(roleID);
                    List<Cost> costPaiedByThisRole = CostRecordManager
                            .getCostPaiedByRole(houseID, id);
                    if (costPaiedByThisRole != null
                            && costPaiedByThisRole.size() > 0) {
                        msg.append("Unable to disable role \"" + roleName
                                + "\". There are " + costPaiedByThisRole.size()
                                + " cost record about this role. "
                                + "Please handle them first.");
                    } else {
                        RoleManager.disableRole(id);
                        msg.append("Role \"" + roleName + "\" disabled.");
                    }
                } else {
                    msg.append("Invalid role id.");

                }
            }
        } catch (Exception ex) {
            msg.append("Exception occured:\n");
            msg.append(ex.getMessage());
        } finally {
            req.getSession().setAttribute("message", msg.toString());
            resp.sendRedirect("/role.jsp");
        }
    }
}
