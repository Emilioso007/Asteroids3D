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
        lifetimeSystem.timeProvider = mockTimeProvider;

        // Simulate an entity spawned at 10.0s with a 5.0s lifetime
        lifetime = new Lifetime(10.0f, 5.0f);
    }

    @Test
    void givenNotRunOut_WhenUpdate_ThenDontRemove() {
        when(mockEntity.get(Lifetime.class)).thenReturn(lifetime);
        when(mockTimeProvider.getTime()).thenReturn(14.0f); // 4.0s alive

        lifetimeSystem.update(mockWorld, mockEntity, 0.016f);

        verify(mockEntity).get(Lifetime.class);
        verifyNoMoreInteractions(mockEntity); // Ensure removed() was NOT called
    }

    @Test
    void givenHasRunOut_WhenUpdate_ThenRemovesEntity() {
        when(mockEntity.get(Lifetime.class)).thenReturn(lifetime);
        when(mockTimeProvider.getTime()).thenReturn(16.0f); // 6.0s alive

        lifetimeSystem.update(mockWorld, mockEntity, 0.016f);

        verify(mockEntity).removed(true); // Ensure it WAS flagged for removal
    }
}