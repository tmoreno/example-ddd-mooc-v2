package com.tmoreno.mooc.shared.command;

public interface Command<T extends CommandParams> {
    void execute(T params);
}
