package io.asteroidsjaylib.common.ecs;

import io.asteroidsjaylib.common.IWorld;

public interface IGameStateProvider {
    String getStateName();
    void onEnter(IWorld world);
}
