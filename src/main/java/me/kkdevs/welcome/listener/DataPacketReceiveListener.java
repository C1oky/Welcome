package me.kkdevs.welcome.listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.network.protocol.SetLocalPlayerAsInitializedPacket;
import cn.nukkit.network.protocol.DataPacket;
import me.kkdevs.welcome.BarManager;

import java.util.List;

import static me.kkdevs.welcome.Loader.config;
import static me.kkdevs.welcome.Loader.replace;

public class DataPacketReceiveListener implements Listener {

    private String
            title = config.getString("title", "title"),
            subTitle = config.getString("subTitle", "subTitle");
    private int
            fadeIn = config.getInt("fadeIn", 20),
            stay = config.getInt("stay", 20),
            fadeOut = config.getInt("fadeOut", 5);

    @EventHandler
    public void onPacketReceive(DataPacketReceiveEvent event) {
        Player player = event.getPlayer();
        DataPacket packet = event.getPacket();

        if (packet instanceof SetLocalPlayerAsInitializedPacket) {
            player.sendTitle(
                    replace(title, player),
                    replace(subTitle, player),
                    fadeIn,
                    stay,
                    fadeOut
            );

            BarManager.createBossBar(player, replace(((List<String>) config.get("messages")).get(0), player));
        }
    }
}