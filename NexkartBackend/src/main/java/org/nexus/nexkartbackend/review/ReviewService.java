//package org.nexus.nexkartbackend.review;
//import java.util.NoSuchElementException;
//
//import org.nexus.nexkartbackend.Repository.ReviewRepository;
//import org.nexus.nexkartbackend.entity.Review;
//import org.nexus.nexkartbackend.exception.ReviewNotFoundException;
//import org.nexus.nexkartbackend.paging.PagingAndSortingHelper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//
//@Service
//public class ReviewService {
//    public static final int REVIEWS_PER_PAGE = 5;
//
//    @Autowired
//    private ReviewRepository repo;
//
//    public void listByPage(int pageNum, PagingAndSortingHelper helper) {
//        helper.listEntities(pageNum, REVIEWS_PER_PAGE, repo);
//    }
//
//    public Review get(Integer id) throws ReviewNotFoundException {
//        try {
//            return repo.findById(id).get();
//        } catch (NoSuchElementException ex) {
//            throw new ReviewNotFoundException("Could not find any reviews with ID " + id);
//        }
//    }
//
//    public void save(Review reviewInForm) {
//        Review reviewInDB = repo.findById(reviewInForm.getId()).get();
//        reviewInDB.setHeadline(reviewInForm.getHeadline());
//        reviewInDB.setComment(reviewInForm.getComment());
//
//        repo.save(reviewInDB);
//    }
//
//    public void delete(Integer id) throws ReviewNotFoundException {
//        if (!repo.existsById(id)) {
//            throw new ReviewNotFoundException("Could not find any reviews with ID " + id);
//        }
//
//        repo.deleteById(id);
//    }
//}