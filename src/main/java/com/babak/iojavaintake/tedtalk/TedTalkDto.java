package com.babak.iojavaintake.tedtalk;

import com.babak.iojavaintake.base.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This dto class is used in REST endpoints as TedTalk transferable object
 * also is used- as filter model- in the query specification
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TedTalkDto extends BaseDto {

    private String title;
    private String author;
    private String date;
    private Long views;
    private Long likes;
    private String link;

    public TedTalkDto(String title, String author, Long views, Long likes) {
        this.author = author;
        this.title = title;
        this.likes = likes;
        this.views = views;
    }
}
