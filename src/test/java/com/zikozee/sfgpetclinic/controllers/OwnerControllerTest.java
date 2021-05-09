package com.zikozee.sfgpetclinic.controllers;

import com.zikozee.sfgpetclinic.fauxspring.BindingResult;
import com.zikozee.sfgpetclinic.model.Owner;
import com.zikozee.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

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

    //TODO note it will be injected. ownerservice will be injected in controller:::: controller has something injected in it
//    PetTypeService petTypeService;
//
//    PetService petService;
//
//    @BeforeEach
//    void setUp() {
//        petTypeService = new PetTypeMapService();
//        petService = new PetMapService();
//
//        ownerService = new OwnerMapService(petTypeService, petService);
//        controller = new OwnerController(ownerService);
//    }

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
}