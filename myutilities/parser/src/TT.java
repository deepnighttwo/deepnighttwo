
public class TT {

    /**
     * @param args
     */
    public static void main(String[] args) {
        String[] data = {
                "7501000", "4", "7501500", "4", "7502000", "2", "7502500", "2", "7503000", "2", "7503500", "4",
                "7504000", "2", "7504500", "2", "7505000", "2", "7505500", "4", "7506500", "2"
        };

        String[][] realms = {
            {
                    "USAmazon", "1", "2"
            }
        };

        String sql = "insert into workflow_db.oih_config values(uuid(),'%s',75,%s,'%s',null,null,'%s','MINIMUM_AVAILABLE_STOCK_ON_HAND','NUMBER');";

        for (String[] realm : realms) {
            String rm = realm[0].trim();
            for (int i = 1; i < realm.length; i++) {
                String iog = realm[i].trim();
                for (int j = 0; j < data.length; j += 2) {
                    String cate = data[j].trim();
                    String moh = data[j + 1].trim();
                    System.out.println(String.format(sql, rm, iog, cate, moh));
                }
            }

        }
    }
}
