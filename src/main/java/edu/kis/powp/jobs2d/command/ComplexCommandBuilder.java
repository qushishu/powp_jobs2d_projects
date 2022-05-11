package edu.kis.powp.jobs2d.command;

import java.util.ArrayList;
import java.util.List;

public class ComplexCommandBuilder {
    private final List<DriverCommand> commandList = new ArrayList<>();
    private String name = "";

    public static ComplexCommandBuilder builder() {
        return new ComplexCommandBuilder();
    }

    public ComplexCommandBuilder addCommand(final DriverCommand command) {
        commandList.add(command);
        return this;
    }

    public ComplexCommandBuilder addName(String name) {
        this.name = name;
        return this;
    }

    public ComplexCommandBuilder addOperateTo(int posX, int posY) {
        return addCommand(new OperateToCommand(posX, posY));
    }

    public ComplexCommandBuilder addSetPosition(int posX, int posY) {
        return addCommand(new SetPositionCommand(posX, posY));
    }

    public ComplexCommand build() {
        //TODO: clear result if command build will be added
        return new ComplexCommand(new ArrayList<>(commandList), name);
    }
}
