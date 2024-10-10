package org.lushplugins.lushrewards.olddata;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.lushplugins.lushrewards.LushRewards;
import org.enchantedskies.EnchantedStorage.Storage;

import java.io.*;
import java.util.UUID;

public class JsonStorage implements Storage<DataManager.StorageData, DataManager.StorageLocation> {
    private final File dataFolder = new File(LushRewards.getInstance().getDataFolder(), "data");

    @Override
    public DataManager.StorageData load(DataManager.StorageLocation storageLocation) {
        UUID uuid = storageLocation.uuid();
        String module = storageLocation.moduleId();
        String path = module != null ? module : "main";

        JsonObject json = loadFile(uuid);
        JsonObject moduleJson = json.has(path) ? json.get(path).getAsJsonObject() : null;

        return new DataManager.StorageData(uuid, module, moduleJson);
    }

    @Override
    public void save(DataManager.StorageData storageData) {
        if (!dataFolder.exists()) {
            dataFolder.mkdir();
        }

        UUID uuid = storageData.uuid();
        String module = storageData.moduleId();
        JsonObject moduleJson = storageData.json();
        if (moduleJson == null) {
            throw new NullPointerException("JsonObject cannot be null when saving");
        }

        JsonObject json = loadFile(uuid);
        json.add(module != null ? module : "main", moduleJson);
        try {
            FileWriter writer = new FileWriter(getUserFile(uuid));
            LushRewards.getInstance().getGson().toJson(json, writer);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private JsonObject loadFile(UUID uuid) {
        if (!dataFolder.exists()) {
            dataFolder.mkdir();
        }

        try {
            JsonElement json = JsonParser.parseReader(new FileReader(getUserFile(uuid)));
            return json.isJsonObject() ? json.getAsJsonObject() : new JsonObject();
        } catch (FileNotFoundException e) {
            return new JsonObject();
        }
    }

    private File getUserFile(UUID uuid) {
        return new File(dataFolder, uuid + ".json");
    }
}
