# Trylma

Projekt na Technologie Programowania, semestr zimowy, 2024/25

## Autorzy
- Michał Loński
- Jan Brzoska
  
---

## Spis treści
1. [Wersja bez GUI](#wersja-bez-gui)
2. [Wersja z GUI](#wersja-z-gui)
3. [Wersja z botem i DB](#wersja-z-botem-i-db)
---
## Wersja bez GUI

### Funkcjonalności
- Serwer obsługujący wiele klientów, umożliwiający komunikację.
- Tworzenie, wyświetlanie i dołączanie do gier przez klientów.
- Przesyłanie informacji o ruchach wśród graczy danej gry.

### Wymagania
- Java Development Kit (JDK) 21
- Apache Maven

### Instalacja
1. Sklonuj repozytorium:
   ```bash
   git clone https://github.com/MichalLonski/Trylma.git
   ```
2. Skompiluj projekt:
   ```bash
   cd Trylma/Trylma
   mvn compile
   ```

### Uruchamianie
1. Upewnij się, że znajdujesz się w folderze zawierającym plik **pom.xml**.
2. Aby uruchomić serwer:
   ```bash
   mvn exec:java -Dexec.mainClass="com.studia.WlaczSerwer"
   ```
3. Aby uruchomić nowego klienta:
   ```bash
   mvn exec:java -Dexec.mainClass="com.studia.Komunikacja.Klient"
   ```

---

## Wersja z GUI

### Funkcjonalności
- Interfejs graficzny umożliwiający graczom wizualne zarządzanie grą.
- Wizualizacja planszy i ruchów graczy w czasie rzeczywistym.
- Obsługa rozgrywek dla wielu graczy z możliwością dołączania przez GUI.
- 3 tryby rozgrywki

### Wymagania
- Java Development Kit (JDK) 21
- Apache Maven
- JavaFX 21

### Instalacja
1. Sklonuj repozytorium:
   ```bash
   git clone https://github.com/MichalLonski/Trylma.git
   ```
2. Skompiluj projekt:
   ```bash
   cd "Trylma/Trylma - GUI"
   mvn compile
   ```

### Uruchamianie
1. Upewnij się, że znajdujesz się w folderze zawierającym plik **pom.xml**.
2. Aby uruchomić serwer:
   ```bash
   mvn exec:java -Dexec.mainClass="com.studia.WlaczSerwer"
   ```
3. Aby uruchomić nowego klienta:
   ```bash
   mvn javafx:run
   ```
---

## Wersja z botem i DB

### Funkcjonalności
- To samo co w wersji z GUI
- Możliwość dodania i gry z botami w standardowej grze
- Zapis przebiegu gry do bazy danych
- Odtworzenie zapisanej gry z bazy danych

### Wymagania
- Java Development Kit (JDK) 21
- Apache Maven
- JavaFX 21
- MySQL

### Instalacja
1. Sklonuj repozytorium:
   ```bash
   git clone https://github.com/MichalLonski/Trylma.git
   ```
2. Skompiluj projekt:
   ```bash
   cd "Trylma/Trylma - BOT"
   mvn compile
   ```
3. Stwórz bazę danych "trylma"
   ```sql
    CREATE DATABASE trylma;
    USE trylma;
   CREATE TABLE Gry (
    id INT(11) NOT NULL AUTO_INCREMENT,
    typ VARCHAR(50) DEFAULT NULL,
    iloscGraczy INT(11) DEFAULT NULL,
    PRIMARY KEY (id)
    );
   CREATE TABLE Ruchy (
    id INT(11) NOT NULL AUTO_INCREMENT,
    idGry INT(11) DEFAULT NULL,
    ruch VARCHAR(50) DEFAULT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (idGry) REFERENCES Gry(id)
    );
---

Alternatywnie można zaimportować projekt do **Eclipse**
- Uruchom Eclipse i przejdź do menu `File`.
- Wybierz `File` > `Import...`.
- W oknie Importu wybierz `Maven` > `Existing Maven Projects` i kliknij `Next`.
- Kliknij `Browse...` i wskaż katalog, w którym znajduje się sklonowany projekt Maven.
- Zaznacz projekt i kliknij `Finish`.
- Możesz teraz kliknąć prawym przyciskiem myszy na projekcie i wybrać `Run As` > `Java Application`, aby uruchomić aplikację w Eclipse.
