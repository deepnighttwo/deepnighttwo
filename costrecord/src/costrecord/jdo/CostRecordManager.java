/**
 * This source code belongs to Moon Zang, the author. To use it for
 * commercial/business purpose, please contact DeepNightTwo@gmail.com
 * 
 * @author Moon Zang
 * 
 */

package costrecord.jdo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.jdo.PersistenceManager;

public class CostRecordManager {
    // private static final Logger log =
    // Logger.getLogger(CostRecordManager.class
    // .getName());

    public static boolean addCostRecord(Long houseID, Long roleID,
            CostRoleWeight[] weights, double money, String memo) {
        Cost cost = new Cost();

        cost.setPayerID(roleID);
        cost.setMoneyAmount(money);
        cost.setMemo(memo);
        cost.setDate(new Date());
        cost.setHouseID(houseID);

        PersistenceManager pm = PMF.get().getPersistenceManager();
        try {
            pm.makePersistent(cost);
            if (weights != null) {
                for (CostRoleWeight weight : weights) {
                    weight.setCostID(cost.getId());
                    pm.makePersistent(weight);
                }
            }
        } finally {
            pm.close();
        }
        return true;
    }

    public static boolean disableRecord(Long recordID) {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        Cost cost = pm.getObjectById(Cost.class, recordID);
        try {
            cost.setActive(false);
            cost.setOngoing(false);
            pm.makePersistent(cost);
        } finally {
            pm.close();
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    public static boolean archiveRecord(Long houseID) {
        PersistenceManager pm = PMF.get().getPersistenceManager();

        String query = "select from " + Cost.class.getName()
                + " where isOngoing == true && isActive == true && houseID == "
                + houseID;

        List<Cost> costs = (List<Cost>) pm.newQuery(query).execute();
        if (costs == null) {
            return true;
        }
        Date date = new Date();
        try {
            for (Cost cost : costs) {
                cost.setArchivedDate(date);
                cost.setOngoing(false);
            }
        } finally {
            pm.close();
        }

        return true;
    }

    /**
     * 
     * @param isOngoing
     *            for history if value is false
     * @param isActive
     *            should always be true
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List<Cost> getAllCost(Long houseID, boolean isOngoing,
            boolean isActive) {
        PersistenceManager pm = PMF.get().getPersistenceManager();

        String query = "select from " + Cost.class.getName()
                + " where isOngoing == " + isOngoing + " && isActive == "
                + isActive + " && houseID == " + houseID;

        List<Cost> costs = (List<Cost>) pm.newQuery(query).execute();
        if (costs == null) {
            costs = new ArrayList<Cost>();
        }
        return costs;
    }

    public static List<Cost> getCostWithRole(Long houseID) {
        List<Cost> costs = CostRecordManager.getAllCost(houseID, true, true);
        for (Cost cost : costs) {
            cost.setRole(RoleManager.getRole(cost.getPayerID()));
        }

        return costs;
    }

    @SuppressWarnings("unchecked")
    public static List<Cost> getCostPaiedByRole(Long houseID, Long roleID) {
        PersistenceManager pm = PMF.get().getPersistenceManager();

        String query = "select from " + Cost.class.getName()
                + " where isOngoing == true && isActive == true && houseID == "
                + houseID + " && payerID == " + roleID;

        List<Cost> costs = (List<Cost>) pm.newQuery(query).execute();
        if (costs == null) {
            costs = new ArrayList<Cost>();
        }
        return costs;
    }

    @SuppressWarnings("unchecked")
    public static CostRoleWeight[] getWeigtsForCost(Long costID) {
        PersistenceManager pm = PMF.get().getPersistenceManager();

        String query = "select from " + CostRoleWeight.class.getName()
                + " where costID == " + costID;

        List<CostRoleWeight> weightsForCost = (List<CostRoleWeight>) pm
                .newQuery(query).execute();
        if (weightsForCost == null) {
            return null;
        }
        CostRoleWeight[] weightsForCostArr = new CostRoleWeight[weightsForCost
                .size()];
        return weightsForCost.toArray(weightsForCostArr);
    }

    public static List<String> countCost(List<Cost> costs,
            List<CostRecordRole> roles) {

        class EveryonePaied {
            public CostRecordRole role;
            public double money;

            public EveryonePaied(CostRecordRole role, double money) {
                this.role = role;
                this.money = money;
            }
        }

        int roleCount = roles.size();

        List<String> costAnalysis = new ArrayList<String>();
        List<EveryonePaied> lessPaied = new ArrayList<EveryonePaied>();
        List<EveryonePaied> morePaied = new ArrayList<EveryonePaied>();
        HashMap<Long, Double> paied = new HashMap<Long, Double>();
        HashMap<Long, Double> shouldPay = new HashMap<Long, Double>();
        CostRoleWeight[] weightsForAll = new CostRoleWeight[roleCount];

        for (int i = 0; i < roleCount; i++) {
            CostRecordRole role = roles.get(i);
            paied.put(role.getId(), 0.0);
            shouldPay.put(role.getId(), 0.0);
            weightsForAll[i] = new CostRoleWeight();
            weightsForAll[i].setRoleID(role.getId());
            weightsForAll[i].setWeight(1);
        }

        for (Cost cost : costs) {
            Long roleID = cost.getRole().getId();
            double money = cost.getMoneyAmount();

            // add to paid map
            Double value = paied.get(roleID);
            if (value == null) {
                paied.put(roleID, 0.0);
                value = 0.0;
            }
            paied.put(roleID, value + money);

            // add to each shouldPay map
            CostRoleWeight[] weights = getWeigtsForCost(cost.getId());
            if (weights == null || weights.length == 0) {
                weights = weightsForAll;
            }
            double totalWeight = 0;
            for (CostRoleWeight weight : weights) {
                totalWeight += weight.getWeight();
            }
            if (totalWeight == 0) {
                weights = weightsForAll;
            }
            for (CostRoleWeight weight : weights) {
                double shouldPayCost = weight.getWeight() * money / totalWeight;
                Double shouldPayTotalValue = shouldPay.get(weight.getRoleID());
                if (shouldPayTotalValue == null) {
                    shouldPay.put(weight.getRoleID(), 0.0);
                    shouldPayTotalValue = 0.0;
                }
                shouldPay.put(weight.getRoleID(), shouldPayTotalValue
                        + shouldPayCost);
            }
        }

        for (CostRecordRole role : roles) {
            double shouldPayMoney = shouldPay.get(role.getId());
            double paidMoney = paied.get(role.getId());
            double dist = paidMoney - shouldPayMoney;
            if (dist > 0) {
                morePaied.add(new EveryonePaied(role, dist));
            } else {
                lessPaied.add(new EveryonePaied(role, Math.abs(dist)));
            }
        }

        int current = 0;
        int max = morePaied.size();
        boolean stillToPay = true;

        for (EveryonePaied ep : lessPaied) {
            double moneyToPay = ep.money;
            for (int i = current; (i < max) && stillToPay; i++) {
                EveryonePaied more = morePaied.get(i);
                if (moneyToPay > more.money) {
                    current++;
                    moneyToPay -= more.money;
                    costAnalysis.add(ep.role.getUserName() + " should pay "
                            + formatMoney(more.money) + " to "
                            + more.role.getUserName());
                } else {
                    more.money -= moneyToPay;
                    costAnalysis.add(ep.role.getUserName() + " should pay "
                            + formatMoney(moneyToPay) + " to "
                            + more.role.getUserName());
                }
            }
        }

        return costAnalysis;
    }

    private static double formatMoney(double money) {
        money += 0.005;
        return ((int) (money * 100)) / 100.0;
    }

}
