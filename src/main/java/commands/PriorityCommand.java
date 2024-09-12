package commands;

import common.TaskList;

import exceptions.FridayException;
import exceptions.MissingCommandException;

import storage.Storage;

import ui.Ui;

import java.util.Objects;

public class PriorityCommand extends Command {

    public enum Priorities {
        HIGH{
            @Override
            public String toString() {
                return "H";
            }
        },
        MEDIUM {
            @Override
            public String toString() {
                return "M";
            }
        },
        LOW {
            @Override
            public String toString() {
                return "L";
            }
        }

    }
    private final String input;
    public PriorityCommand(String input) {
        this.input = input;
    }
    @Override
    public void execute(TaskList list, Ui ui, Storage storage) throws FridayException {
        if (!this.input.contains("/lvl")) {
            throw new MissingCommandException("/lvl");
        }
        int priorityIndex = this.input.indexOf("priority") + "priority".length();
        int lvlIndex = this.input.indexOf("/lvl");
        String stringId = this.input.substring(priorityIndex, lvlIndex).trim();
        if (stringId.isEmpty()) {
            throw new FridayException("Please give a valid task index.");
        }
        int id;
        try {
            id = Integer.parseInt(stringId);
        } catch (NumberFormatException e) {
            throw new FridayException("Please give a valid numerical task index.");
        }
        if (id - 1< 0 || id - 1>= list.size()) {
            throw new FridayException("The task you are looking for does not exist in the list.");
        }
        String level = this.input.substring(lvlIndex + "/lvl".length()).trim().toUpperCase();
        if (level.isEmpty()) {
            throw new FridayException("Please enter a priority level. i.e HIGH/MEDIUM/LOW");
        }
        Priorities p = null;
        for (Priorities priority : Priorities.values()) {
            if (priority.name().equals(level)) {
                p = priority;
            }
        }
        if (p == null) {
            throw new FridayException("Please enter a valid priority level. i.e HIGH/MEDIUM/LOW");
        }
        list.get(id - 1).setPriority(p);
        storage.saveTasks(list);
        String output = "\t Nice! I've added a priority to this task:" + "\n" + "\t  " + list.get(id - 1);
        ui.showOutput(output);
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public String guiResponse(TaskList list, Storage storage) throws FridayException {
        if (!this.input.contains("/lvl")) {
            throw new MissingCommandException("/lvl");
        }
        int priorityIndex = this.input.indexOf("priority") + "priority".length();
        int lvlIndex = this.input.indexOf("/lvl");
        String stringId = this.input.substring(priorityIndex, lvlIndex).trim();
        if (stringId.isEmpty()) {
            throw new FridayException("Please give a valid task index.");
        }
        int id;
        try {
            id = Integer.parseInt(stringId);
        } catch (NumberFormatException e) {
            throw new FridayException("Please give a valid numerical task index.");
        }
        if (id - 1 < 0 || id - 1>= list.size()) {
            throw new FridayException("The task you are looking for does not exist in the list.");
        }
        String level = this.input.substring(lvlIndex + "/lvl".length()).trim().toUpperCase();
        if (level.isEmpty()) {
            throw new FridayException("Please enter a priority level. i.e HIGH/MEDIUM/LOW");
        }
        Priorities p = null;
        for (Priorities priority : Priorities.values()) {
            if (priority.name().equals(level)) {
                p = priority;
            }
        }
        if (p == null) {
            throw new FridayException("Please enter a valid priority level. i.e HIGH/MEDIUM/LOW");
        }
        list.get(id - 1).setPriority(p);
        storage.saveTasks(list);
        return "Nice! I've added a priority to this task:" + "\n" + "\t  " + list.get(id - 1);
    }

    public static Priorities priorityString(String input) {
        if (input.equals("H")) {
            return Priorities.HIGH;
        }
        if (input.equals( "M")) {
            return Priorities.MEDIUM;
        }
        if (input.equals("L")) {
            return Priorities.LOW;
        }
        return null;
    }
}
