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
