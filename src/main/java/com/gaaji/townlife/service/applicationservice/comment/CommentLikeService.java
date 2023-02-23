package com.gaaji.townlife.service.applicationservice.comment;

public interface CommentLikeService {

    void like(String authId, String townLifeId, String commentId);
    void unlike(String authId, String townLifeId, String commentId);

}
