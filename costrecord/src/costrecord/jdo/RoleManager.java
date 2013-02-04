/**
 * This source code belongs to Moon Zang, the author. To use it for
 * commercial/business purpose, please contact DeepNightTwo@gmail.com
 * 
 * @author Moon Zang
 * 
 */

package costrecord.jdo;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;

public class RoleManager {
    // private static final Logger log = Logger.getLogger(RoleManager.class
    // .getName());

    public static boolean addRole(Long houseID, String roleName) {
        CostRecordRole role = new CostRecordRole();
        role.setUserName(roleName);
        role.setHouseID(houseID);
        PersistenceManager pm = PMF.get().getPersistenceManager();
        try {
            pm.makePersistent(role);
        } finally {
            pm.close();
        }
        return true;
    }

    public static boolean disableRole(Long roleID) {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        CostRecordRole role = pm.getObjectById(CostRecordRole.class, roleID);
        try {
            role.setActive(false);
            pm.makePersistent(role);
        } finally {
            pm.close();
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    public static List<CostRecordRole> getAllRoles(Long houseID,
            boolean activeOnly) {
        PersistenceManager pm = PMF.get().getPersistenceManager();

        String query = "select from " + CostRecordRole.class.getName();
        if (activeOnly) {
            query += " where isActive == true && houseID == " + houseID;
        }
        List<CostRecordRole> roles = (List<CostRecordRole>) pm.newQuery(query)
                .execute();
        if (roles == null) {
            roles = new ArrayList<CostRecordRole>();
        }
        return roles;
    }

    public static CostRecordRole getRole(Long roleID) {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        CostRecordRole role = pm.getObjectById(CostRecordRole.class, roleID);
        return role;
    }

}
