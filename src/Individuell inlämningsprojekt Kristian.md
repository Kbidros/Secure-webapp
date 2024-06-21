#K-Secure, Kristians inlämningsuppgift

#Beskrivning av det jag gjort under projektarbetet:

Jag var i grupp 4 och där blev det lite konstigt med kommunikationen och en
viss medlem som inte ville samarbeta som grupp. Därav gjorde jag ett helt eget projekt där jag skrev
all kod själv. 

I och med att jag skrivit all kod så är det inte endast en specifik kodrad jag skrivit som jag berättar om men
jag kommer välja att prata om DTO klassen jag gjorde.

Koden ser ut på följande vis:

```java
    public class RegistrationDto {

    @NotEmpty(message = "Username cannot be empty")
    private String username;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Please provide a valid email address")
    @Pattern(regexp = ".+@(gmail|hotmail|outlook)\\.com$", message = "Email should be a either a Gmail, Hotmail, or Outlook address")
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    // Getters and setters har jag också i samma klass.
} 
``` 

Men det koden gör är att den används för validering av email och lösenord (även en validering för username).
Den ser till att när en admin registrerar en ny användare så finns restriktioner. Som är följande:

Username får ej vara empty alltså tom.

Email får ej vara empty samt ska ha ett @ tecken OCH sluta på en av följande: gmail/hotmail/outlook.com

Det gjorde jag med @pattern regex där man sätter specifika krav, man skulle även kunna lägga till lösenordsDTO:on
att lösenordet bör innehålla minst en stor bokstav samt ett tecken som är väldigt vanligt nuförtiden på
hemsidor.

Password får ej vara empty OCH password måste innehålla minst 8 tecken och det gör man med hjälp av @size.

Nu till frågorna för uppgiften:

#1  
  ```java
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registrationDto", new RegistrationDto());
        return "register";
    }
// Hanterar GET-förfrågningar för att visa registreringsformuläret

```

#2 

Orden "public" och "private" i metodsignaturen anger metodens åtkomstnivå. 
"Public" betyder att metoden kan nås från vilken annan klass som helst, medan "private" innebär att
metoden endast kan användas inom den klass där den är definierad.

#3 

Om det står "void" i metodsignaturen innebär det att metoden inte returnerar något värde.
Om det står en typ eller klass innebär det att metoden returnerar ett värde av den typen eller klassen.

#4 

I Java används camelCase för metodnamn. Det innebär att metoden börjar med en liten bokstav och
varje efterföljande ord börjar med en stor bokstav, till exempel. camelCase, myUsers etc..

#5

Om det inte står något mellan parenteserna efter metodens namn betyder det att metoden inte
tar några argument eller parametrar.

#6

De kallas antingen argument eller parametrar.

#7

```java
public void add(int a, int b){
    return a + b;
}
```

#8

```java
public class Calculator {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        int result = calculator.add(5, 10);
        System.out.println("The sum is: " + result);
    }

    public int add(int a, int b) {
        return a + b;
    }
}
```

#9

```java
public class HakansClass{
    public HakansClass() {
    }
}
```

#10

```java
// 1) Skapandet av en primitiv typ med ett värde
int temperature = 37;

// 2) Skapandet av ett objekt (en instans av en klass)
public class Car {
    String model;
    int year;

    // Konstruktor för att initiera modell och år
    public Car(String model, int year) {
        this.model = model;
        this.year = year;
    }
}

// Skapa en instans av Car-klassen
Car myCar = new Car("Toyota", 2021);

```

#11

I Java, används kamelnotation för metodnamn. Det innebär att metodnamn börjar med en liten
bokstav och varje nytt ord börjar med en stor bokstav. Metodnamn brukar vara verb eller verbfraser
för att beskriva vad metoden gör.

Exempel på metodnamn enligt konventionen:
* calculateTotal
* getUserName
* getPassword
* doesNotReturnNull

#12
