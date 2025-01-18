package com.tmoreno.mooc.backoffice.course.infrastructure.repository;

import com.tmoreno.mooc.backoffice.course.domain.SectionClass;
import com.tmoreno.mooc.backoffice.course.domain.SectionClassId;
import com.tmoreno.mooc.backoffice.course.domain.SectionClassTitle;
import com.tmoreno.mooc.shared.domain.DurationInSeconds;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity(name = "course_section_classes")
public final class SectionClassJpaDto {

    @Id
    private String id;

    private String title;
    private int durationInSeconds;

    @ManyToOne(fetch = FetchType.LAZY)
    private SectionJpaDto section;

    public static SectionClassJpaDto fromSectionClass(SectionJpaDto section, SectionClass sectionClass) {
        SectionClassJpaDto sectionClassJpaDto = new SectionClassJpaDto();

        sectionClassJpaDto.setId(sectionClass.getId().getValue());
        sectionClassJpaDto.setTitle(sectionClass.getTitle().getValue());
        sectionClassJpaDto.setDurationInSeconds(sectionClass.getDuration().value());
        sectionClassJpaDto.setSection(section);

        return sectionClassJpaDto;
    }

    public static SectionClass toSectionClass(SectionClassJpaDto sectionClassJpaDto) {
        return new SectionClass(
            new SectionClassId(sectionClassJpaDto.getId()),
            new SectionClassTitle(sectionClassJpaDto.getTitle()),
            new DurationInSeconds(sectionClassJpaDto.getDurationInSeconds())
        );
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDurationInSeconds() {
        return durationInSeconds;
    }

    public void setDurationInSeconds(int durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    public SectionJpaDto getSection() {
        return section;
    }

    public void setSection(SectionJpaDto section) {
        this.section = section;
    }
}
