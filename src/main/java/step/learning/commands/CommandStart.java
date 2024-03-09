package step.learning.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import step.learning.Learning;

import java.util.*;


public class CommandStart implements CommandExecutor {
    private double initialBorderDecreaseSpeed = 0.02;
    static World world = Bukkit.getWorld("world");

    private List<Location> list = new ArrayList<>(Arrays.asList(
            new Location(world, 0.5, 60, 12.5),
            new Location(world, 12.5, 60, 0.5),
            new Location(world, 0.5, 60, -11.5),
            new Location(world, -11.5, 60, 0.5)
    ));
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player) {
            clearMap();
            tpPlayers();
            Timer timer = Timer.getWaveTimer();
            timer.stopTimer();
            timer.startTimer();
            setInitialWorldBorderSize(35);
        }
        return true;
    }
    private void clearMap() {
        for (int x = -18; x < 18; x++) {
            for (int y = 49; y < 80; y++) {
                for (int z = -18; z < 18; z++) {
                    Block block = world.getBlockAt(x, y, z);
                    if (block.getType() != Material.BEDROCK && block.getType() != Material.AIR) {
                        block.setType(Material.AIR);
                    }
                }
            }
        }
    }
    private void setInitialWorldBorderSize(int size) {
        WorldBorder worldBorder = world.getWorldBorder();
        worldBorder.setSize(size);
    }
    private void tpPlayers() {
        List<Player> players = Bukkit.getWorld("world").getPlayers();
        for (Player player : players) {
            if (!list.isEmpty()) {
                player.getInventory().clear();
                Random random = new Random();
                Location randomSpawnPoint = list.get(random.nextInt(list.size()));
                player.setGameMode(GameMode.SURVIVAL);
                player.teleport(randomSpawnPoint);
            }
        }
    }

}
