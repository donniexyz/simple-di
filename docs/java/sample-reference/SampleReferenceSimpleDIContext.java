package com.example;

import com.github.michaelboyles.simpledi.ISimpleDIContext;
import com.github.michaelboyles.simpledi.MutableProvider;
import com.github.michaelboyles.simpledi.test.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class SampleReferenceSimpleDIContext implements ISimpleDIContext {

    public static SimpleDIContext INSTANCE;

    private final Map<String, Object> nameToBean = new HashMap<>();
    private final Map<Class<?>, List<Object>> classToBean = new HashMap<>();
    private final boolean skipClassMap;

    public SampleReferenceSimpleDIContext() {
        this(false);
    }

    public SampleReferenceSimpleDIContext(boolean skipClassMap) {
        this.skipClassMap = skipClassMap;
        MutableProvider<Car> carProvider = new MutableProvider<>();
        Driver driver = new Driver();
        DriversSeat driversSeat = new DriversSeat();
        PassengerSeat passengerSeat = new PassengerSeat();
        Turbocharger turbocharger = new Turbocharger();
        Engine engine = new Engine(turbocharger);
        Car car = new Car(engine, driversSeat, List.of(driversSeat, passengerSeat), carProvider);
        carProvider.set(car);
        car.addDriver(driver);
        car.addSeats(new Seat[]{driversSeat, passengerSeat});
        nameToBean.put("driver", driver);
        nameToBean.put("driversSeat", driversSeat);
        nameToBean.put("passengerSeat", passengerSeat);
        nameToBean.put("turbocharger", turbocharger);
        nameToBean.put("engine", engine);
        nameToBean.put("car", car);

        putIntoClassMapList(driver);
        putIntoClassMapList(driversSeat);
        putIntoClassMapList(passengerSeat);
        putIntoClassMapList(turbocharger);
        putIntoClassMapList(engine);
        putIntoClassMapList(car);

        if (null == INSTANCE) INSTANCE = this;
    }

    private List<Object> putIntoClassMapList(Object object) {
        if (null == object) return null;
        Class<?> aClass = object.getClass();
        List<Object> list;
        list = putIntoClassMapList(aClass, object);

        Class<?> superclass = aClass.getSuperclass();
        while (superclass != null) {
            putIntoClassMapList(superclass, object);
            superclass = superclass.getSuperclass();
        }

        Class<?>[] interfaces = aClass.getInterfaces();
        for (Class<?> iface : interfaces) {
            putIntoClassMapList(iface, object);
        }

        return list;
    }

    private List<Object> putIntoClassMapList(Class<?> aClass, Object object) {
        if (!classToBean.containsKey(aClass)) classToBean.put(aClass, new ArrayList<>());
        List<Object> list = classToBean.get(aClass);
        list.add(object);
        return list;
    }

    @Override
    public Object getBeanByName(String name) {
        return nameToBean.get(name);
    }

    @Override
    public <T> List<T> getBeanByType(Class<T> tClass) {
        return skipClassMap ? null : (List<T>) classToBean.get(tClass);
    }
}
