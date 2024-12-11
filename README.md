# Trylma
Projekt na Technologie Programowania, semestr zimowy, 2024/25

## Autorzy
- Michał Loński
- Jan Brzoska

## Funkcjonalność (na dzień 11.12.2024)
- Serwer obsługujący wiele klientów, umożliwiający komunikację.
- Tworzenie, wyświetlanie i dołączanie do gier przez klientów.
- Przesyłanie informacji o ruchach wśród graczy danej gry.

## Wymagania
- Java Development Kit (JDK) 21
- Apache Maven

## Instalacja
1. Sklonuj repozytorium:
   ```bash
   git clone https://github.com/MichalLonski/Trylma.git
2. Skompiluj projekt:
    ```bash
    cd Trylma/Trylma; mvn compile

## Uruchamianie
Upewnij się, że znajdujesz się w folderze zawierającym plik **pom.xml**.

- Aby uruchomić serwer:
    ```bash
    mvn exec:java -Dexec.mainClass="com.studia.WlaczSerwer"

- Aby uruchomić nowego klienta:
    ```bash
    mvn exec:java -Dexec.mainClass="com.studia.Komunikacja.Klient"

Alternatywnie można zaimportować projekt do **Eclipse**
- Uruchom Eclipse i przejdź do menu `File`.
- Wybierz `File` > `Import...`.
- W oknie Importu wybierz `Maven` > `Existing Maven Projects` i kliknij `Next`.
- Kliknij `Browse...` i wskaż katalog, w którym znajduje się sklonowany projekt Maven.
- Zaznacz projekt i kliknij `Finish`.
- Możesz teraz kliknąć prawym przyciskiem myszy na projekcie i wybrać `Run As` > `Java Application`, aby uruchomić aplikację w Eclipse.
