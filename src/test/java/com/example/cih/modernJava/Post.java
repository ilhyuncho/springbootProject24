package com.example.cih.modernJava;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Post {
    private String boardName;
    private int postId;
}
