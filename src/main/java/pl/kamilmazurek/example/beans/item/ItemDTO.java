package pl.kamilmazurek.example.beans.item;

public record ItemDTO(Long id, String name) {

    public static ItemDTO fromEntity(ItemEntity entity) {
        return new ItemDTO(entity.getId(), entity.getName());
    }

}
