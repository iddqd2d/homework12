package com.youtube;

import com.google.api.client.util.DateTime;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class Comment {
    private String author;
    private String message;
    private long likes;
    private DateTime publishedDate;
    private boolean edited;
}
