package com.innovation.minflearn.course.application.service;

import com.innovation.minflearn.course.application.dto.request.AddSectionRequestDto;
import com.innovation.minflearn.course.domain.entity.Section;
import com.innovation.minflearn.course.domain.enums.SectionNumber;
import com.innovation.minflearn.course.domain.repository.SectionRepository;
import com.innovation.minflearn.exception.custom.section.DuplicateSectionException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SectionService {

    private final SectionRepository sectionRepository;

    @Transactional
    public void addSection(Long courseId, AddSectionRequestDto addSectionRequestDto) {

        SectionNumber sectionNumber = SectionNumber.checkSection(addSectionRequestDto.getSectionNumber());

        checkDuplicateSectionNumber(courseId, sectionNumber);

        sectionRepository.save(addSectionRequestDto.toEntity(courseId, sectionNumber));
    }

    private void checkDuplicateSectionNumber(Long courseId, SectionNumber sectionNumber) {
        boolean sectionExist = sectionRepository.isSectionExist(courseId, sectionNumber);
        if (sectionExist) {
            throw new DuplicateSectionException();
        }
    }
}
