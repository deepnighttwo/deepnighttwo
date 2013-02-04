/**
 * This source code belongs to Moon Zang, the author. To use it for
 * commercial/business purpose, please contact DeepNightTwo@gmail.com
 * 
 * @author Moon Zang
 * 
 */

package costrecord.jdo;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HouseManager {
    // private static final Logger log = Logger.getLogger(RoleManager.class
    // .getName());

    @SuppressWarnings("unchecked")
    public static boolean addHouse(String houseName, String password) {
        House house = new House();
        house.setHouseName(houseName);
        house.setPassword(password);
        PersistenceManager pm = PMF.get().getPersistenceManager();
        String query = "select from " + House.class.getName()
                + "  where houseName == '" + houseName + "'";
        // make sure house name will not be duplicated
        List<House> houseEx = (List<House>) pm.newQuery(query).execute();
        if (houseEx != null && houseEx.size() > 0) {
            return false;
        }

        try {
            pm.makePersistent(house);
        } finally {
            pm.close();
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    public static Long verifyLogin(String houseName, String password) {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        String query = "select from " + House.class.getName()
                + "  where houseName == '" + houseName + "' && password == '"
                + password + "'";
        List<House> house = (List<House>) pm.newQuery(query).execute();
        if (house != null && house.size() > 0) {
            return house.get(0).getId();
        }
        return (long) -1;
    }

    public static House getHouse(Long houseID) {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        House house = pm.getObjectById(House.class, houseID);
        return house;
    }

    public static Long checkLogin(HttpServletRequest req,
            HttpServletResponse resp) throws ServletException, IOException {
        Long houseid = (Long) req.getSession().getAttribute("houseid");
        if (houseid == null || houseid == -1) {
            req.getSession().setAttribute("message", "Please login first.");
            resp.sendRedirect("/index.jsp");
            return (long) -1;
        }
        return houseid;
    }

}
