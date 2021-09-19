package services;

import core.exceptions.IdAlreadyInUseException;
import core.exceptions.ItemNotAvailableException;
import model.Item;

import java.util.List;

public interface ItemService
{
    /**
     * Return the full {@link List} of {@link Item} instances.
     *
     * @return The full {@link List} of {@link Item} instances.
     */
    List<Item> getAll();

    /**
     * Add an item to the {@link List} of items.
     * <p>
     * Note that the newly added item should have an unique Id.
     *
     * @param Item The {@link Item} to be added
     * @throws IdAlreadyInUseException Thrown if the Id is not unique
     */
    void addItem(Item Item);

    /**
     * Return a specific {@link Item} with the given Id.
     *
     * @param id The Id of the {@link Item} to be returned
     * @throws ItemNotAvailableException Thrown if there is no {@link Item} for the given Id
     */
    Item getItem(int id);

    /**
     * Delete a specific {@link Item} with the given Id.
     *
     * @throws ItemNotAvailableException Thrown if there is no {@link Item} for the given Id
     */
    void deleteItem(int id);
}
