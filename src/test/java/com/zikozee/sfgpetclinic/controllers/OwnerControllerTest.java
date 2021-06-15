package com.zikozee.sfgpetclinic.controllers;

import com.zikozee.sfgpetclinic.fauxspring.BindingResult;
import com.zikozee.sfgpetclinic.fauxspring.Model;
import com.zikozee.sfgpetclinic.model.Owner;
import com.zikozee.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    public static final String OWNERS_CREATE_OR_UPDATE_OWNER_FORM = "owners/createOrUpdateOwnerForm";
    public static final String REDIRECT_OWNERS_5 = "redirect:/owners/5";

    @InjectMocks
    OwnerController controller;

    @Mock
    OwnerService ownerService;

    @Mock
    BindingResult bindingResult;


    @Test
    void processCreationFormHasErrors() {
        //given
        Owner owner = new Owner(1L, "Samson", "Eromosei");
        given(bindingResult.hasErrors()).willReturn(true);

        //when
        String viewName = controller.processCreationForm(owner, bindingResult);

        //then
        assertThat(viewName).isEqualToIgnoringCase(OWNERS_CREATE_OR_UPDATE_OWNER_FORM);


    }

    @Test
    void processCreationFormNoErrors() {
        //given
        Owner owner = new Owner(5L, "Samson", "Eromosei");
        given(bindingResult.hasErrors()).willReturn(false);
        given(ownerService.save(any(Owner.class))).willReturn(owner);

        //when
        String viewName = controller.processCreationForm(owner, bindingResult);

        //then
        assertThat(viewName).isEqualToIgnoringCase(REDIRECT_OWNERS_5);
    }

    @DisplayName("Testing argument capture")
    @Nested
    class ArgumentCaptureTest{

        @Mock
        Model model;

        @Captor
        ArgumentCaptor<String> stringArgumentCaptor;

        @BeforeEach
        void setUp() {
            given(ownerService.findAllByLastNameLike(stringArgumentCaptor.capture()))
                    .willAnswer(invocation -> {
                        List<Owner> owners = new ArrayList<>();
                        String name = invocation.getArgument(0);

                        switch (name) {
                            case "%Buck%":
                                owners.add(new Owner(1L, "Joe", "Buck"));
                                return owners;
                            case "%DontFindMe%":
                                return owners;
                            case "%FindMe%":
                                owners.add(new Owner(1L, "Joe", "Buck"));
                                owners.add(new Owner(2L, "Joe2", "Buck2"));
                                return owners;
                        }

                        throw new RuntimeException("Invalid Argument");
                    });
        }

      //todo info capturing and testing arguments without annotation passed to a mock o ensure the value passed in is correct
//    @Test
//    void processFindFormWildcardString() {
//        //given
//        Owner owner = new Owner(1L, "Joe", "Buck");
//        List<Owner> ownerList = new ArrayList<>();
//        final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
//
//        given(ownerService.findAllByLastNameLike(captor.capture())).willReturn(ownerList);
//
//        //when
//        String viewName = controller.processFindForm(owner, bindingResult, null);
//
//        //then
//        assertThat("%Buck%").isEqualToIgnoringCase(captor.getValue());
//    }

        //todo info capturing and testing arguments passed to a mock + ANNOTATION_BASED
        @Test
        void processFindFormWildcardStringAnnotation() {
            //given
            Owner owner = new Owner(1L, "Joe", "Buck");

            //when
            String viewName = controller.processFindForm(owner, bindingResult, null);

            //then
            assertThat("%Buck%").isEqualToIgnoringCase(stringArgumentCaptor.getValue());
            assertThat("redirect:/owners/1").isEqualToIgnoringCase(viewName);
            verifyNoInteractions(model);
        }

        @Test
        void processFindFormWildcardNotFound() {
            //given
            Owner owner = new Owner(1L, "Joe", "DontFindMe");

            //when
            String viewName = controller.processFindForm(owner, bindingResult, null);

            verifyNoMoreInteractions(ownerService);
            //then
            assertThat("%DontFindMe%").isEqualToIgnoringCase(stringArgumentCaptor.getValue());
            assertThat("owners/findOwners").isEqualToIgnoringCase(viewName);
            verifyNoInteractions(model);
        }

        //todo info:: ORDER of service invocations
        @Test
        void processFindFormWildcardFound() {
            //given
            Owner owner = new Owner(1L, "Joe", "FindMe");
            InOrder inOrder = Mockito.inOrder(ownerService, model);

            //when
            String viewName = controller.processFindForm(owner, bindingResult, model);

            //then
            assertThat("%FindMe%").isEqualToIgnoringCase(stringArgumentCaptor.getValue());
            assertThat("owners/ownersList").isEqualToIgnoringCase(viewName);

            // inorder assertions todo info :: asserting order of mock invocations, switch the assert below and see it fail
            inOrder.verify(ownerService).findAllByLastNameLike(anyString());
            inOrder.verify(model, times(1)).addAttribute(anyString(), anyList());
            verifyNoMoreInteractions(model);
        }
    }

}