package com.zikozee.sfgpetclinic;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * @author : zikoz
 * @created : 04 May, 2021
 */
//todo initiating mocks 1
class InlineMockTest {

    @Test
    void testInlineMock(){
        Map mapMock = mock(Map.class);

        assertEquals(mapMock.size(), 0);
    }
}
