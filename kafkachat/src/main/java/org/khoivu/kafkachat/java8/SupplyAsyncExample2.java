package org.khoivu.kafkachat.java8;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class SupplyAsyncExample2 {
  public static void main(String[] args) {
    CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
      System.out.println("Running a task");
      return "Task complete";
    });

    System.out.println("Test1");
    if (cf != null) {
      try {
        System.out.println("Returned Value " + cf.get().toString());
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (ExecutionException e) {
        e.printStackTrace();
      }
    }
    System.out.println("Test 2");
  }
}
