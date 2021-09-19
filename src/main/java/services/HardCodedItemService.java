package services;

import core.exceptions.IdAlreadyInUseException;
import core.exceptions.ItemNotAvailableException;
import model.Item;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The {@code ItemService} can be used for accessing a {@link List} of {@link Item} instances, but also
 * for adding instances to and deleting from the {@link List}.
 */
@Default
@ApplicationScoped
public class HardCodedItemService implements ItemService
{
    private List<Item> items = new ArrayList<>();

    public HardCodedItemService()
    {
        items.add(new Item(1, "Bread", new String[] {"Breakfast", "Lunch"}, "Delicious!"));
        items.add(new Item(2, "Butter", new String[] {"Breakfast", "Lunch"}, "Use it with bread"));
        items.add(new Item(3, "Honey", new String[] {"Breakfast", "Lunch"}, "Use it with bread"));
    }

    @Override
    public List<Item> getAll()
    {
        return items;
    }

    @Override
    public void addItem(Item Item)
    {
        if (items.stream().anyMatch(item -> item.getId() == Item.getId()))
        {
            throw new IdAlreadyInUseException();
        }

        items.add(Item);
    }

    @Override
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

    @Override
    public void deleteItem(int id)
    {
        List<Item> filteredItems = items.stream().filter(item -> item.getId() != id).collect(Collectors.toList());

        if (filteredItems.size() == items.size())
        {
            throw new ItemNotAvailableException();
        }

        items = filteredItems;
    }
}
