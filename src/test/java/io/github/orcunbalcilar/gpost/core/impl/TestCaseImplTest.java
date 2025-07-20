package io.github.orcunbalcilar.gpost.core.impl;

import io.github.orcunbalcilar.gpost.TestItemStatus;
import io.github.orcunbalcilar.gpost.core.TestStep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for TestCaseImpl.
 * Tests the test case management functionality including test step execution and status management.
 */
class TestCaseImplTest {

    private TestCaseImpl testCase;
    
    @Mock
    private TestStep mockStep1;
    
    @Mock
    private TestStep mockStep2;
    
    @Mock
    private TestStep mockStep3;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testCase = new TestCaseImpl();
        
        // Setup mock test steps
        when(mockStep1.getName()).thenReturn("Step 1");
        when(mockStep1.getStatus()).thenReturn(TestItemStatus.PASSED);
        when(mockStep1.isDisabled()).thenReturn(false);
        
        when(mockStep2.getName()).thenReturn("Step 2");
        when(mockStep2.getStatus()).thenReturn(TestItemStatus.PASSED);
        when(mockStep2.isDisabled()).thenReturn(false);
        
        when(mockStep3.getName()).thenReturn("Step 3");
        when(mockStep3.getStatus()).thenReturn(TestItemStatus.PASSED);
        when(mockStep3.isDisabled()).thenReturn(false);
    }

    @Test
    void shouldSetAndGetName() {
        // Given
        String testName = "Test Case Name";

        // When
        testCase.setName(testName);

        // Then
        assertEquals(testName, testCase.getName());
    }

    @Test
    void shouldInitializeWithName() {
        // Given
        String testName = "Initialized Test Case";

        // When
        TestCaseImpl namedTestCase = new TestCaseImpl(testName);

        // Then
        assertEquals(testName, namedTestCase.getName());
    }

    @Test
    void shouldAddTestStep() {
        // When
        testCase.addTestStep(mockStep1);

        // Then
        assertEquals(1, testCase.getTestSteps().size());
        assertEquals(mockStep1, testCase.getTestSteps().get(0));
    }

    @Test
    void shouldAddMultipleTestSteps() {
        // When
        testCase.addTestStep(mockStep1);
        testCase.addTestStep(mockStep2);
        testCase.addTestStep(mockStep3);

        // Then
        assertEquals(3, testCase.getTestSteps().size());
        assertEquals(mockStep1, testCase.getTestSteps().get(0));
        assertEquals(mockStep2, testCase.getTestSteps().get(1));
        assertEquals(mockStep3, testCase.getTestSteps().get(2));
    }

    @Test
    void shouldGetStepByIndex() {
        // Given
        testCase.addTestStep(mockStep1);
        testCase.addTestStep(mockStep2);

        // When & Then
        assertEquals(mockStep1, testCase.getStep(0));
        assertEquals(mockStep2, testCase.getStep(1));
    }

    @Test
    void shouldThrowExceptionForInvalidStepIndex() {
        // Given
        testCase.addTestStep(mockStep1);

        // When & Then
        assertThrows(IndexOutOfBoundsException.class, () -> {
            testCase.getStep(5);
        });
    }

    @Test
    void shouldGoToSpecificStep() {
        // Given
        testCase.addTestStep(mockStep1);
        testCase.addTestStep(mockStep2);
        testCase.addTestStep(mockStep3);

        // When
        testCase.gotoStep(2);

        // Then
        // The gotoStep method should log the step name - we can't test logging directly
        // but we can test that it doesn't throw an exception
        assertDoesNotThrow(() -> testCase.gotoStep(2));
    }

    @Test
    void shouldFailTestCase() {
        // Given
        testCase.addTestStep(mockStep1);
        testCase.addTestStep(mockStep2);
        testCase.addTestStep(mockStep3);

        // When
        testCase.fail();

        // Then
        assertEquals(TestItemStatus.FAILED, testCase.getStatus());
        verify(mockStep1).setDisabled(true);
        verify(mockStep2).setDisabled(true);
        verify(mockStep3).setDisabled(true);
    }

    @Test
    void shouldSkipTestCase() {
        // Given
        testCase.addTestStep(mockStep1);
        testCase.addTestStep(mockStep2);
        testCase.addTestStep(mockStep3);

        // When
        testCase.skip();

        // Then
        assertEquals(TestItemStatus.SKIPPED, testCase.getStatus());
        verify(mockStep1).setDisabled(true);
        verify(mockStep2).setDisabled(true);
        verify(mockStep3).setDisabled(true);
    }

    @Test
    void shouldRunAllStepsAndPassWhenAllStepsPass() {
        // Given
        testCase.addTestStep(mockStep1);
        testCase.addTestStep(mockStep2);
        testCase.addTestStep(mockStep3);

        // When
        testCase.run();

        // Then
        verify(mockStep1).run();
        verify(mockStep2).run();
        verify(mockStep3).run();
        assertEquals(TestItemStatus.PASSED, testCase.getStatus());
    }

    @Test
    void shouldSkipDisabledSteps() {
        // Given
        when(mockStep2.isDisabled()).thenReturn(true);
        testCase.addTestStep(mockStep1);
        testCase.addTestStep(mockStep2);
        testCase.addTestStep(mockStep3);

        // When
        testCase.run();

        // Then
        verify(mockStep1).run();
        verify(mockStep2, never()).run(); // Should not run disabled step
        verify(mockStep3, never()).run(); // Should not continue after disabled step
    }

    @Test
    void shouldFailWhenOneStepFails() {
        // Given
        when(mockStep2.getStatus()).thenReturn(TestItemStatus.FAILED);
        testCase.addTestStep(mockStep1);
        testCase.addTestStep(mockStep2);
        testCase.addTestStep(mockStep3);

        // When
        testCase.run();

        // Then
        assertEquals(TestItemStatus.FAILED, testCase.getStatus());
    }

    @Test
    void shouldMaintainSkippedStatusAfterRun() {
        // Given
        testCase.addTestStep(mockStep1);
        testCase.addTestStep(mockStep2);
        
        // Make sure mock steps are not disabled initially and have initial status
        when(mockStep1.isDisabled()).thenReturn(false);
        when(mockStep2.isDisabled()).thenReturn(false);
        when(mockStep1.getStatus()).thenReturn(TestItemStatus.UNKNOWN);
        when(mockStep2.getStatus()).thenReturn(TestItemStatus.UNKNOWN);
        
        testCase.skip();

        // After skip, they should be disabled
        when(mockStep1.isDisabled()).thenReturn(true);
        when(mockStep2.isDisabled()).thenReturn(true);

        // When
        testCase.run();

        // Then
        assertEquals(TestItemStatus.SKIPPED, testCase.getStatus());
    }

    @Test
    void shouldHaveUnknownStatusInitially() {
        // When & Then
        assertEquals(TestItemStatus.UNKNOWN, testCase.getStatus());
    }

    @Test
    void shouldReturnEmptyTestStepsListInitially() {
        // When & Then
        assertTrue(testCase.getTestSteps().isEmpty());
    }

    @Test
    void shouldHandleRunWithNoSteps() {
        // When
        testCase.run();

        // Then
        assertEquals(TestItemStatus.PASSED, testCase.getStatus());
    }

    @Test
    void shouldHandleNullName() {
        // When
        testCase.setName(null);

        // Then
        assertNull(testCase.getName());
    }

    @Test
    void shouldHandleEmptyName() {
        // Given
        String emptyName = "";

        // When
        testCase.setName(emptyName);

        // Then
        assertEquals(emptyName, testCase.getName());
    }
}
