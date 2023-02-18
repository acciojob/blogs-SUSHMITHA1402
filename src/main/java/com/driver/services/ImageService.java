package com.driver.services;

import com.driver.models.*;
import com.driver.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {

    @Autowired
    BlogRepository blogRepository2;
    @Autowired
    ImageRepository imageRepository2;

    public Image addImage(Integer blogId, String description, String dimensions){
        //add an image to the blog
        Blog blog = blogRepository2.findById(blogId).get();

        Image image = new Image();

        image.setDescription(description);
        image.setDimensions(dimensions);
        image.setBlog(blog);

        List<Image> blogImages = blog.getImageList();
        blogImages.add(image);
        blog.setImageList(blogImages);

        blogRepository2.save(blog);

        return image;

    }

    public void deleteImage(Integer id){
        Image image = imageRepository2.findById(id).get();
        Blog blog = image.getBlog();
        List<Image> images = blog.getImageList();
        images.remove(images.indexOf(image));
        blog.setImageList(images);
        imageRepository2.deleteById(id);
        blogRepository2.save(blog);

    }

    public int countImagesInScreen(Integer id, String screenDimensions) {
        //Find the number of images of given dimensions that can fit in a screen having `screenDimensions`
        Image image = imageRepository2.findById(id).get();
        String imageDimensions = image.getDimensions();
        int imageX = imageDimensions.indexOf('X');
        int imageArea = (Integer.parseInt(imageDimensions.substring(0,imageX)))*(Integer.parseInt(imageDimensions.substring(imageX+1)));
        int screenX = screenDimensions.indexOf('X');
        int screenArea = (Integer.parseInt(screenDimensions.substring(0,screenX)))*(Integer.parseInt(screenDimensions.substring(screenX+1)));
        int out = screenArea/imageArea;
        return out;
    }
}
