package com.ncl.backend.service.impl;

import com.ncl.backend.common.Constant;
import com.ncl.backend.entity.Image;
import com.ncl.backend.entity.Post;
import com.ncl.backend.exception.NotFoundException;
import com.ncl.backend.model.PostCreatedModel;
import com.ncl.backend.model.PostServiceDTO;
import com.ncl.backend.model.ServiceResult;
import com.ncl.backend.repository.ImageRepository;
import com.ncl.backend.repository.PostRepository;
import com.ncl.backend.service.PostSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        List<Post> postList = postRepository.findAllByTypeContains(Constant.HOMEPAGE_SERVICE);
        return getServiceResult(postList);
    }

    @Override
    public ServiceResult getOnePost(Long id) throws NotFoundException {
        if (!postRepository.existsById(id)) {
            throw new NotFoundException(Constant.POST_NOT_FOUND);
        }
        PostCreatedModel finalPost = new PostCreatedModel();
        finalPost.setPost(postRepository.findById(id).get());
        finalPost.setList(imageRepository.findAllByPostId(id));
        return new ServiceResult(finalPost, ServiceResult.SUCCESS, Constant.EMPTY);
    }

    @Override
    public ServiceResult createPost(PostCreatedModel postCreatedModel) {
        postRepository.save(postCreatedModel.getPost());
        List<Image> listImage = postCreatedModel.getList();
        int len = listImage.size();
        listImage.get(0).setType(Constant.COVER_IMAGE);
        for (int i = 0; i < len; i++) {
            imageRepository.save(listImage.get(i));
        }
        return new ServiceResult(postRepository.findAll(), ServiceResult.SUCCESS, Constant.CREATE_SUCCESS);
    }

    @Override
    @Transactional
    public ServiceResult editPost(PostCreatedModel postCreatedModel) throws NotFoundException {
        Long postId = postCreatedModel.getPost().getId();
        if (!postRepository.existsById(postId)) {
            throw new NotFoundException(Constant.POST_NOT_FOUND);
        }
        imageRepository.deleteAllByPostId(postId);
        postRepository.save(postCreatedModel.getPost());
        List<Image> listImage = postCreatedModel.getList();
        for (Image i : listImage) {
            imageRepository.save(i);
        }
        return new ServiceResult(null, ServiceResult.SUCCESS, Constant.EDIT_SUCCESS);
    }

    @Override
    public ServiceResult getAllEvent() {
        List<Post> postList = postRepository.findAllByType(Constant.EVENT);
        return getServiceResult(postList);

    }

    @Autowired
    private ImageRepository imageRepository;

    private ServiceResult getServiceResult(List<Post> postList) {
        List<PostServiceDTO> postServiceDTOList = new ArrayList<>();
        for (Post p : postList) {
            String img = Constant.EMPTY;
            Image image = imageRepository.findByPostIdAndType(p.getId(), Constant.COVER_IMAGE);
            if (image != null)
                img = image.getImg();
            PostServiceDTO postReturned = new PostServiceDTO(p, img);
            postServiceDTOList.add(postReturned);
        }
        return new ServiceResult(postServiceDTOList, ServiceResult.SUCCESS, Constant.EMPTY);
    }

}
