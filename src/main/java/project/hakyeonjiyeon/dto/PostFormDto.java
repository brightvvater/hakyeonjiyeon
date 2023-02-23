package project.hakyeonjiyeon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostFormDto {

    private Long postId;

    private String title;

    private String content;

    private LocalDateTime postDate;

    private String nickName;

    private String boardName;

}
