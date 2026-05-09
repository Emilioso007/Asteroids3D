package io.asteroidsjaylib.lifetime;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.lifetime.Lifetime;
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

    private Lifetime lifetime;

    @BeforeEach
    void setUp() {
        lifetimeSystem = new LifetimeSystem();

        // Manually inject our mock time provider (simulating Spring's @Autowired)
        lifetimeSystem.timeProvider = mockTimeProvider;

        // Simulate an entity that was spawned at game time = 10.0 seconds
        // It has a maximum lifetime of 5.0 seconds
        lifetime = new Lifetime(10.0f, 5.0f);
    }

    @Test
    void givenNotRunOut_WhenUpdate_ThenDontRemove() {
        // Arrange
        when(mockEntity.get(Lifetime.class)).thenReturn(lifetime);

        // Simulate the current time being 14.0 seconds.
        // 14.0 - 10.0 = 4.0 seconds alive. This is less than the 5.0 limit.
        when(mockTimeProvider.getTime()).thenReturn(14.0f);

        // Act
        lifetimeSystem.update(mockWorld, mockEntity, 0.016f);

        // Assert
        verify(mockEntity).get(Lifetime.class);
        verifyNoMoreInteractions(mockEntity); // Ensure setToBeRemoved(true) was NOT called
    }

    @Test
    void givenHasRunOut_WhenUpdate_ThenRemovesEntity() {
        // Arrange
        when(mockEntity.get(Lifetime.class)).thenReturn(lifetime);

        // Simulate the current time being 16.0 seconds.
        // 16.0 - 10.0 = 6.0 seconds alive. This is greater than the 5.0 limit.
        when(mockTimeProvider.getTime()).thenReturn(16.0f);

        // Act
        lifetimeSystem.update(mockWorld, mockEntity, 0.016f);

        // Assert
        verify(mockEntity).removed(true); // Ensure it WAS flagged for removal
    }
}