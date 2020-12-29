package com.ncl.backend.service.impl;

import com.ncl.backend.common.Constant;
import com.ncl.backend.entity.Post;
import com.ncl.backend.entity.PostImage;
import com.ncl.backend.entity.Preface;
import com.ncl.backend.exception.NotFoundException;
import com.ncl.backend.exception.NullObjectException;
import com.ncl.backend.model.PostCreatedModel;
import com.ncl.backend.model.PostServiceDTO;
import com.ncl.backend.model.ServiceResult;
import com.ncl.backend.repository.PostImageRepository;
import com.ncl.backend.repository.PostRepository;
import com.ncl.backend.repository.PrefaceRepository;
import com.ncl.backend.service.PostSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostSerivceImpl implements PostSerivce {
    @Autowired
    private PostRepository postRepository;

    @Override
    public ServiceResult getAllServicePost() {
        List<Post> postList = postRepository.findAllByTypeContains(Constant.SERVICE);
        return getServiceResult(postList);
    }

    @Override
    public ServiceResult getHomepageServicePost() {
        List<Post> postList = postRepository.findAllByTypeContains(Constant.POST_HOMEPAGE_SERVICE);
        return getServiceResult(postList);
    }

    @Override
    public ServiceResult getOnePost(Long id) throws NotFoundException {
        if (!postRepository.existsById(id)) {
            throw new NotFoundException(Constant.POST_NOT_FOUND);
        }
        PostCreatedModel finalPost = new PostCreatedModel();
        finalPost.setPost(postRepository.findById(id).get());
        finalPost.setList(postImageRepository.findAllByPostId(id));
        return new ServiceResult(finalPost, ServiceResult.SUCCESS, Constant.EMPTY);
    }

    @org.springframework.transaction.annotation.Transactional(rollbackFor = Throwable.class)
    @Override
    public ServiceResult createPost(PostCreatedModel postCreatedModel) throws NullObjectException {
        if (Constant.POST_HOMEPAGE_SERVICE.equalsIgnoreCase(postCreatedModel.getPost().getType())) {
            return new ServiceResult(null, ServiceResult.FAIL, "Không thể tạo quá 3 major post");
        }
        Post p = postRepository.saveAndFlush(postCreatedModel.getPost());

        List<PostImage> listImage = null;

        listImage = postCreatedModel.getList();

        if (!listImage.isEmpty())
            listImage.get(0).setType(Constant.COVER_IMAGE);
        for (PostImage i : listImage) {
            i.setPost(p);
            postImageRepository.save(i);
        }
        return new ServiceResult(null, ServiceResult.SUCCESS, Constant.CREATE_POST_SUCCESS);
    }

    @Override
    @org.springframework.transaction.annotation.Transactional(rollbackFor = Throwable.class)
    public ServiceResult editPost(PostCreatedModel postCreatedModel) throws NotFoundException {
        Long postId = postCreatedModel.getPost().getId();
        if (!postRepository.existsById(postId)) {
            throw new NotFoundException(Constant.POST_NOT_FOUND);
        }
        Post post = postCreatedModel.getPost();
        if (Constant.POST_HOMEPAGE_SERVICE.equalsIgnoreCase(postRepository.findById(postId).get().getType()))
            post.setType(Constant.POST_HOMEPAGE_SERVICE);
        postImageRepository.deleteAllByPostId(postId);
        postRepository.save(post);
        List<PostImage> listImage = postCreatedModel.getList();
        if (!listImage.isEmpty())
            listImage.get(0).setType(Constant.COVER_IMAGE);
        for (PostImage i : listImage) {
            Post p = new Post();
            p.setId(postId);
            i.setPost(p);
            postImageRepository.save(i);
        }
        return new ServiceResult(null, ServiceResult.SUCCESS, Constant.EDIT_SUCCESS);
    }

    @Override
    public ServiceResult getAllEvent() {
        List<Post> postList = postRepository.findAllByType(Constant.EVENT);
        return getServiceResult(postList);
    }

    @Override
    public void initMajorPost() {

        for (int i = 0; i < 3; i++) {
            Post post = new Post();
            post.setType(Constant.POST_HOMEPAGE_SERVICE);
            post.setBrief("");
            post.setDescription("");
            post.setTitle("");
            post.setShortDescription("");
            postRepository.save(post);
        }
    }

    @org.springframework.transaction.annotation.Transactional(rollbackFor = Throwable.class)
    @Override
    public ServiceResult deletePost(Long id) throws NotFoundException {
        if (!postRepository.existsById(id)) {
            throw new NotFoundException(Constant.POST_NOT_FOUND);
        }
        Post p = postRepository.findById(id).get();
        if (Constant.POST_HOMEPAGE_SERVICE.equalsIgnoreCase(p.getType()))
            return new ServiceResult(null, ServiceResult.FAIL, "Không thể xoá vì bài viết nằm ở trang chủ");
        postImageRepository.deleteAllByPostId(id);
        postRepository.deleteById(id);
        return new ServiceResult(null, ServiceResult.SUCCESS, Constant.DELETE_SUCCESS);
    }

    @Override
    public ServiceResult getAllMinorService() {
        List<Post> postList = postRepository.findAllByType(Constant.SERVICE);
        return getServiceResult(postList);
    }

    @Autowired
    private PrefaceRepository prefaceRepository;

    @Override
    public ServiceResult editPreface(Preface post) throws NotFoundException {
        post.setId(1L);
        if (!prefaceRepository.existsById(post.getId())) {
            throw new NotFoundException(Constant.POST_NOT_FOUND);
        }
        prefaceRepository.save(post);
        return new ServiceResult(null, ServiceResult.SUCCESS, Constant.EDIT_SUCCESS);

    }

    @Override
    public ServiceResult getPreface() {
        Preface list = prefaceRepository.findById(1l).get();
        return new ServiceResult(list, ServiceResult.SUCCESS, "");
    }

    @Autowired
    private PostImageRepository postImageRepository;

    private ServiceResult getServiceResult(List<Post> postList) {
        List<PostServiceDTO> postServiceDTOList = new ArrayList<>();
        for (Post p : postList) {
            String img = Constant.EMPTY;
            PostImage postImage = postImageRepository.findByPostIdAndType(p.getId(), Constant.COVER_IMAGE);
            if (postImage != null)
                img = postImage.getImg();

            PostServiceDTO postReturned = new PostServiceDTO(p, img);
            postServiceDTOList.add(postReturned);
        }
        return new ServiceResult(postServiceDTOList, ServiceResult.SUCCESS, Constant.EMPTY);
    }

}
