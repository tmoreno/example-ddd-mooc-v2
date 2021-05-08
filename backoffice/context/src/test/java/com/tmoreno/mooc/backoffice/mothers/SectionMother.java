package com.tmoreno.mooc.backoffice.mothers;

import com.tmoreno.mooc.backoffice.course.domain.Section;
import com.tmoreno.mooc.backoffice.course.domain.SectionClass;
import com.tmoreno.mooc.backoffice.course.domain.SectionClassId;
import com.tmoreno.mooc.backoffice.course.domain.SectionClassTitle;
import com.tmoreno.mooc.shared.domain.DurationInSeconds;

import java.util.ArrayList;
import java.util.List;

public final class SectionMother {

    public static Section random() {
        return new Section(SectionIdMother.random(), SectionTitleMother.random());
    }

    public static Section randomWithClass(SectionClassId id, SectionClassTitle title, DurationInSeconds duration) {
        List<SectionClass> classes = new ArrayList<>();
        classes.add(new SectionClass(id, title, duration));

        return new Section(
                SectionIdMother.random(),
                SectionTitleMother.random(),
                classes
        );
    }
}
