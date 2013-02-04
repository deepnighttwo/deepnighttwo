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

import com.google.appengine.api.datastore.Blob;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class StoredImagePiece {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;

    @Persistent
    private Long imageID;

    @Persistent
    private int pieceOrder;

    @Persistent
    private Blob image;

    public StoredImagePiece(byte[] imageData) {
        image = new Blob(imageData);
        image.getBytes();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPieceOrder() {
        return pieceOrder;
    }

    public void setPieceOrder(int pieceOrder) {
        this.pieceOrder = pieceOrder;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    public Long getImageID() {
        return imageID;
    }

    public void setImageID(Long imageID) {
        this.imageID = imageID;
    }

}
