package costrecord;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import costrecord.jdo.CostRecordManager;
import costrecord.jdo.CostRecordUtils;
import costrecord.jdo.CostRoleWeight;
import costrecord.jdo.HouseManager;

@SuppressWarnings("serial")
public class CostrecordServlet extends HttpServlet {
    // private static final Logger log = Logger
    // .getLogger(RoleOperationServlet.class.getName());
    private Long houseID;

    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/plain");
        houseID = HouseManager.checkLogin(req, resp);
        if ((new Long("-1")).equals(houseID)) {
            return;
        }

        StringBuffer msg = new StringBuffer();
        try {
            String operation = req.getParameter("operation");
            if ("addcostforall".equalsIgnoreCase(operation)) {
                msg.append(addCostForAll(req, resp));
            } else if ("addcostforusers".equalsIgnoreCase(operation)) {
                msg.append(addCostForUsers(req, resp));
            } else if ("disableCost".equalsIgnoreCase(operation)) {
                msg.append(disableCost(req, resp));
            } else if ("archive".equalsIgnoreCase(operation)) {
                msg.append(archiveCost(req, resp));
            }
        } catch (Exception ex) {
            msg.append("Exception occured:\n");
            msg.append(ex.getMessage());
        } finally {
            req.getSession().setAttribute("message", msg.toString());
            resp.sendRedirect("/costrecord.jsp");
        }
    }

    private String addCostForAll(HttpServletRequest req,
            HttpServletResponse resp) {
        String roleID = req.getParameter("roleID");
        String money = req.getParameter("money");
        String memo = req.getParameter("memo");
        if (CostRecordUtils.checkStringValue(roleID)
                && CostRecordUtils.checkDoubleValue(money)) {
            CostRecordManager.addCostRecord(houseID, Long.valueOf(roleID),
                    null, Double.valueOf(money), memo);
            return "Cost about \"" + money + "\" created.";
        } else {
            return "Invalid money. " + "Please make sure the text could be "
                    + "converted to a number.";
        }
    }

    private String addCostForUsers(HttpServletRequest req,
            HttpServletResponse resp) {
        String roleID = req.getParameter("roleID");
        String money = req.getParameter("money");
        String memo = req.getParameter("memo");
        String[] weights = req.getParameterValues("userWeight");
        String[] userIDs = req.getParameterValues("userIDs");
        if (weights.length != userIDs.length) {
            return "Internal Error : User and weight does not match!";
        }
        List<CostRoleWeight> validatedWeight = new ArrayList<CostRoleWeight>();
        for (int i = 0; i < weights.length; i++) {
            if (!CostRecordUtils.checkDoubleValue(weights[i])) {
                continue;
            }
            double weightValue = Double.valueOf(weights[i]);
            if (weightValue < 0) {
                return "Invalid weight value. "
                        + "Weight value must bigger than zero :" + weightValue;
            }
            CostRoleWeight weight = new CostRoleWeight();
            weight.setRoleID(Long.valueOf(userIDs[i]));
            weight.setWeight(weightValue);
            validatedWeight.add(weight);
        }
        CostRoleWeight[] weightsArr = new CostRoleWeight[validatedWeight.size()];
        validatedWeight.toArray(weightsArr);

        if (CostRecordUtils.checkStringValue(roleID)
                && CostRecordUtils.checkDoubleValue(money)) {
            CostRecordManager.addCostRecord(houseID, Long.valueOf(roleID),
                    weightsArr, Double.valueOf(money), memo);
            return "Cost about \"" + money + "\" created for selected users.";
        } else {
            return "Invalid money. " + "Please make sure the text could be "
                    + "converted to a number.";
        }
    }

    private String disableCost(HttpServletRequest req, HttpServletResponse resp) {
        String costid = req.getParameter("costid");
        Long id = Long.valueOf(costid);
        CostRecordManager.disableRecord(id);
        return "Cost \"" + costid + "\" disabled.";
    }

    private String archiveCost(HttpServletRequest req, HttpServletResponse resp) {
        CostRecordManager.archiveRecord(houseID);
        return "Cost records archived.";
    }

}
