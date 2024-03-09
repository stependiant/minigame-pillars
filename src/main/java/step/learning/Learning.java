package step.learning;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import step.learning.commands.Command;

public final class Learning extends JavaPlugin {
    private static Learning learning;

    @Override
    public void onEnable() {
        learning = this;
        getServer().getPluginManager().registerEvents(new EventHandler(), this);
        setAlwaysDay();
        Command.initializeCommand();
    }
    private void setAlwaysDay() {
        World world = Bukkit.getWorlds().get(0);
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        world.setTime(6000);
    }
    public static Learning getLearning() {
        return learning;
    }
}
