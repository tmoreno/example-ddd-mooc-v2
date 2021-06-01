package com.tmoreno.mooc.backoffice.course.infrastructure;

import com.tmoreno.mooc.backoffice.course.domain.Section;
import com.tmoreno.mooc.backoffice.course.domain.SectionId;
import com.tmoreno.mooc.backoffice.course.domain.SectionTitle;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.stream.Collectors;

@Entity(name = "course_sections")
public final class SectionJpaDto {

    @Id
    private String id;

    private String title;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private List<SectionClassJpaDto> classes;

    public static SectionJpaDto fromSection(Section section) {
        SectionJpaDto sectionJpaDto = new SectionJpaDto();

        sectionJpaDto.setId(section.getId().getValue());
        sectionJpaDto.setTitle(section.getTitle().getValue());
        sectionJpaDto.setClasses(section.getClasses().stream().map(SectionClassJpaDto::fromSectionClass).collect(Collectors.toList()));

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
}
