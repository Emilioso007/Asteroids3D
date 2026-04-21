package io.asteroidsjaylib.lifetime;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.lifetime.LifetimeComponent;
import io.asteroidsjaylib.common.util.ITimeProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LifetimeSystemTest {

    private LifetimeSystem lifetimeSystem;

    @Mock
    private IWorld mockWorld;

    @Mock
    private BaseEntity mockEntity;

    @Mock
    private ITimeProvider mockTimeProvider;

    private LifetimeComponent lifetimeComponent;

    @BeforeEach
    void setUp() {
        lifetimeSystem = new LifetimeSystem();

        // Manually inject our mock time provider (simulating Spring's @Autowired)
        lifetimeSystem.timeProvider = mockTimeProvider;

        // Simulate an entity that was spawned at game time = 10.0 seconds
        // It has a maximum lifetime of 5.0 seconds
        lifetimeComponent = new LifetimeComponent(10.0f, 5.0f);
    }

    @Test
    void testProcessEntity_NotRunOut_DontRemove() {
        // Arrange
        when(mockEntity.getComponent(LifetimeComponent.class)).thenReturn(lifetimeComponent);

        // Simulate the current time being 14.0 seconds.
        // 14.0 - 10.0 = 4.0 seconds alive. This is less than the 5.0 limit.
        when(mockTimeProvider.getTime()).thenReturn(14.0f);

        // Act
        lifetimeSystem.processEntity(mockWorld, mockEntity, 0.016f);

        // Assert
        verify(mockEntity).getComponent(LifetimeComponent.class);
        verifyNoMoreInteractions(mockEntity); // Ensure setToBeRemoved(true) was NOT called
    }

    @Test
    void testProcessEntity_HasRunOut_RemovesEntity() {
        // Arrange
        when(mockEntity.getComponent(LifetimeComponent.class)).thenReturn(lifetimeComponent);

        // Simulate the current time being 16.0 seconds.
        // 16.0 - 10.0 = 6.0 seconds alive. This is greater than the 5.0 limit.
        when(mockTimeProvider.getTime()).thenReturn(16.0f);

        // Act
        lifetimeSystem.processEntity(mockWorld, mockEntity, 0.016f);

        // Assert
        verify(mockEntity).setToBeRemoved(true); // Ensure it WAS flagged for removal
    }
}