package me.kkdevs.welcome.listener;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.network.protocol.SetLocalPlayerAsInitializedPacket;
import cn.nukkit.network.protocol.DataPacket;
import me.kkdevs.welcome.BarManager;
import me.kkdevs.welcome.Loader;

import java.util.*;

import static me.kkdevs.welcome.Loader.*;

public class DataPacketReceiveListener implements Listener {

    private static List<String> titleMessages, subTitleMessages;
    private int
            fadeIn = config.getInt("fadeIn", 20),
            stay = config.getInt("stay", 20),
            fadeOut = config.getInt("fadeOut", 5);

    public DataPacketReceiveListener(Loader plugin) {
        try {
            if (config.getBoolean("showTitle", true)) {
                titleMessages = config.getStringList("title");
                subTitleMessages = config.getStringList("subTitle");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            Server.getInstance().getPluginManager().disablePlugin(plugin);
        }
    }

    @EventHandler
    public void onPacketReceive(DataPacketReceiveEvent event) {
        Player player = event.getPlayer();
        DataPacket packet = event.getPacket();

        if (packet instanceof SetLocalPlayerAsInitializedPacket) {
            if (config.getBoolean("showTitle", true)) {
                String title = replace(getRandomStringElementList(titleMessages), player),
                        subTitle = replace(getRandomStringElementList(subTitleMessages), player);

                player.sendTitle(title, subTitle, fadeIn, stay, fadeOut);
            }

            BarManager.createBossBar(player, replace(((List<String>) config.get("messages")).get(0), player));
        }
    }

    private String getRandomStringElementList(List<String> stringList) {
        int listSize = stringList.size();

        if (listSize > 1) {
            return stringList.get(new Random().nextInt(listSize));
        }

        return stringList.get(0);
    }
}