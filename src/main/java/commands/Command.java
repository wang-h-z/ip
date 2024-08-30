package commands;

import exceptions.FridayException;

import common.TaskList;

import storage.Storage;

import ui.Ui;

public abstract class Command {
    public abstract void execute(TaskList list, Ui ui, Storage storage) throws FridayException;
    public abstract boolean isExit();
}
