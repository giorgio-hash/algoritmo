package util;


import java.util.Random;

public class UniqueIdGenerator {

    private static int generatedId;

    private static UniqueIdGenerator instance;

    private UniqueIdGenerator(){
        generatedId=1;
    }

    public static UniqueIdGenerator getInstance(){
        if(instance == null)
            instance = new UniqueIdGenerator();
        return instance;
    }

    /**
     *
     * @return l'ultimo id generato
     */
    public int getGeneratedId(){
        return generatedId;
    }

    /**
     * incrementa l'ID per generarne uno nuovo e unico per la sessione di log
     */
    public void newGeneratedId(){
        generatedId++;
    }


}
