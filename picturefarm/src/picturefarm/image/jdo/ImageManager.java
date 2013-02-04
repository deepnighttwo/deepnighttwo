/**
 * This source code belongs to Moon Zang, the author. To use it for
 * commercial/business purpose, please contact DeepNightTwo@gmail.com
 * 
 * @author Moon Zang
 * 
 */
package picturefarm.image.jdo;

import java.util.List;

import javax.jdo.PersistenceManager;

import picturefarm.image.util.FormImage;

public class ImageManager {

    public static boolean storeImageGroup(List<FormImage> images,
            String groupName) {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        try {
            int counter = 0;
            for (FormImage image : images) {
                StoredImage imageStored = new StoredImage();
                imageStored.setPieceCount(1);
                int len = image.getEnd() - image.getStart();
                if (len == 0) {
                    continue;
                }
                counter++;
            }
            ImageGroup group = new ImageGroup();
            group.setGourpName(groupName);
            group.setGroupCount(counter);
            pm.makePersistent(group);
            counter = 0;
            for (FormImage image : images) {
                StoredImage imageStored = new StoredImage();
                imageStored.setImageType(image.getContentType());
                imageStored.setPieceCount(1);
                int len = image.getEnd() - image.getStart();
                if (len == 0) {
                    continue;
                }
                byte[] imageData = new byte[image.getEnd() - image.getStart()];
                System.arraycopy(image.getData(), image.getStart(), imageData,
                        0, imageData.length);
                StoredImagePiece imagePiece = new StoredImagePiece(imageData);

                imageStored.setGroupID(group.getId());
                imageStored.setSeqID(counter);
                // TODO: set image type and image name here
                pm.makePersistent(imageStored);
                imageStored.getId();
                imagePiece.setImageID(imageStored.getId());
                pm.makePersistent(imagePiece);

                System.out.print(image.getAttributeName() + "\t");
                System.out.print(image.getFileName() + "\t");
                System.out.println(image.getEnd() - image.getStart());
                counter++;
            }

        } finally {
            pm.close();
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public static String[] getAllImageNames() {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        String query = "select from " + ImageGroup.class.getName();

        List<ImageGroup> imageGroup = (List<ImageGroup>) pm.newQuery(query)
                .execute();
        if (imageGroup == null) {
            return new String[0];
        }
        int len = imageGroup.size();
        String[] groupNames = new String[len];
        for (int i = 0; i < len; i++) {
            groupNames[i] = imageGroup.get(i).getGourpName();
        }
        return groupNames;
    }

    @SuppressWarnings("unchecked")
    public static byte[][] getImage(String imageGroupName,
            StringBuffer contentType) {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        String query = "select from " + ImageGroup.class.getName()
                + " where gourpName == lastNameParam "
                + " parameters String lastNameParam";

        List<ImageGroup> imageGroup = (List<ImageGroup>) pm.newQuery(query)
                .execute(imageGroupName);
        if (imageGroup == null) {
            System.out.println("Image Group Name not found: " + imageGroupName);
        }
        ImageGroup group = imageGroup.get(0);
        Long groupID = group.getId();
        int count = group.getGroupCount();

        int selected = (int) (Math.random() * count);

        query = "select from " + StoredImage.class.getName()
                + " where groupID == " + groupID + " && seqID == " + selected;

        List<StoredImage> images = (List<StoredImage>) pm.newQuery(query)
                .execute();

        if (images == null) {
            System.out.println("Image Group Name not found: " + imageGroupName);
        }

        StoredImage image = images.get(0);
        Long imageID = image.getId();
        contentType.append(image.getImageType());

        query = "select from " + StoredImagePiece.class.getName()
                + " where imageID == " + imageID;

        List<StoredImagePiece> imagePiecesList = (List<StoredImagePiece>) pm
                .newQuery(query).execute();

        if (imagePiecesList == null) {
            System.out.println("Image Group Name not found: " + imageGroupName);
        }

        byte[][] imagePiece = new byte[imagePiecesList.size()][];

        for (int i = 0; i < imagePiece.length; i++) {
            imagePiece[i] = imagePiecesList.get(0).getImage().getBytes();
        }
        return imagePiece;
    }
}
