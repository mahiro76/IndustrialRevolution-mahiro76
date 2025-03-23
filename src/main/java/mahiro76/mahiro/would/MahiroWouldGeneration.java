package mahiro76.mahiro.would;

import mahiro76.mahiro.Mahiro;

public class MahiroWouldGeneration {

    public static void initialization(){
        MahiroFlowerGeneration.generateFlowers();
    }

    public static void registerMahiroWouldGeneration() {
        MahiroWouldGeneration.initialization();
        Mahiro.LOGGER.debug("register WouldGeneration for" + Mahiro.MOD_ID);
    }
}
