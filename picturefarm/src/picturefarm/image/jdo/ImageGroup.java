/**
 * This source code belongs to Moon Zang, the author. To use it for
 * commercial/business purpose, please contact DeepNightTwo@gmail.com
 * 
 * @author Moon Zang
 * 
 */

package picturefarm.image.jdo;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class ImageGroup {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;

    @Persistent
    private int groupCount;// start from 0

    @Persistent
    private String gourpName;

    public String getGourpName() {
        return gourpName;
    }

    public void setGourpName(String gourpName) {
        this.gourpName = gourpName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getGroupCount() {
        return groupCount;
    }

    public void setGroupCount(int groupCount) {
        this.groupCount = groupCount;
    }

}
