package com.sha.microservicecousemanagement.service;

import com.sha.microservicecousemanagement.model.Course;
import com.sha.microservicecousemanagement.model.Transaction;

import java.util.List;
//B8  tạo class CourseServiceimpl
public interface CourseService {
    List<Course> allCourse();

    Course findCourseById(Long courseId);

    List<Transaction> findTransactionsOfUser(Long userId);

    List<Transaction> findTransactionOfCourse(Long courseId);

    Transaction saveTransaction(Transaction transaction);
}
