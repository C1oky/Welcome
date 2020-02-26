package me.kkdevs.welcome;

import cn.nukkit.Player;
import cn.nukkit.utils.DummyBossBar;

import java.util.HashMap;

import static me.kkdevs.welcome.Loader.*;

public class BarManager {

    private static HashMap<String, Long> barList = new HashMap<>();

    public static void createBossBar(Player player, String text) {
        if (config.getBoolean("useBossBar", true)) {
            if (getBarId(player) == null) {
                DummyBossBar dummyBossBar = new DummyBossBar.Builder(player)
                        .text(text)
                        .length(100)
                        .build();

                player.createBossBar(dummyBossBar);
                getMap().put(player.getName(), dummyBossBar.getBossBarId());
            }
        }
    }

    public static void removeBossBar(Player player) {
        Long barId = getBarId(player);

        if (barId != null) {
            player.removeBossBar(barId);
            getMap().remove(player.getName());
        }
    }

    public static void updateBossBar(Player player, String text, int length) {
        DummyBossBar dummyBossBar = getDummyBossBar(player);

        if (dummyBossBar != null) {
            dummyBossBar.setText(text);
            dummyBossBar.setLength(length);
        }
    }

    public static DummyBossBar getDummyBossBar(Player player) {
        Long barId = getBarId(player);

        if (barId != null) {
            return player.getDummyBossBar(barId);
        }

        return null;
    }

    public static Long getBarId(Player player) {
        Long barId = getMap().get(player.getName());

        if (barId != null) {
            return barId;
        }

        return null;
    }

    public static HashMap<String, Long> getMap() {
        return barList;
    }
}