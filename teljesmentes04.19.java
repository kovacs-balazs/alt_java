package me.koba1.tutorial;

import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.ChatColor;
import org.bukkit.Bukkit;



public class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Tutorial plugin betöltve!");
        getConfig().options().copyDefaults();
        saveConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("tutorial")) {
            if (sender instanceof Player) {
                try {
                    sender.sendMessage(getConfig().getString("szoveg"));
                    sender.sendMessage("§o" + getConfig().getInt("int"));
                    sender.sendMessage("§1" + getConfig().getDouble("double"));
                    sender.sendMessage("§n" + getConfig().getBoolean("boolean"));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("Szines_szoveg")));
//                    reloadConfig();
//                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("Szines_szoveg")));
                } catch (Exception e) {
                    sender.sendMessage("§cValami hibát észleltem! Nézd meg a konzolt!");
                    e.printStackTrace();

                }
            } else {
                sender.sendMessage("§cEz a konzol!");
            }
        }

        if(cmd.getName().equalsIgnoreCase("stats")) {
            if(sender instanceof Player) {
            Player p = (Player) sender;
            Player t = (Player) sender;
            String uuid = p.getUniqueId().toString();

            sender.sendMessage(ChatColor.AQUA + "+------------------------------------+");
            sender.sendMessage(ChatColor.BLUE + "Szia " + ChatColor.GREEN + ((Player) sender).getName() + "§9!");
            sender.sendMessage(ChatColor.RED + "Életed: " + ((Player) sender).getHealth() / 2);

            int kills = getConfig().getInt("Players." + uuid + ".Kills");
            int deaths = getConfig().getInt("Players." + uuid + ".Deaths");
            sender.sendMessage("§a§n" + p.getName() + "§r " + ChatColor.YELLOW + kills + "§a öléssel és " + ChatColor.YELLOW + deaths + "§a halállal rendelkezik!");
            sender.sendMessage(ChatColor.AQUA + "+------------------------------------+");
            }
        }

        if(cmd.getName().equalsIgnoreCase("targyinfo")) {
            if(sender instanceof Player) {
                sender.sendMessage(ChatColor.GOLD + "Tárgy a kezedben: " + ((Player) sender).getItemInHand().getType());
                sender.sendMessage(ChatColor.GOLD + "Kiütött blockok: " + ChatColor.YELLOW + ((Player) sender).getItemInHand().getDurability() + " §6Maradt még: " + ChatColor.YELLOW + (((Player) sender).getItemInHand().getType().getMaxDurability() - ((Player) sender).getItemInHand().getDurability()));
            }
        }

        if(cmd.getName().equalsIgnoreCase("teszt")) {
            if(sender instanceof Player) {

                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "say 1 (Console)");
                ((Player) sender).performCommand("say 2 (Player)");
                getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "asd!");
            }
        }

        return false;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Location loc = e.getBlock().getLocation();

        e.getPlayer().sendMessage(ChatColor.GREEN + "Kiütöttél egy blockot!");
        e.getPlayer().playEffect(loc, Effect.MOBSPAWNER_FLAMES, 1);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Location loc = e.getBlock().getLocation();

        e.getPlayer().sendMessage(ChatColor.AQUA + "Letettél egy blockot!");
        e.getPlayer().playEffect(loc, Effect.MOBSPAWNER_FLAMES, 1);

    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        if(p.getKiller() instanceof  Player) {
            Player k = p.getKiller();
            String pUUID = p.getUniqueId().toString();
            String kUUID = k.getUniqueId().toString();
            int kills = getConfig().getInt("Players." + kUUID + ".Kills");
            int deaths = getConfig().getInt("Players." + pUUID + ".Deaths");

            getConfig().set("Players." + kUUID + ".Kills", kills + 1);
            getConfig().set("Players." + pUUID + ".Deaths", deaths + 1);
            saveConfig();

            k.sendMessage(color("&aMegölted őt: " + ChatColor.RED + k.getName()));
            p.sendMessage(color("&cMegölt téged: " + ChatColor.YELLOW + p.getName()));
        }
    }

    public String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

}
