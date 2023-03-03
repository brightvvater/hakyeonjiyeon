package project.hakyeonjiyeon.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    private String name;

    private void setId(Long id) {
        this.id = id;
    }

    private void setTitle(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "board")
    private List<Post> posts = new ArrayList<>();


    @Builder
    public Board(String name) {
        this.name = name;
    }
}
