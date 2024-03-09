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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Timer {
    private List<BossBar> bossBars = new ArrayList<>();
    private static Timer Timer;
    BukkitTask task;
    private double currentBorderDecreaseSpeed = 0.002;
    private int seconds;
    public void startTimer() {
        bossBars.add(Bukkit.createBossBar("Next new item", BarColor.PINK, BarStyle.SEGMENTED_10));


        task = Bukkit.getScheduler().runTaskTimer(Learning.getLearning(), () -> {
            List<Player> players = Bukkit.getWorld("world").getPlayers();

            decreaseWorldBorderSize();

            if (seconds >= 15) {
                bossBars.get(0).setProgress(0);
                for (Player player : players) {
                    randomItem(player);
                    player.setFoodLevel(20);
                }
                seconds = 0;
            }

            double progress = (double) seconds / 15.0;
            bossBars.get(0).setProgress(progress);

            for (Player player : players) {
                bossBars.get(0).addPlayer(player);
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
    public void clearBossBars() {
        for (BossBar bossBar : bossBars) {
            bossBar.removeAll();
        }
        seconds = 0;
        bossBars.clear();
    }
    public static Timer getTimer() {
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
