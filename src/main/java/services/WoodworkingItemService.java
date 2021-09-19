package services;

import core.exceptions.IdAlreadyInUseException;
import core.exceptions.ItemNotAvailableException;
import model.Item;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Alternative
@ApplicationScoped
public class WoodworkingItemService implements ItemService
{
    private List<Item> items = new ArrayList<>();

    public WoodworkingItemService()
    {
        items.add(new Item(1, "Saw", new String[] {"Carpentry"}, "Keep it sharp"));
        items.add(new Item(2, "Chisel", new String[] {"Carpentry"}, "Keep it sharp"));
        items.add(new Item(3, "Plane", new String[] {"Carpentry"}, "Keep it sharp"));
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
