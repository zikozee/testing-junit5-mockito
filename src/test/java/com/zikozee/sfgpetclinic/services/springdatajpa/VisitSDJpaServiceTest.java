package com.zikozee.sfgpetclinic.services.springdatajpa;

import com.zikozee.sfgpetclinic.model.Visit;
import com.zikozee.sfgpetclinic.repositories.VisitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {

    @Mock
    VisitRepository visitRepository;

    @InjectMocks
    VisitSDJpaService service;

    Visit visit;
    Visit visit2;

    @BeforeEach
    void setUp() {
        visit = new Visit();
        visit2 = new Visit();
    }

    @DisplayName("Test Find All")
    @Test
    void findAll() {

        Set<Visit> visits = new HashSet<>(Arrays.asList(visit, visit2));

        //given
        given(visitRepository.findAll()).willReturn(visits);

        //when
        Set<Visit> foundVisits = service.findAll();

        //then
        then(visitRepository).should(atMostOnce()).findAll();

        assertEquals( 2, foundVisits.size());
        assertThat(foundVisits).hasSize(2);//AssertJ


    }

    @Test
    void findById() {
        //given
        given(visitRepository.findById(anyLong())).willReturn(Optional.of(visit));

        //when
        Visit foundVisit = service.findById(1L);

        //then
        then(visitRepository).should().findById(anyLong());

        assertThat(foundVisit).isEqualTo(visit);
    }

    @Test
    void save() {
        //given
        given(visitRepository.save(any(Visit.class))).willReturn(visit);

        //when
        Visit savedVisit = service.save(new Visit());

        //then
        then(visitRepository).should().save(any(Visit.class));

        assertThat(savedVisit).isNotNull();
    }

    @Test
    void delete() {
        //given - none

        //when
        service.delete(visit);

        //then
        then(visitRepository).should(atLeastOnce()).delete(any(Visit.class));
    }

    @Test
    void deleteById() {
        //given - none

        //when
        service.deleteById(anyLong());

        //then
        then(visitRepository).should().deleteById(anyLong());

    }
}