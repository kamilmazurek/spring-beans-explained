package pl.kamilmazurek.example.item;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @PostConstruct
    public void init() {
        // some initialization logic can be added here
        log.info("ItemService has been initialized");
    }

    public Optional<ItemDTO> getItem(Long id) {
        return itemRepository.findById(id).map(ItemDTO::fromEntity);
    }

    @PreDestroy
    public void cleanup() {
        log.info("ItemService is shutting down");
        // some cleanup logic can be added here
    }

}
