package pl.kamilmazurek.example.beans.item;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    @Test
    void shouldGetItem() {
        //given
        var id = 1L;
        var entity = mock(ItemEntity.class);
        when(entity.getId()).thenReturn(id);
        when(entity.getName()).thenReturn("Test Item");

        when(itemRepository.findById(id)).thenReturn(Optional.of(entity));

        //when
        var result = itemService.getItem(id);

        //then
        assertTrue(result.isPresent());
        assertEquals(id, result.get().id());
        assertEquals("Test Item", result.get().name());
    }

    @Test
    void shouldReturnEmptyWhenItemDoesNotExist() {
        //given
        var id = 999L;
        when(itemRepository.findById(id)).thenReturn(Optional.empty());

        //when
        var result = itemService.getItem(id);

        //then
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldExecuteInitWithoutExceptions() {
        assertDoesNotThrow(() -> itemService.init());
    }

    @Test
    void shouldExecuteCleanupWithoutExceptions() {
        assertDoesNotThrow(() -> itemService.cleanup());
    }

}