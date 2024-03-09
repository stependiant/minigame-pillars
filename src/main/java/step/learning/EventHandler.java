package step.learning;

import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;


public class EventHandler implements Listener {

    @org.bukkit.event.EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Component text = Component.text("Hello!")
                .append(Component.newline())
                .append(Component.text("Welcome in minigame Pillars")
                .append(Component.newline())
                .append(Component.text("for start game, say in chat /start")));
        player.sendMessage(text);
    }
    @org.bukkit.event.EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        event.getDrops().clear();
        Location respawnLocation = new Location(player.getWorld(), 0, 101, 0);
        player.teleport(respawnLocation);
        player.setGameMode(GameMode.ADVENTURE);
    }

}
