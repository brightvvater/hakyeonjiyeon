package project.hakyeonjiyeon.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String title;

    private LocalDateTime postDate;

    private LocalDateTime modifiedDate;

    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    private void setId(Long id) {
        this.id = id;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    private void setPostDate(LocalDateTime postDate) {
        this.postDate = postDate;
    }

    private void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    private void setContent(String content) {
        this.content = content;
    }

    private void setBoard(Board board) {
        this.board = board;
    }

    private void setMember(Member member) {
        this.member = member;
    }

    @Builder
    public Post(String title, LocalDateTime postDate, String content, Board board, Member member) {
        this.title = title;
        this.postDate = postDate;
        this.content = content;
        this.board = board;
        this.member = member;
    }
}
