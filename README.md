# Trylma

Projekt na Technologie Programowania, semestr zimowy, 2024/25

## Autorzy
- Michał Loński
- Jan Brzoska
  
---

## Spis treści
1. [Wersja bez GUI](#wersja-bez-gui)
2. [Wersja z GUI](#wersja-z-gui)
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

Alternatywnie można zaimportować projekt do **Eclipse**
- Uruchom Eclipse i przejdź do menu `File`.
- Wybierz `File` > `Import...`.
- W oknie Importu wybierz `Maven` > `Existing Maven Projects` i kliknij `Next`.
- Kliknij `Browse...` i wskaż katalog, w którym znajduje się sklonowany projekt Maven.
- Zaznacz projekt i kliknij `Finish`.
- Możesz teraz kliknąć prawym przyciskiem myszy na projekcie i wybrać `Run As` > `Java Application`, aby uruchomić aplikację w Eclipse.
