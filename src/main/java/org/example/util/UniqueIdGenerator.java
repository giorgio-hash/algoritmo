package util;

/**
 * Classe di utilità per generare codice Id univoci.
 */
public class UniqueIdGenerator {

    /**
     * codice id generato.
     */
    private static int generatedId;

    /**
     * istanza della classe.
     */
    private static UniqueIdGenerator instance;

    /**
     * Costruttore privato che inizializza il conto degli id a 1.
     */
    private UniqueIdGenerator(){
        generatedId=1;
    }

    /**
     * Crea un'istanza della classe UniqueIdGenerator in modo da poterne creare solamente una univoca.
     * Richiama il costruttore privato (Pattern Singleton class).
     *
     * @return istanza univoca della classe UniqueIdGenerator.
     */
    public static UniqueIdGenerator getInstance(){
        if(instance == null)
            instance = new UniqueIdGenerator();
        return instance;
    }

    /**
     * restituisce l'ultimo id generato
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
