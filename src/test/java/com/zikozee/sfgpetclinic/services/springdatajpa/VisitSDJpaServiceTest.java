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

        when(visitRepository.findAll()).thenReturn(visits);

        Set<Visit> foundVisits = service.findAll();

        verify(visitRepository, atMostOnce()).findAll();

        assertEquals( 2, foundVisits.size());
        assertThat(foundVisits).hasSize(2);//AssertJ


    }

    @Test
    void findById() {
        when(visitRepository.findById(1L)).thenReturn(Optional.of(visit));
        Visit foundVisit = service.findById(1L);

        verify(visitRepository).findById(anyLong());

        assertThat(foundVisit).isEqualTo(visit);
    }

    @Test
    void save() {
        when(visitRepository.save(any(Visit.class))).thenReturn(visit);

        Visit savedVisit = service.save(new Visit());

        verify(visitRepository).save(any(Visit.class));

        assertThat(savedVisit).isNotNull();
    }

    @Test
    void delete() {
        service.delete(visit);

        verify(visitRepository).delete(any(Visit.class));
    }

    @Test
    void deleteById() {
        service.deleteById(anyLong());

        verify(visitRepository).deleteById(anyLong());

    }
}