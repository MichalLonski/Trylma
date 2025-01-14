package com.studia.Zasady;

public class FabrykaZasad {
    public static ZasadyGry stworzZasadyGry(TypGry typ, int liczbaGraczy) {
        switch (typ) {
            case STANDARDOWA:
                return new StandardoweZasadyGry(liczbaGraczy);
            case FAST_PACED:
                return new FastPacedZasadyGry(liczbaGraczy);
            case CAPTURE:
                return new CaptureZasadyGry(liczbaGraczy);
            default:
                throw new IllegalArgumentException("Nieznany typ");
        }

    }
}
