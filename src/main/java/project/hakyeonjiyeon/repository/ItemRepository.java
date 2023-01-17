package project.hakyeonjiyeon.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import project.hakyeonjiyeon.entity.Item;

public interface ItemRepository  extends JpaRepository<Item, Long> {


}
