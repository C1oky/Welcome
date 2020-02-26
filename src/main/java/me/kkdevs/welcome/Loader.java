package me.kkdevs.welcome;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import me.kkdevs.welcome.listener.DataPacketReceiveListener;
import me.kkdevs.welcome.listener.PlayerQuitListener;
import me.kkdevs.welcome.task.UpdaterTask;

import java.util.Arrays;

public class Loader extends PluginBase {

    public static Config config;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        config = new Config("plugins/Welcome/config.yml", Config.YAML);

        if (config.getBoolean("useBossBar", true) && config.getBoolean("updateBar", true)) {
            Server.getInstance().getScheduler().scheduleRepeatingTask(this, new UpdaterTask(), config.getInt("period", 20));
        }

        Arrays.asList(
                new DataPacketReceiveListener(this),
                new PlayerQuitListener()
        ).forEach(listener -> Server.getInstance().getPluginManager().registerEvents(listener, this));
    }

    public static String replace(String text, Player player) {
        return text
                .replace("%player_name%", player.getName())
                .replace("%player_displayname%", player.getDisplayName())
                .replace("%player_ping%", Integer.toString(player.getPing()))
                .replace("%player_health%", Float.toString(player.getHealth()))
                .replace("%player_maxhealth%", Integer.toString(player.getMaxHealth()))
                .replace("%player_x%", Integer.toString(player.getFloorX()))
                .replace("%player_y%", Integer.toString(player.getFloorY()))
                .replace("%player_z%", Integer.toString(player.getFloorZ()))
                .replace("%server_online%", Integer.toString(Server.getInstance().getOnlinePlayers().size()))
                .replace("%server_maxonline%", Integer.toString(Server.getInstance().getMaxPlayers()));
    }
}