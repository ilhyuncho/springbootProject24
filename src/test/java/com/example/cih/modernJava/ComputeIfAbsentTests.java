package com.example.cih.modernJava;

import org.junit.jupiter.api.Test;

import java.util.*;

public class ComputeIfAbsentTests {

    private Map<String, List<Post>> arrange(List<Post> postList) {
        Map<String, List<Post>> result = new HashMap<>();

        postList.forEach(post -> {
            if (result.get(post.getBoardName()) == null) {
                result.put(post.getBoardName(), new ArrayList<>());
            }
            result.get(post.getBoardName()).add(post);
        });

        // computeIfAbsent 를 활용
        postList.forEach(post -> result.computeIfAbsent(post.getBoardName(), k -> new ArrayList<>())
                .add(post)
        );

        return result;
    }

    @Test
    public void test() {
        List<Post> postList = Arrays.asList(
                Post.builder().boardName("자게").postId(1).build(),
                Post.builder().boardName("자게").postId(2).build(),
                Post.builder().boardName("유게").postId(3).build(),
                Post.builder().boardName("유게").postId(4).build(),
                Post.builder().boardName("유게").postId(5).build(),
                Post.builder().boardName("핫게").postId(6).build()
        );
        Map<String, List<Post>> result = arrange(postList);

        for (Map.Entry<String, List<Post>> stringListEntry : result.entrySet()) {
            System.out.println("key:" + stringListEntry.getKey());
            for (Post post : stringListEntry.getValue()) {
                System.out.println(post.getPostId());

            }
        }
    }

}
