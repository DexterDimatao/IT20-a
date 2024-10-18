
package LabActivity;
import java.util.LinkedList;
public class linkedlist {
    public static void main(String[] args) {
        LinkedList<String> person = new LinkedList<>();
        person.add("dexter");
        person.addFirst("ampo");
        person.addLast("always");
        person.add(1, "blessed");
        System.out.println("Linked List Original: " + person);
        System.out.println(person.size());
        if (person.contains("amen")) {
            System.out.println("True");
        } else {
            System.out.println("False");
        }
        boolean containslion = person.contains("gwapo ko");
        System.out.println(containslion);
    }
}