package com.zikozee.sfgpetclinic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

/**
 * @author : zikoz
 * @created : 04 May, 2021
 */
//todo initiating mocks 2
class AnnotationMocksTest {

    @Mock
    Map<String, Object> mapMocks;


    @BeforeEach
    void setUp() {
        System.out.println("Initializing");

        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testMock() {
        mapMocks.put("key", "foo");
    }
}
