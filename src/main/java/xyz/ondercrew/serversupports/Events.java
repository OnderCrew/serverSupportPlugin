package xyz.ondercrew.serversupports;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.List;

public class Events implements Listener {
    private List<Deaths> deathRecord = ServerSupports.getPlugin(ServerSupports.class).deathRecord;
    private Server server = ServerSupports.getPlugin(ServerSupports.class).getServer();

    @EventHandler
    public void onPlayerJoin (PlayerJoinEvent event) {
        Player player = event.getPlayer();
        boolean contain = false;

        for (Deaths record : deathRecord) {
            if (record.uid.equals(player.getUniqueId().toString())) contain = true;
        }

        if (!contain) {
            Deaths deaths = new Deaths();
            deaths.uid = player.getUniqueId().toString();
            deaths.death = 0;

            deathRecord.add(deaths);
        }
    }

    @EventHandler
    public void onPlayerSneak (PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        if (player.isSneaking()) {
            for (Player p : server.getOnlinePlayers()) {
                for (Deaths deaths : deathRecord) {
                    if (deaths.uid.equals(p.getUniqueId().toString())) {
                        if (p.equals(player)) {
                            String str = new String(new char[Integer.toString(deaths.death).length()]).replace("\0", "?");
                            player.sendMessage(ChatColor.GOLD + p.getDisplayName() + ": " + ChatColor.RED + str + " Death(s)");
                        } else {
                            player.sendMessage(ChatColor.GOLD + p.getDisplayName() + ": " + ChatColor.RED + deaths.death + " Death(s)");
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDeath (PlayerDeathEvent event) {
        Player player = event.getEntity();
        for (Deaths deaths : deathRecord) {
            if (deaths.uid.equals(player.getUniqueId().toString())) {
                deaths.death++;
            }
        }
    }
}
