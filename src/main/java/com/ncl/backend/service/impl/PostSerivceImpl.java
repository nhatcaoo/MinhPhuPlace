package com.ncl.backend.service.impl;
import com.ncl.backend.common.Constant;
import com.ncl.backend.entity.Image;
import com.ncl.backend.entity.Post;
import com.ncl.backend.exception.NotFoundException;
import com.ncl.backend.model.PostServiceDTO;
import com.ncl.backend.model.ServiceResult;
import com.ncl.backend.repository.PostRepository;
import com.ncl.backend.service.PostSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostSerivceImpl implements PostSerivce {
    @Autowired
    private PostRepository postRepository;

    @Override
    public ServiceResult getAllServicePost() {
        List<Post> postList = postRepository.findAllByType(Constant.SERVICE);
        List<PostServiceDTO> postServiceDTOList = null;
        for (Post p : postList) {
            String img = Constant.EMPTY;
            List<Image> coverImages = p.getImages().stream().filter(i -> i.getType().equals(Constant.COVER_IMAGE))
                    .collect(Collectors.toList());
            if (!coverImages.isEmpty())
                img = coverImages.get(0).getImg();
            PostServiceDTO postReturned = new PostServiceDTO(p, img);
            postServiceDTOList.add(postReturned);
        }
        return new ServiceResult(postServiceDTOList, ServiceResult.SUCCESS, Constant.EMPTY);
    }

    @Override
    public ServiceResult getOnePost(Long id) throws NotFoundException {
        if (!postRepository.existsById(id)) {
            throw new NotFoundException(Constant.POST_NOT_FOUND);
        }
        Post p = postRepository.findById(id).get();
        return new ServiceResult(p, ServiceResult.SUCCESS, Constant.EMPTY);
    }

    @Override
    public ServiceResult createPost(Post post) {
        postRepository.save(post);
        return new ServiceResult(postRepository.findAll(), ServiceResult.SUCCESS, Constant.CREATE_SUCCESS);
    }

    @Override
    public ServiceResult editPost(Post post) throws NotFoundException {
        Optional<Post> postOptional = postRepository.findById(post.getId());
        if (!postOptional.isPresent()) throw new NotFoundException(Constant.POST_NOT_FOUND);
        Post postFinal = postRepository.saveAndFlush(postOptional.get());
        return new ServiceResult(postFinal, ServiceResult.SUCCESS, Constant.EDIT_SUCCESS);
    }

}
