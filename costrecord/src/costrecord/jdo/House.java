/**
 * This source code belongs to Moon Zang, the author. To use it for
 * commercial/business purpose, please contact DeepNightTwo@gmail.com
 * 
 * @author Moon Zang
 * 
 */

package costrecord.jdo;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class House implements java.io.Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = -6176661466898820921L;

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;

    @Persistent
    private String houseName;

    @Persistent
    private String password;

    @Persistent
    private boolean isActive;

    public House() {
        isActive = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String toString() {
        return houseName;
    }

    public int hashCode() {
        if (id == null) {
            return 0;
        }
        return id.intValue();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof House)) {
            return false;
        }
        return (id != null) && id.equals(((House) obj).id);
    }
}
