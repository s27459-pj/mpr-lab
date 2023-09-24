package lab1;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        try {
            new Person("John", 200);
        } catch (InvalidAgeException e) {
            System.out.println(e.getMessage());
        }

        try {
            var person1 = new Person("John", 21);
            var person2 = new Person("Chris", 25);
            var person3 = new Person("Jane", 30);
            var person4 = new Person("Jane", 26);

            var personList = List.of(person1, person2, person3, person4);
            System.out.println("personList: " + personList);

            var personSet = Set.of(person1, person2, person3, person4);
            System.out.println("personSet: " + personSet);

            var personMap = Map.of(0, person1, 1, person2, 2, person3, 3, person4);
            System.out.println("personMap: " + personMap);

            // Map
            var names = personList.stream().map(Person::getName).toList();
            var ages = personList.stream().map(Person::getAge).toList();
            System.out.println("names: " + names);
            System.out.println("ages: " + ages);

            // Reduce
            var avgAge = ages.stream().reduce(0, Integer::sum) / (double) ages.size();
            System.out.println("avgAge: " + avgAge);

            // Filter
            var above25 = personList.stream().filter(p -> p.getAge() > 25).toList();
            System.out.println("above25: " + above25);

            // Sort
            var sortedByName = personList.stream().sorted((p1, p2) -> p1.getName().compareTo(p2.getName())).toList();
            System.out.println("sortedByName: " + sortedByName);

            // ForEach
            System.out.println("perosonList.forEach(System.out::println):");
            personList.stream().forEach(System.out::println);

            // Min/Max
            Comparator<Person> ageComparator = (p1, p2) -> p1.getAge() - p2.getAge();
            var youngest = personList.stream().min(ageComparator);
            if (youngest.isPresent()) {
                System.out.println("youngest: " + youngest);
            } else {
                System.out.println("youngest: null");
            }

            var oldest = personList.stream().max(ageComparator);
            if (oldest.isPresent()) {
                System.out.println("oldest: " + oldest);
            } else {
                System.out.println("oldest: null");
            }
        } catch (InvalidAgeException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}
