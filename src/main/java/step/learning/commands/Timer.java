package step.learning.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.WorldBorder;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;
import step.learning.Learning;

import java.util.List;
import java.util.Random;

public class Timer {
    private static Timer Timer;
    BukkitTask task;
    private double currentBorderDecreaseSpeed = 0.002;
    private int seconds;
    public void startTimer() {
        BossBar bossBar = Bukkit.createBossBar("Next new item", BarColor.PINK, BarStyle.SEGMENTED_20);

        task = Bukkit.getScheduler().runTaskTimer(Learning.getLearning(), () -> {
            List<Player> players = Bukkit.getWorld("world").getPlayers();

            decreaseWorldBorderSize();

            if (seconds >= 15) {
                bossBar.setProgress(0);
                for (Player player : players) {
                    randomItem(player);
                }
                seconds = 0;
            }

            double progress = (double) seconds / 15.0;
            bossBar.setProgress(progress);

            for (Player player : players) {
                bossBar.addPlayer(player);
            }
            seconds++;
        }, 20, 20);
    }
    private static void randomItem(Player player) {
        Material[] allMaterials = Material.values();
        Random random = new Random();
        Material randomMaterial = allMaterials[random.nextInt(allMaterials.length)];
        ItemStack randomItem = new ItemStack(randomMaterial);
        player.getInventory().addItem(randomItem);
    }
    private void decreaseWorldBorderSize() {
        WorldBorder worldBorder = CommandStart.world.getWorldBorder();
        double currentSize = worldBorder.getSize();

        if (currentSize > 1) {
            double newSize = currentSize - currentBorderDecreaseSpeed;
            if (newSize < 1) {
                newSize = 1;
            }

            worldBorder.setSize(newSize);

            if (currentBorderDecreaseSpeed < 0.75) {
                currentBorderDecreaseSpeed += 0.0004;
            }
        }
    }
    public static Timer getWaveTimer() {
        if (Timer == null) {
            Timer = new Timer();
        }
        return Timer;
    }
    public void stopTimer() {
        if (task != null) {
            task.cancel();
        }
    }
}
