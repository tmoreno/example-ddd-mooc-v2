package com.tmoreno.mooc.backoffice.shared.command;

public interface Command<T extends CommandParams> {
    void execute(T params);
}
