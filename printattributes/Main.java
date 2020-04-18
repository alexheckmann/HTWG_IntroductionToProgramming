package printattributes;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        City city = new City("78467", "Petershausen", "BW");
        Address address = new Address(city, "...", 1);
        Student student = new Student("Max Mustermann", address);
        printObjectsAttributes(student);
    }


    private static void printObjectsAttributes(Object object) throws InvocationTargetException, IllegalAccessException {
        Class clazz = object.getClass();
        Field[] attributes = clazz.getDeclaredFields();
        Method[] methods = clazz.getDeclaredMethods();

        for (Field attribute : attributes) {
            String fieldName = attribute.getName();
            if (attribute.getType().isPrimitive() || attribute.getType().equals(String.class)) {
                System.out.println(fieldName);
                for (Method method : methods) {
                    String methodName = method.getName();
                    if (methodName.startsWith("get") && methodName.toLowerCase().contains(fieldName.toLowerCase())) {
                        System.out.println("> " + method.invoke(object));
                    }
                }
            } else {
                for (Method method : methods) {
                    String methodName = method.getName().toLowerCase();
                    if (methodName.startsWith("get") && methodName.contains(fieldName.toLowerCase())) {
                        printObjectsAttributes(method.invoke(object));
                    }
                }
            }
        }
    }
}