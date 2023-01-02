package com.babak.iojavaintake.tedtalk;

import com.babak.iojavaintake.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_ted_talk")
@Data
@NoArgsConstructor
public class TedTalk extends BaseEntity {

    private String title;
    private String author;
    private String date;
    private Long views;
    private Long likes;
    private String link;
}
