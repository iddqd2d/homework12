package com.youtube;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.CommentSnippet;
import com.google.api.services.youtube.model.CommentThread;
import com.google.api.services.youtube.model.CommentThreadListResponse;
import com.google.api.services.youtube.model.VideoListResponse;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import static com.youtube.YoutubeService.getService;

public class MyParseYoutube {
    private static final String DEVELOPER_KEY = "AIzaSyDBoAaMpOt0hDvr7wp_Iu2UFL0ZPb3c8oQ";
    private YouTube youtubeService;
    private String videoId;
    private String captionVideo;
    private List<Comment> comments;


    public MyParseYoutube(String url) throws GeneralSecurityException, IOException {
        youtubeService = getService();
        comments = new ArrayList<>();
        this.videoId = url.replace("https://www.youtube.com/watch?v=", "");
        parseCaptionVideo();
        parseComments();
    }

    public void parseCaptionVideo() throws IOException {
        YouTube.Videos.List request = youtubeService.videos()
                .list("snippet");
        VideoListResponse response = request.setKey(DEVELOPER_KEY)
                .setId(videoId)
                .execute();
        captionVideo = response.getItems().get(0).getSnippet().getTitle();
    }

    public void parseComments() throws IOException {
        YouTube.CommentThreads.List request = youtubeService.commentThreads()
                .list("snippet");
        CommentThreadListResponse response = request.setKey(DEVELOPER_KEY)
                .setMaxResults(10L)
                .setOrder("time")
                .setVideoId(videoId)
                .execute();
        for (CommentThread commentThread : response.getItems()) {
            CommentSnippet commentSnippet = commentThread.getSnippet().getTopLevelComment().getSnippet();
            Comment comment = new Comment();
            comment.setAuthor(commentSnippet.getAuthorDisplayName())
                    .setMessage(commentSnippet.getTextDisplay())
                    .setLikes(commentSnippet.getLikeCount())
                    .setPublishedDate(commentSnippet.getPublishedAt())
                    .setEdited(!commentSnippet.getPublishedAt().equals(commentSnippet.getUpdatedAt()));
            comments.add(comment);
        }
    }

    @Override
    public String toString() {
        return "Title: " + captionVideo
                + "\nComments: " + comments;
    }
}
