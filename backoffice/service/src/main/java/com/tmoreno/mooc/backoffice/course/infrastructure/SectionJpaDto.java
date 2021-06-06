package com.tmoreno.mooc.backoffice.course.infrastructure;

import com.tmoreno.mooc.backoffice.course.domain.Section;
import com.tmoreno.mooc.backoffice.course.domain.SectionId;
import com.tmoreno.mooc.backoffice.course.domain.SectionTitle;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.stream.Collectors;

@Entity(name = "course_sections")
public final class SectionJpaDto {

    @Id
    private String id;

    private String title;

    @OneToMany(mappedBy = "section", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SectionClassJpaDto> classes;

    @ManyToOne(fetch = FetchType.LAZY)
    private CourseJpaDto course;

    public static SectionJpaDto fromSection(CourseJpaDto course, Section section) {
        SectionJpaDto sectionJpaDto = new SectionJpaDto();

        List<SectionClassJpaDto> sectionClasses = section
                .getClasses()
                .stream()
                .map(sectionClass -> SectionClassJpaDto.fromSectionClass(sectionJpaDto, sectionClass))
                .collect(Collectors.toList());

        sectionJpaDto.setId(section.getId().getValue());
        sectionJpaDto.setTitle(section.getTitle().getValue());
        sectionJpaDto.setCourse(course);
        sectionJpaDto.setClasses(sectionClasses);

        return sectionJpaDto;
    }

    public static Section toSection(SectionJpaDto sectionJpaDto) {
        return new Section(
            new SectionId(sectionJpaDto.getId()),
            new SectionTitle(sectionJpaDto.getTitle()),
            sectionJpaDto.getClasses().stream().map(SectionClassJpaDto::toSectionClass).collect(Collectors.toList())
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

    public List<SectionClassJpaDto> getClasses() {
        return classes;
    }

    public void setClasses(List<SectionClassJpaDto> classes) {
        this.classes = classes;
    }

    public CourseJpaDto getCourse() {
        return course;
    }

    public void setCourse(CourseJpaDto course) {
        this.course = course;
    }
}
