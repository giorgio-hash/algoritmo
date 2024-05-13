package util;

import entities.IngredientePrincipale;
import entities.OrdinePQ;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

public class GeneraOrdine {

    /**
     * Genera un ordinePQ dato un indice
     *
     * @param i indice dell'ordine da generare
     * @return OrdinePQ
     */
    public static OrdinePQ genOrdine(int i){
        switch (i){
            case 1:
                return genOrdine1();
            case 2:
                return genOrdine2();
            case 3:
                return genOrdine3();
            case 4:
                return genOrdine4();
            case 5:
                return genOrdine5();
            case 6:
                return genOrdine6();
            case 7:
                return genOrdine7();
            case 8:
                return genOrdine8();
            case 9:
                return genOrdine9();
            default:
                return genOrdineDef();
        }
    }

    private static OrdinePQ genOrdineDef() {
        return new OrdinePQ(1524,
                false,
                IngredientePrincipale.CARNE,
                Duration.ofMinutes(0).plusSeconds(35),
                1,
                Timestamp.valueOf(LocalDateTime.now()));
    }

    private static OrdinePQ genOrdine1() {
        return new OrdinePQ(3645,
                true,
                IngredientePrincipale.PESCE,
                Duration.ofMinutes(0).plusSeconds(30),
                1,
                Timestamp.valueOf(LocalDateTime.now()));
    }

    private static OrdinePQ genOrdine2() {
        return new OrdinePQ(2154,
                true,
                IngredientePrincipale.CARNE,
                Duration.ofMinutes(0).plusSeconds(30),
                1,
                Timestamp.valueOf(LocalDateTime.now()));
    }

    private static OrdinePQ genOrdine3() {
        return new OrdinePQ(5458,
                true,
                IngredientePrincipale.RISO,
                Duration.ofMinutes(0).plusSeconds(30),
                1,
                Timestamp.valueOf(LocalDateTime.now()));
    }

    private static OrdinePQ genOrdine4() {
        return new  OrdinePQ(1000,
                true,
                IngredientePrincipale.PASTA,
                Duration.ofMinutes(0).plusSeconds(30),
                1,
                Timestamp.valueOf(LocalDateTime.now()));
    }

    private static OrdinePQ genOrdine5() {
        return  new OrdinePQ(5445,
                false,
                IngredientePrincipale.PASTA,
                Duration.ofMinutes(0).plusSeconds(10),
                4,
                Timestamp.valueOf(LocalDateTime.now()));
    }

    private static OrdinePQ genOrdine6() {
        return new OrdinePQ(1054,
                true,
                IngredientePrincipale.RISO,
                Duration.ofMinutes(0).plusSeconds(15),
                1,
                Timestamp.valueOf(LocalDateTime.now()));
    }

    private static OrdinePQ genOrdine7() {
        return new  OrdinePQ(1212,
                false,
                IngredientePrincipale.PESCE,
                Duration.ofMinutes(0).plusSeconds(45),
                3,
                Timestamp.valueOf(LocalDateTime.now()));
    }

    private static OrdinePQ genOrdine8() {
        return  new OrdinePQ(8888,
                true,
                IngredientePrincipale.PESCE,
                Duration.ofMinutes(1).plusSeconds(0),
                6,
                Timestamp.valueOf(LocalDateTime.now()));
    }

    private static OrdinePQ genOrdine9() {
        return  new OrdinePQ(2222,
                false,
                IngredientePrincipale.PESCE,
                Duration.ofMinutes(0).plusSeconds(55),
                5,
                Timestamp.valueOf(LocalDateTime.now()));
    }

    /**
     * Genera un ordinePQ con parametri casuali
     *
     * @return nuovo oggetto OrdinePQ
     */
    public static OrdinePQ genOrdineRandom(){
        Random random = new Random();
        int id = random.nextInt(1000, 10000);
        int idComanda = random.nextInt(1000,10000);
        String idPiatto =  "P" + (random.nextInt(1000, 10000));
        Timestamp tOrdinazione = Timestamp.valueOf(LocalDateTime.now());
        boolean urgenzaCliente = random.nextBoolean();
        IngredientePrincipale ingredientePrincipale = getRandomEnum(IngredientePrincipale.class);
        Duration tp = Duration.ofMinutes(0).plusSeconds(random.nextInt(10,60));
        int numOrdineEffettuato = random.nextInt(1,6);
        return new OrdinePQ(
                id,
                idComanda,
                idPiatto,
                1,
                tOrdinazione,
                urgenzaCliente,
                ingredientePrincipale,
                null,
                tp,
                numOrdineEffettuato);
    }

    private static <T extends Enum<?>> T getRandomEnum(Class<T> enumeration) {
        Random random = new Random();
        int index = random.nextInt(enumeration.getEnumConstants().length);
        return enumeration.getEnumConstants()[index];
    }


}
