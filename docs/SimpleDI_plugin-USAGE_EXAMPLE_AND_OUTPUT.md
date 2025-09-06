# USAGE EXAMPLE AND OUTPUT

## Example project content
### Summary
#### Directory:
- sample/src/main/java/com/github/michaelboyles/simpledi/test/
#### Files:
- Car.java
- Driver.java
- DriversSeat.java
- Engine.java
- PassengerSeat.java
- Seat.java
- Turbocharger.java

### Detail
#### Car.java
```java
package com.github.michaelboyles.simpledi.test;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Provider;
import jakarta.inject.Singleton;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;

@Singleton
public class Car {
    private final Engine engine;
    private final Seat driversSeat;
    private final List<? extends Seat> seats;

    @Inject
    public Car(Engine engine, @Named("driver") Seat driversSeat, List<? extends Seat> seats, Provider<Car> self) {
        this.engine = engine;
        this.driversSeat = driversSeat;
        this.seats = seats;
    }

    // Just an example of a constructor that's ignored due to @Inject on the other one
    public Car() {
        this(null, null, emptyList(), null);
    }

    @Inject
    public void addDriver(Driver driver) {
        System.out.println("Added driver "  + driver);
    }

    @Inject
    public void addSeats(Seat[] seats) {
        System.out.println("Added seats " + Arrays.toString(seats));
    }
}
```
### Driver.java
```java
package com.github.michaelboyles.simpledi.test;

import jakarta.inject.Singleton;

@Singleton
public class Driver {
    @Override
    public String toString() {
        return "John Smith";
    }
}
```
### DriversSeat.java
```java
package com.github.michaelboyles.simpledi.test;

import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Singleton
@Named("driver")
public class DriversSeat implements Seat {
    @Override
    public String getPosition() {
        return "front right";
    }
}
```
### Engine.java
```java
package com.github.michaelboyles.simpledi.test;

import jakarta.inject.Singleton;

@Singleton
public record Engine(Turbocharger turbocharger) {
}
```
### PassengerSeat.java
```java
package com.github.michaelboyles.simpledi.test;

import jakarta.inject.Singleton;

@Singleton
public class PassengerSeat implements Seat {
    @Override
    public String getPosition() {
        return "front left";
    }
}
```
### Seat.java
```java
package com.github.michaelboyles.simpledi.test;

public interface Seat {
    String getPosition();
}
```
### Turbocharger.java
package com.github.michaelboyles.simpledi.test;

import jakarta.inject.Singleton;

@Singleton
public class Turbocharger {
public String getCondition() {
return "Fair";
}
}
```java
```

## Generated SimpleDIContext
### SimpleDIContext
```java
package com.example;

import com.github.michaelboyles.simpledi.MutableProvider;
import com.github.michaelboyles.simpledi.test.Car;
import com.github.michaelboyles.simpledi.test.Driver;
import com.github.michaelboyles.simpledi.test.DriversSeat;
import com.github.michaelboyles.simpledi.test.Engine;
import com.github.michaelboyles.simpledi.test.PassengerSeat;
import com.github.michaelboyles.simpledi.test.Seat;
import com.github.michaelboyles.simpledi.test.Turbocharger;
import java.lang.Object;
import java.lang.String;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class SimpleDIContext {
  private final Map<String, Object> nameToBean = new HashMap<>();

  public SimpleDIContext() {
    MutableProvider<Car> carProvider = new MutableProvider<>();
    Driver driver = new Driver();
    DriversSeat driversSeat = new DriversSeat();
    PassengerSeat passengerSeat = new PassengerSeat();
    Turbocharger turbocharger = new Turbocharger();
    Engine engine = new Engine(turbocharger);
    Car car = new Car(engine, driversSeat, List.of(driversSeat, passengerSeat), carProvider);
    carProvider.set(car);
    car.addDriver(driver);
    car.addSeats(new Seat[] {driversSeat, passengerSeat});
    nameToBean.put("driver", driver);
    nameToBean.put("driversSeat", driversSeat);
    nameToBean.put("passengerSeat", passengerSeat);
    nameToBean.put("turbocharger", turbocharger);
    nameToBean.put("engine", engine);
    nameToBean.put("car", car);
  }

  public Object getBeanByName(String name) {
    return nameToBean.get(name);
  }
}
```

## Usage of generated SimpleDIContext
### VehicleService.java
```java
```
