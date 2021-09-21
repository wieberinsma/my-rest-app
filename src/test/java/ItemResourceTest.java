import controllers.ItemResource;
import core.exceptions.IdAlreadyInUseException;
import core.exceptions.ItemNotAvailableException;
import model.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import services.ItemService;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Nodig om Mockito te laten werken met jUnit 5 wanneer je in je test automatisch wil injecten; zie {@link InjectMocks}.
 * Je hebt hierdoor zelfs geen {@link BeforeEach} methode meer nodig, omdat Mockito de SUT _ook_ zelf initialiseert.
 *
 * De tests zijn verder hetzelfde.
 */
@ExtendWith(MockitoExtension.class)
public class ItemResourceTest
{
    private static final int ITEM_ID = 1;
    private static final int HTTP_OK = 200;
    private static final int HTTP_CREATED = 201;
    private static final int HTTP_NO_CONTENT = 204;

    @InjectMocks
    private ItemResource sut;

    @Mock
    private ItemService itemService;

    @Test
    public void getTextItemsTest()
    {
        // Arrange

        // Act
        var textItems = sut.getTextItems();

        // Assert
        assertEquals("bread, butter", textItems);
    }

    @Test
    void getJsonItemsTest()
    {
        // Arrange
        var itemsToReturn = new ArrayList<Item>();

        when(itemService.getAll()).thenReturn(itemsToReturn);

        // Act
        var response = sut.getJsonItems();

        // Assert
        assertEquals(HTTP_OK, response.getStatus());
        assertEquals(itemsToReturn, response.getEntity());
    }

    @Test
    void getJsonItemsCallsGetAll()
    {
        // Arrange

        // Act
        sut.getJsonItems();

        // Assert
        verify(itemService).getAll();
    }

    @Test
    void addItemsCallsAddItemOnService()
    {
        // Arrange
        var item = new Item(37, "Chocolate spread", new String[] {"Breakfast, Lunch"}, "Not to much");

        // Act
        Response response = sut.addItem(item);

        // Assert
        verify(itemService).addItem(item);
    }

    @Test
    void addItemsReturnsHttp201()
    {
        // Arrange
        var item = new Item(37, "Chocolate spread", new String[] {"Breakfast, Lunch"}, "Not to much");

        // Act
        var response = sut.addItem(item);

        // Assert
        assertEquals(HTTP_CREATED, response.getStatus());
    }

    @Test
    void addItemsLetsIdAlreadyInUseExceptionPass()
    {
        // Arrange
        var item = new Item(37, "Chocolate spread", new String[] {"Breakfast, Lunch"}, "Not to much");
        doThrow(IdAlreadyInUseException.class).when(itemService).addItem(item);

        // Act & Assert
        assertThrows(IdAlreadyInUseException.class, () -> sut.addItem(item));
    }

    @Test
    void getItemCallsGetItemOnService()
    {
        // Arrange
        var item = new Item(ITEM_ID, "Chocolate spread", new String[] {"Breakfast, Lunch"}, "Not to much");

        // Act
        sut.getItem(ITEM_ID);

        // Assert
        verify(itemService).getItem(ITEM_ID);
    }

    @Test
    void getItemReturnsObjectFromServiceAsEntity()
    {
        // Arrange
        var item = new Item(ITEM_ID, "Chocolate spread", new String[] {"Breakfast, Lunch"}, "Not to much");
        when(itemService.getItem(ITEM_ID)).thenReturn(item);

        // Act
        Response response = sut.getItem(ITEM_ID);

        // Assert
        assertEquals(item, response.getEntity());
    }

    @Test
    void getItemReturnsHttp200()
    {
        // Arrange
        var item = new Item(ITEM_ID, "Chocolate spread", new String[] {"Breakfast, Lunch"}, "Not to much");
        when(itemService.getItem(ITEM_ID)).thenReturn(item);

        // Act
        Response response = sut.getItem(ITEM_ID);

        // Assert
        assertEquals(HTTP_OK, response.getStatus());
        assertEquals(item, response.getEntity());
    }

    @Test
    void getItemLetsItemNotAvailableExceptionPass()
    {
        // Arrange
        doThrow(ItemNotAvailableException.class).when(itemService).getItem(ITEM_ID);

        // Act & Assert
        assertThrows(ItemNotAvailableException.class, () -> sut.getItem(ITEM_ID));
    }

    @Test
    void deleteItemCallsDeleteItemOnService()
    {
        // Arrange

        // Act
        sut.deleteItem(ITEM_ID);

        // Assert
        verify(itemService).deleteItem(ITEM_ID);
    }

    @Test
    void deleteItemsReturnsHttp204()
    {
        // Arrange

        // Act
        var response = sut.deleteItem(ITEM_ID);

        // Assert
        assertEquals(HTTP_NO_CONTENT, response.getStatus());
    }

    @Test
    void deleteItemLetsItemNotAvailableExceptionPass()
    {
        // Arrange
        doThrow(ItemNotAvailableException.class).when(itemService).deleteItem(ITEM_ID);

        // Act & Assert
        assertThrows(ItemNotAvailableException.class, () -> sut.deleteItem(ITEM_ID));
    }
}
