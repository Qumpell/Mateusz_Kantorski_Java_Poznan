# Payment Optimization Project

## Description
This project enables the optimization of payments based on available payment methods and promotional discounts. The goal of the application is to select the most beneficial payment method for a given order.

## Requirements
- Java 21
- Maven 3.x

## Project Structure
- `src/`: The source code of the application
- `pom.xml`: Maven configuration file managing dependencies, compilation, and testing
- `target/`: The directory containing the build artifacts, including the `.jar` file

## Dependencies
The application uses the following libraries:
- **JUnit 5** for unit testing
- **Lombok** to simplify boilerplate code (e.g., generating getters, setters, `toString`, etc.)
- **MapStruct** for object mapping
- **Jackson** for JSON processing (serialization and deserialization)

## How to Build the Project

1. **Install Required Tools:**
    - Java 11 or higher
    - Maven 3.9.6

2. **Clone the Repository:**
   ```bash
   git clone https://github.com/github.com/Qumpell/Mateusz_Kantorski_Java_Poznan.git

3. **Build the Project:**
   ```bash
   mvn clean package
4. **Locate the .jar File** \
   The .jar file will be located in the target/ directory, and its name will include the artifact ID and version, for example:
   ```bash
   target/your-artifact-name-jar-with-dependencies.jar
5. **Run the application** \

 ```bash
   java -jar target/your-artifact-name-jar-with-dependencies.jar your/path/orders.json your/path/paymentmethods.json 
```

   The application expects two input files in JSON format:

 - orders.json – a JSON file containing the orders data.

 - paymentmethods.json – a JSON file containing the payment methods data.

Both of these files must be provided when running the application

## Testing
The application includes unit tests written with JUnit 5. To run the tests, use the following command:
```bash
     mvn test
```
