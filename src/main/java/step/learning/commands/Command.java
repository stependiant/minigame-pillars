package step.learning.commands;

import step.learning.Learning;

public class Command {
    public static void initializeCommand() {
        Learning plugin = Learning.getLearning();

        plugin.getCommand("start").setExecutor(new CommandStart());
    }
}
