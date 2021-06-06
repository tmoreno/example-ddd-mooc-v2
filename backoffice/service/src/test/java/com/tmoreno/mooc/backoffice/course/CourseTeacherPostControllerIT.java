package com.tmoreno.mooc.backoffice.course;

import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.events.CourseTeacherAddedDomainEvent;
import com.tmoreno.mooc.backoffice.mothers.CourseIdMother;
import com.tmoreno.mooc.backoffice.mothers.CourseMother;
import com.tmoreno.mooc.backoffice.mothers.TeacherIdMother;
import com.tmoreno.mooc.backoffice.mothers.TeacherMother;
import com.tmoreno.mooc.backoffice.teacher.domain.Teacher;
import com.tmoreno.mooc.backoffice.teacher.domain.TeacherRepository;
import com.tmoreno.mooc.backoffice.utils.BaseControllerIT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static com.tmoreno.mooc.backoffice.utils.ResponseAssertions.assertCreated;
import static com.tmoreno.mooc.backoffice.utils.ResponseAssertions.assertNotFound;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

public class CourseTeacherPostControllerIT extends BaseControllerIT {

    @SpyBean
    private CourseRepository courseRepository;

    @SpyBean
    private TeacherRepository teacherRepository;

    @BeforeEach
    public void setUp() {
        url += "/courses/%s/teachers";
    }

    @Test
    public void given_a_course_without_teachers_and_an_existing_teacher_when_send_post_request_then_receive_created_response_and_teacher_is_added_and_events_are_stored() {
        Course course = CourseMother.randomInDraftState();
        courseRepository.save(course);

        Teacher teacher = TeacherMother.random();
        teacherRepository.save(teacher);

        url = String.format(url, course.getId().getValue());

        ResponseEntity<String> response = post(Map.of(
            "teacherId", teacher.getId().getValue()
        ));

        assertCreated(response);

        Course persistedCourse = courseRepository.find(course.getId()).orElseThrow();
        assertThat(persistedCourse.getTeachers().size(), is(1));
        assertThat(persistedCourse.getTeachers(), hasItem(teacher.getId()));

        Teacher persistedTeacher = teacherRepository.find(teacher.getId()).orElseThrow();
        assertThat(persistedTeacher.getCourses().size(), is(1));
        assertThat(persistedTeacher.getCourses(), hasItem(course.getId()));

        verify(domainEventRepository).store(any(CourseTeacherAddedDomainEvent.class));
    }

    @Test
    public void given_not_existing_course_when_send_post_request_then_receive_not_found_response() {
        Course course = CourseMother.randomInDraftState();
        courseRepository.save(course);

        url = String.format(url, course.getId().getValue());

        ResponseEntity<String> response = post(Map.of(
            "teacherId", TeacherIdMother.random().getValue()
        ));

        assertNotFound(response);
    }

    @Test
    public void given_not_existing_teacher_when_send_post_request_then_receive_not_found_response() {

        url = String.format(url, CourseIdMother.random().getValue());

        ResponseEntity<String> response = post(Map.of(
                "teacherId", TeacherIdMother.random().getValue()
        ));

        assertNotFound(response);
    }

    @Test
    public void should_rollback_all_database_changes_when_an_exception_happen() {
        doThrow(RuntimeException.class).when(domainEventRepository).store(any());

        Course course = CourseMother.randomInDraftState();
        courseRepository.save(course);

        Teacher teacher = TeacherMother.random();
        teacherRepository.save(teacher);

        url = String.format(url, course.getId().getValue());

        post(Map.of(
            "teacherId", teacher.getId().getValue()
        ));

        Course persistedCourse = courseRepository.find(course.getId()).orElseThrow();
        assertThat(persistedCourse.getTeachers().isEmpty(), is(true));

        Teacher persistedTeacher = teacherRepository.find(teacher.getId()).orElseThrow();
        assertThat(persistedTeacher.getCourses().isEmpty(), is(true));
    }
}
