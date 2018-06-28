package com.steerlean.service;

import com.steerlean.model.Course;
import com.steerlean.model.Student;
import com.steerlean.repo.StudentRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
public class StudentServiceTest {

    @Mock
    private StudentRepository mockStudentRepository;

    @InjectMocks
    private StudentService studentService;

    @Test
    public void testRetrieveAllStudents() {
        Map<String, Student> studentsMaps = new HashMap<>();
        studentsMaps.put("1", new Student("1", null, null, null));
        Mockito.when(mockStudentRepository.findAllStudents()).thenReturn(studentsMaps);
        List<Student> students = studentService.retrieveAllStudents();
        Assert.assertEquals(1, students.size());
        Assert.assertEquals("2", students.get(0).getId());
    }

    @Test
    public void testRetrieveStudent() {
        Student mockStudent = Mockito.mock(Student.class);
        Mockito.when(mockStudentRepository.findStudent("1")).thenReturn(mockStudent);
        Student student = studentService.retrieveStudent("1");
        Assert.assertEquals(mockStudent, student);
    }

    @Test
    public void testRetrieveCoursesWhenNoValidStudent() {
        StudentService spyStudentService = Mockito.spy(studentService);
        Mockito.when(spyStudentService.retrieveStudent("1")).thenReturn(null);
        List<Course> courses = spyStudentService.retrieveCourses("1");
        Assert.assertNull(courses);
    }

    @Test
    public void testRetrieveCoursesWhenValidStudent() {
        StudentService spyStudentService = Mockito.spy(studentService);
        Student mockStudent = Mockito.mock(Student.class);
        List<Course> mockCourses = Mockito.mock(List.class);
        Mockito.when(mockStudent.getCourses()).thenReturn(mockCourses);
        Mockito.when(spyStudentService.retrieveStudent("1")).thenReturn(mockStudent);
        List<Course> courses = spyStudentService.retrieveCourses("1");
        Assert.assertEquals(mockCourses, courses);
    }

    @Test
    public void testRetrieveCourseWhenNoValidStudent() {
        StudentService spyStudentService = Mockito.spy(studentService);
        Mockito.when(spyStudentService.retrieveStudent("1")).thenReturn(null);
        Course course = spyStudentService.retrieveCourse("1", null);
        Assert.assertNull(course);
    }

    @Test
    public void testRetrieveCourseWhenStudentNotEnrolledInAnyCourse() {
        StudentService spyStudentService = Mockito.spy(studentService);
        Student mockStudent = Mockito.mock(Student.class);
        List<Course> stubCourses = new ArrayList<>();
        Mockito.when(mockStudent.getCourses()).thenReturn(stubCourses);
        Mockito.when(spyStudentService.retrieveStudent("1")).thenReturn(mockStudent);

        Course course = spyStudentService.retrieveCourse("1", "courseId");

        Assert.assertNull(course);
    }

    @Test
    public void testRetrieveCourseWhenStudentNotEnrolled() {
        StudentService spyStudentService = Mockito.spy(studentService);
        Student mockStudent = Mockito.mock(Student.class);
        List<Course> stubCourses = new ArrayList<>();
        stubCourses.add(new Course("agri", null, null, null));
        Mockito.when(mockStudent.getCourses()).thenReturn(stubCourses);
        Mockito.when(spyStudentService.retrieveStudent("1")).thenReturn(mockStudent);

        Course course = spyStudentService.retrieveCourse("1", "maths");

        Assert.assertNull(course);
    }

    @Test
    public void testRetrieveCourse() {
        StudentService spyStudentService = Mockito.spy(studentService);
        Student mockStudent = Mockito.mock(Student.class);
        List<Course> stubCourses = new ArrayList<>();
        stubCourses.add(new Course("agri", null, null, null));
        Mockito.when(mockStudent.getCourses()).thenReturn(stubCourses);
        Mockito.when(spyStudentService.retrieveStudent("1")).thenReturn(mockStudent);

        Course course = spyStudentService.retrieveCourse("1", "agri");

        Assert.assertEquals("agri", course.getId());
    }

    @Test
    public void testAddCourseWhenNoStudent() {
        StudentService spyStudentService = Mockito.spy(studentService);
        Mockito.when(spyStudentService.retrieveStudent("1")).thenReturn(null);
        Course course = spyStudentService.addCourse("1", null);
        Assert.assertNull(course);
    }

    @Test
    public void testAddCourse() {
        StudentService spyStudentService = Mockito.spy(studentService);
        List<Course> courses = new ArrayList<>();
        courses.add(new Course("agri", null, null, null));
        Student stubStudent = new Student("1", null, null, courses);
        Mockito.when(spyStudentService.retrieveStudent("1")).thenReturn(stubStudent);
        Course mockNewCourse = Mockito.mock(Course.class);
        Course course = spyStudentService.addCourse("1", mockNewCourse);

        Mockito.verify(mockNewCourse, Mockito.times(1)).setId(Mockito.anyString());
        Assert.assertEquals(mockNewCourse, course);
        Assert.assertEquals(2, stubStudent.getCourses().size());
        Assert.assertEquals(mockNewCourse, stubStudent.getCourses().get(1));
    }

    @Test
    public void testAddStudent() {
        Student mockStudent = Mockito.mock(Student.class);
        studentService.addStudent(mockStudent);

        Mockito.verify(mockStudent, Mockito.times(1)).setId(Mockito.anyString());
        Mockito.verify(mockStudentRepository, Mockito.times(1)).saveStudent(mockStudent);
    }

}
