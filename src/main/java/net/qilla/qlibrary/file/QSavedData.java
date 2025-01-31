package net.qilla.qlibrary.file;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A generic saved data manager to handle creation, saving, and resetting of data files.
 * Supports JSON serialization/deserialization using Gson.
 *
 * @param <T> The type of data being managed.
 */

public abstract class QSavedData<T> {

    private static final Logger LOGGER = Bukkit.getLogger();

    private final Gson gson;
    private final Type type;
    private final String defaultLocation;
    private final Path filePath;
    private final T storedData;

    /**
     * Initializes a new QSavedData instance for managing file-based resources.
     *
     * @param gson            The Gson instance for JSON serialization.
     * @param type            The Type of the data object to be managed.
     * @param defaultResource The path to the default resource (used when creating the file).
     * @param filePath        The file location on the system.
     * @param storedData      The initial data to save.
     */

    public QSavedData(@NotNull Gson gson, @NotNull Type type, @NotNull String defaultResource, @NotNull Path filePath, @Nullable T storedData) {
        Preconditions.checkNotNull(gson, "Gson cannot be null");
        Preconditions.checkNotNull(type, "Type cannot be null");
        Preconditions.checkNotNull(defaultResource, "Default resource cannot be null");
        Preconditions.checkNotNull(filePath, "File path cannot be null");

        this.gson = gson;
        this.type = type;
        this.defaultLocation = defaultResource;
        this.filePath = filePath;
        this.storedData = storedData;

        this.ensureFileExists();
    }

    /**
     * Ensures the target file exists. If not, the default resource is copied to the file path.
     */

    private void ensureFileExists() {
        if(Files.exists(filePath)) return;

        try {
            Files.createDirectories(filePath.getParent());

            URL resourceURL = getClass().getClassLoader().getResource(defaultLocation);
            if(resourceURL == null) throw new FileNotFoundException("Default resource not found: " + defaultLocation);

            try(InputStream inputStream = resourceURL.openStream()) {
                Files.copy(inputStream, this.filePath);
            }

        } catch(IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to create file at " + filePath, e);
        }
    }

    /**
     * Resets the file by moving the existing file to a backup with a `.old` suffix and recreates the file from the default resource.
     */

    public void resetFile() {
        try {
            if(Files.exists(filePath)) {
                Path backupPath = filePath.resolveSibling(filePath.getFileName() + ".old");
                Files.move(filePath, backupPath, StandardCopyOption.REPLACE_EXISTING);
            }
            ensureFileExists();
        } catch(IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to reset file at " + filePath, e);
        }
    }

    /**
     * Saves the current `storedData` to the file in JSON format.
     */

    public void save() {
        try(BufferedWriter writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8)) {
            gson.toJson(storedData, type, writer);
        } catch(IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to save file at " + filePath, e);
        }
    }

    /**
     * Returns the currently stored data.
     *
     * @return The stored data.
     */

    public T getStoredData() {
        return storedData;
    }

    /**
     * Load the data from the file into the managed object. This method must be implemented by subclasses.
     */

    public abstract void load();

    /**
     * Clear or reset the managed object in memory. This method must be implemented by subclasses.
     */

    public abstract void clear();
}