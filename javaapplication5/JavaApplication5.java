package LabActivity;
import java.util.LinkedList;
import java.util.Queue;
public class SimpleQueueExample {
    public static void main(String[] args) {
        Queue<String> queue = new LinkedList<>();
        queue.add("dxter");
        queue.add("kent");
        queue.add("yan");
        String firstElement = queue.poll();
        String secondElement = queue.poll();
        System.out.println("Remaining elements in the queue: " + queue);
        queue.add("Date");
        queue.add("sabon");
        String thirdElement = queue.poll();
        System.out.println("Remaining elements in the queue: " + queue);
    }
}
