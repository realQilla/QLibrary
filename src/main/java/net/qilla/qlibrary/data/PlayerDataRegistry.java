package net.qilla.qlibrary.data;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerDataRegistry {

    private static PlayerDataRegistry INSTANCE;

    private final Map<UUID, PlayerData> playerDataMap = new ConcurrentHashMap<>();

    private PlayerDataRegistry() {
    }

    public static PlayerDataRegistry getInstance() {
        if (INSTANCE == null) INSTANCE = new PlayerDataRegistry();
        return INSTANCE;
    }

    public PlayerData getPlayerData(UUID uuid) {
        return playerDataMap.computeIfAbsent(uuid, k -> new PlayerData());
    }
}
