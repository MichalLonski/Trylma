package com.studia.Zasady;

/**
 * Fabryka odpowiednich zasad gry w zależności od wybranego typu gry.
 * Używa wzorca projektowego fabryki, aby stworzyć odpowiednią klasę zasad gry.
 */
public class FabrykaZasad {

    /**
     * Tworzy odpowiednie zasady gry na podstawie wybranego typu gry.
     *
     * @param typ Typ gry, który ma zostać użyty do stworzenia zasad.
     * @param liczbaGraczy Liczba graczy, która będzie miała wpływ na zasady.
     * @return Obiekt rozszerzający abstrakcyjną klasę ZasadyGry, zawierający zasady odpowiednie dla wybranego typu gry.
     * @throws IllegalArgumentException Jeśli typ gry jest nieznany.
     */
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
