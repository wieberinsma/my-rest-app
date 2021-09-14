package services;

import core.exceptions.IdAlreadyInUseException;
import core.exceptions.ItemNotAvailableException;
import model.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The {@code ItemService} can be used for accessing a {@link List} of {@link Item} instances, but also
 * for adding instances to and deleting from the {@link List}.
 */
public class ItemService
{

    private List<Item> items = new ArrayList<>();

    public ItemService()
    {
        items.add(new Item(1, "Bread", new String[] {"Breakfast", "Lunch"}, "Delicious!"));
        items.add(new Item(2, "Butter", new String[] {"Breakfast", "Lunch"}, "Use it with bread"));
        items.add(new Item(3, "Honey", new String[] {"Breakfast", "Lunch"}, "Use it with bread"));
    }

    /**
     * Return the full {@link List} of {@link Item} instances.
     *
     * @return The full {@link List} of {@link Item} instances.
     */
    public List<Item> getAll()
    {
        return items;
    }

    /**
     * Add an item to the {@link List} of items.
     * <p>
     * Note that the newly added item should have an unique Id.
     *
     * @param itemDTO The {@link Item} to be added
     * @throws IdAlreadyInUseException Thrown if the Id is not unique
     */
    public void addItem(Item itemDTO)
    {
        if (items.stream().anyMatch(item -> item.getId() == itemDTO.getId()))
        {
            throw new IdAlreadyInUseException();
        }

        items.add(itemDTO);
    }

    /**
     * Return a specific {@link Item} with the given Id.
     *
     * @param id The Id of the {@link Item} to be returned
     * @throws ItemNotAvailableException Thrown if there is no {@link Item} for the given Id
     */
    public Item getItem(int id)
    {
        Optional<Item> requestedItem = items.stream().filter(item -> item.getId() == id).findFirst();

        if (requestedItem.isPresent())
        {
            return requestedItem.get();
        }
        else
        {
            throw new ItemNotAvailableException();
        }
    }

    /**
     * Delete a specific {@link Item} with the given Id.
     *
     * @throws ItemNotAvailableException Thrown if there is no {@link Item} for the given Id
     */
    public void deleteItem(int id)
    {
        Optional<Item> itemForName = items.stream().filter(item -> item.getId() == id).findFirst();

        List<Item> filteredItems = items.stream().filter(item -> item.getId() != id).collect(Collectors.toList());

        if (filteredItems.size() == items.size())
        {
            throw new ItemNotAvailableException();
        }

        items = filteredItems;
    }
}
