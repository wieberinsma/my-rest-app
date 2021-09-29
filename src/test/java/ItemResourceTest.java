import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

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

//    @InjectMocks
//    private ItemResource sut;
//
//    @Mock
//    private ItemService itemService;
//
//    @Test
//    public void getTextItemsTest()
//    {
//        // Arrange
//
//        // Act
//        var textItems = sut.getTextItems();
//
//        // Assert
//        assertEquals("bread, butter", textItems);
//    }
}
