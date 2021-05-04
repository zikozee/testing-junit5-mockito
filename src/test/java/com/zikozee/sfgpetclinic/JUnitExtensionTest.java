package com.zikozee.sfgpetclinic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

/**
 * @author : zikoz
 * @created : 04 May, 2021
 */
//todo initiating mocks 3
@ExtendWith(MockitoExtension.class) // MockitoExtension this does initmocks internally
class JUnitExtensionTest {

    @Mock
    Map<String, Object> mapMocks;

    @Test
    void testMock() {
        mapMocks.put("key", "foo");
    }
}
