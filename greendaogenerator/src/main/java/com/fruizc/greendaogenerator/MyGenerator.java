package com.fruizc.greendaogenerator;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class MyGenerator {

    public static void main(String[] args) {
        Schema schema = new Schema(1, "com.fruizc.sites.modeldb");
        schema.enableKeepSectionsByDefault();

        addTables(schema);

        try {
            new DaoGenerator().generateAll(schema,"./app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addTables(final Schema schema) {

        addUserEntities(schema);
        addPlaceEntities(schema);
        addPlaceUserEntities(schema);
    }

    // This is use to describe the colums of your table
    private static Entity addUserEntities(final Schema schema) {

        Entity user = schema.addEntity("User");
        user.addStringProperty("name");
        user.addStringProperty("username");
        user.addStringProperty("useremail");
        user.addStringProperty("password");
        user.addStringProperty("latitud");
        user.addStringProperty("longitud");
        return user;


    }

    // This is use to describe the colums of your table
    private static Entity addPlaceEntities(final Schema schema) {

        Entity place = schema.addEntity("Place");
        place.addLongProperty("idPlace").primaryKey().autoincrement();
        place.addStringProperty("placeName");
        place.addStringProperty("descPlace");
        place.addStringProperty("createdAt");
        place.addStringProperty("picture");
        return place;

    }

    // This is use to describe the colums of your table
    private static Entity addPlaceUserEntities(final Schema schema) {

        Entity placeuser = schema.addEntity("PlaceUser");
        placeuser.addLongProperty("idPlace");
        placeuser.addStringProperty("useremail");
        return placeuser;

    }


}
