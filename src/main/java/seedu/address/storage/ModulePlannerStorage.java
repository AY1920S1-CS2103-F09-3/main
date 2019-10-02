package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyModulePlanner;

/**
 * Represents a storage for {@link seedu.address.model.ModulePlanner}.
 */
public interface ModulePlannerStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getModulePlannerFilePath();

    /**
     * Returns ModulePlanner data as a {@link ReadOnlyModulePlanner}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyModulePlanner> readModulePlanner() throws DataConversionException, IOException;

    /**
     * @see #getModulePlannerFilePath()
     */
    Optional<ReadOnlyModulePlanner> readModulePlanner(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyModulePlanner} to the storage.
     * @param ModulePlanner cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveModulePlanner(ReadOnlyModulePlanner ModulePlanner) throws IOException;

    /**
     * @see #saveModulePlanner(ReadOnlyModulePlanner)
     */
    void saveModulePlanner(ReadOnlyModulePlanner ModulePlanner, Path filePath) throws IOException;

}
