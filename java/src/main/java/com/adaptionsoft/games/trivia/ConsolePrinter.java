package com.adaptionsoft.games.trivia;

public class ConsolePrinter implements Printer {
    @Override
    public void print(Object objectToPrint) {
        System.out.println(objectToPrint);
    }
}
