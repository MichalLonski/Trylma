package com.studia.Zasady;

public class FabrykaZasad {
    public static ZasadyGry stworzZasadyGry(TypGry typ, int[] parametry) {
        switch (typ) {
            case STANDARDOWA:
                return new StandardoweZasadyGry(parametry[0]);
            case ZESPOLOWA:
                System.out.println("Proszę nie");
                throw new IllegalArgumentException("Nieobsługiwane");
            case ROZSZERZONA:
                System.out.println("Litości");
                throw new IllegalArgumentException("Nieobsługiwane");
            default:
                throw new IllegalArgumentException("Nieznany typ");
        }

    }
}
