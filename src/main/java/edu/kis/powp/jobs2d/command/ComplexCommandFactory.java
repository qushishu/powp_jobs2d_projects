package edu.kis.powp.jobs2d.command;

public class ComplexCommandFactory {
    public static ComplexCommand getSquareCommand() {
        return new ComplexCommandBuilder()
                .addName("square")
                .addSetPosition(0, 0)
                .addOperateTo(10, 0)
                .addOperateTo(10, 10)
                .addOperateTo(0, 10)
                .addOperateTo(0, 0)
                .build();
    }
}
