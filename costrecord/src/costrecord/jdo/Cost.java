/**
 * This source code belongs to Moon Zang, the author. To use it for
 * commercial/business purpose, please contact DeepNightTwo@gmail.com
 * 
 * @author Moon Zang
 * 
 */

package costrecord.jdo;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Cost {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;

    @Persistent
    private Long houseID;

    @Persistent
    private Long payerID;

    @Persistent
    private double moneyAmount;

    // mark this false to delete it
    @Persistent
    private boolean isActive;

    // mark this is archived
    @Persistent
    private boolean isOngoing;

    @Persistent
    private String memo;

    @Persistent
    private Date date;

    @Persistent
    private Date archivedDate;

    @NotPersistent
    private CostRecordRole role;

    public Cost() {
        isActive = true;
        isOngoing = true;
    }

    public CostRecordRole getRole() {
        return role;
    }

    public void setRole(CostRecordRole role) {
        this.role = role;
    }

    public Long getHouseID() {
        return houseID;
    }

    public void setHouseID(Long houseID) {
        this.houseID = houseID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPayerID() {
        return payerID;
    }

    public void setPayerID(Long payerID) {
        this.payerID = payerID;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Date getArchivedDate() {
        return archivedDate;
    }

    public void setArchivedDate(Date archivedDate) {
        this.archivedDate = archivedDate;
    }

    public double getMoneyAmount() {
        return moneyAmount;
    }

    public boolean isOngoing() {
        return isOngoing;
    }

    public void setOngoing(boolean isOngoing) {
        this.isOngoing = isOngoing;
    }

    public void setMoneyAmount(double moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
