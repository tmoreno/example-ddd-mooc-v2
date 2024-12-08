package com.tmoreno.mooc.backoffice.mothers;

import com.tmoreno.mooc.backoffice.course.domain.CourseState;
import org.apache.commons.lang3.RandomUtils;

public final class CourseStateMother {
    public static CourseState random() {
        return CourseState.values()[RandomUtils.nextInt(0, CourseState.values().length)];
    }
}
