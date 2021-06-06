package com.tmoreno.mooc.backoffice.course;

import com.tmoreno.mooc.backoffice.course.domain.Course;
import com.tmoreno.mooc.backoffice.course.domain.CourseRepository;
import com.tmoreno.mooc.backoffice.course.domain.events.CourseTeacherDeletedDomainEvent;
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

import static com.tmoreno.mooc.backoffice.utils.ResponseAssertions.assertNotFound;
import static com.tmoreno.mooc.backoffice.utils.ResponseAssertions.assertOk;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

public class CourseTeacherDeleteControllerIT extends BaseControllerIT {

    @SpyBean
    private CourseRepository courseRepository;

    @SpyBean
    private TeacherRepository teacherRepository;

    @BeforeEach
    public void setUp() {
        url += "/courses/%s/teachers/%s";
    }

    @Test
    public void given_a_course_with_teachers_when_send_delete_request_then_receive_ok_response_and_teacher_is_deleted_and_event_is_stored() {
        Teacher teacher = TeacherMother.random();
        Course course = CourseMother.randomInDraftStateWithTeacher(teacher);
        teacher.addCourse(course.getId());

        courseRepository.save(course);
        teacherRepository.save(teacher);

        url = String.format(url, course.getId().getValue(), teacher.getId().getValue());

        ResponseEntity<String> response = delete();

        assertOk(response);

        Course persistedCourse = courseRepository.find(course.getId()).orElseThrow();
        assertThat(persistedCourse.getTeachers().isEmpty(), is(true));

        Teacher persistedTeacher = teacherRepository.find(teacher.getId()).orElseThrow();
        assertThat(persistedTeacher.getCourses().isEmpty(), is(true));

        verify(domainEventRepository).store(any(CourseTeacherDeletedDomainEvent.class));
    }

    @Test
    public void given_not_existing_course_when_send_delete_request_then_receive_not_found_response() {

        url = String.format(url, CourseIdMother.random().getValue(), TeacherIdMother.random().getValue());

        ResponseEntity<String> response = delete();

        assertNotFound(response);
    }

    @Test
    public void given_course_when_send_delete_request_for_a_not_existing_teacher_then_receive_not_found_response() {
        Teacher teacher = TeacherMother.random();
        Course course = CourseMother.randomInDraftStateWithTeacher(teacher);
        teacher.addCourse(course.getId());

        courseRepository.save(course);
        teacherRepository.save(teacher);

        url = String.format(url, course.getId().getValue(), TeacherIdMother.random().getValue());

        ResponseEntity<String> response = delete();

        assertNotFound(response);
    }

    @Test
    public void given_course_without_teachers_when_send_delete_request_then_receive_not_found_response() {
        Course course = CourseMother.randomInDraftState();

        courseRepository.save(course);

        url = String.format(url, course.getId().getValue(), TeacherIdMother.random().getValue());

        ResponseEntity<String> response = delete();

        assertNotFound(response);
    }

    @Test
    public void should_rollback_all_database_changes_when_an_exception_happen() {
        doThrow(RuntimeException.class).when(domainEventRepository).store(any());

        Teacher teacher = TeacherMother.random();
        Course course = CourseMother.randomInDraftStateWithTeacher(teacher);
        teacher.addCourse(course.getId());

        courseRepository.save(course);
        teacherRepository.save(teacher);

        url = String.format(url, course.getId().getValue(), teacher.getId().getValue());

        delete();

        Course persistedCourse = courseRepository.find(course.getId()).orElseThrow();
        assertThat(persistedCourse.getTeachers().isEmpty(), is(false));

        Teacher persistedTeacher = teacherRepository.find(teacher.getId()).orElseThrow();
        assertThat(persistedTeacher.getCourses().isEmpty(), is(false));
    }
}
