package pl.kamilmazurek.example.beans.item;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(MockitoExtension.class)
class ItemRestControllerTest {

    @Mock
    private ItemService itemService;

    @InjectMocks
    private ItemRestController itemRestController;

    @Test
    void shouldGetItem() {
        //given
        var itemId = 1L;
        var expectedItem = new ItemDTO(itemId, "Test Item");

        when(itemService.getItem(itemId)).thenReturn(Optional.of(expectedItem));

        //when
        var response = itemRestController.getItem(itemId);

        //then
        assertEquals(OK, response.getStatusCode());
        assertEquals(expectedItem, response.getBody());
    }

    @Test
    void shouldReturnNotFoundWhenItemDoesNotExist() {
        //given
        var itemId = 999L;
        when(itemService.getItem(itemId)).thenReturn(empty());

        //when
        var response = itemRestController.getItem(itemId);

        //then
        assertEquals(NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

}