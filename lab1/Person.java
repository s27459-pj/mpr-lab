package lab1;

public class Person {
    private String name;
    private int age;

    public Person(String name, int age) throws InvalidAgeException {
        setName(name);
        setAge(age);
    }

    public Person(String name) {
        setName(name);
    }

    public String toString() {
        return String.format("Person(%s, %d)", name, age);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) throws InvalidAgeException {
        if (age < 0 || age > 120)
            throw new InvalidAgeException("Invalid age: " + age);
        this.age = age;
    }
}
