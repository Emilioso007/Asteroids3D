package io.asteroidsjaylib.collision;

import io.asteroidsjaylib.common.IWorld;
import io.asteroidsjaylib.common.collision.CircleColliderComponent;
import io.asteroidsjaylib.common.collision.CollisionEvent;
import io.asteroidsjaylib.common.ecs.BaseComponent;
import io.asteroidsjaylib.common.ecs.BaseEntity;
import io.asteroidsjaylib.common.ecs.BulkSystem;
import io.asteroidsjaylib.common.physics.PositionComponent;
import io.asteroidsjaylib.common.util.Vector2D;

import java.util.*;

public class CollisionSystem extends BulkSystem {

    @Override
    public void start(IWorld world) {
        this.setPriority(70);
    }

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(PositionComponent.class, CircleColliderComponent.class);
    }

    @Override
    public void update(IWorld world, List<BaseEntity> entities, float deltaTime) {

        AABB boundary = new AABB(0, 0, world.getWidth(), world.getHeight());
        QuadTree quadTree = new QuadTree(boundary);

        for (BaseEntity entity : entities){
            Vector2D pos = entity.getComponent(PositionComponent.class).orElseThrow().pos;
            float radius = entity.getComponent(CircleColliderComponent.class).orElseThrow().radius;
            quadTree.insert(new EntityBounds(entity, pos, radius));
        }

        List<EntityBounds> queryResults = new ArrayList<>();

        for (BaseEntity collider : entities){
            if (collider.isToBeRemoved()) continue;

            Vector2D colliderPos = collider.getComponent(PositionComponent.class).orElseThrow().pos;
            float colliderRadius = collider.getComponent(CircleColliderComponent.class).orElseThrow().radius;
            EntityBounds colliderBounds = new EntityBounds(collider, colliderPos, colliderRadius);

            queryResults.clear();
            quadTree.queryRange(colliderBounds, queryResults);

            for (EntityBounds targetBounds : queryResults){
                BaseEntity target = targetBounds.entity;

                if (collider == target) continue;
                if (target.isToBeRemoved()) continue;

                if (System.identityHashCode(collider) > System.identityHashCode(target)) continue;

                float distSq = Vector2D.distSq(colliderPos, targetBounds.pos);
                float radiiSq = (colliderRadius + targetBounds.radius) * (colliderRadius + targetBounds.radius);

                if (distSq <= radiiSq){
                    world.getEventBus().publish(world, new CollisionEvent(collider, target));
                }

            }

        }
    }
    class AABB {
        public final float minX, minY, maxX, maxY;

        public AABB(float minX, float minY, float maxX, float maxY){
            this.minX = minX;
            this.minY = minY;
            this.maxX = maxX;
            this.maxY = maxY;
        }

        public boolean intersects(AABB other){
            return (this.minX <= other.maxX && this.maxX >= other.minX &&
                    this.minY <= other.maxY && this.maxY >= other.minY);
        }
    }

    class EntityBounds extends AABB {
        public final BaseEntity entity;
        public final Vector2D pos;
        public final float radius;

        public EntityBounds(BaseEntity entity, Vector2D pos, float radius) {
            super(pos.x()-radius, pos.y()-radius, pos.x()+radius, pos.y()+radius);
            this.entity = entity;
            this.pos = pos;
            this.radius = radius;
        }
    }

    class QuadTree {
        private final int QT_NODE_CAPACITY = 4;
        private final AABB boundary;

        private final List<EntityBounds> objects;

        private QuadTree northWest;
        private QuadTree northEast;
        private QuadTree southWest;
        private QuadTree southEast;

        public QuadTree(AABB boundary){
            this.boundary = boundary;
            this.objects = new ArrayList<>(QT_NODE_CAPACITY);
        }

        public boolean insert(EntityBounds bounds){

            // Ignore object that do not belong in this quad tree
            if (!boundary.intersects(bounds)){
                return false; // object cannot be added
            }

            // If there is space in this quad tree and if it doesn't have subdivisions, add the object here
            if (objects.size() < QT_NODE_CAPACITY && northWest == null){
                objects.add(bounds);
                return true;
            }

            // Otherwise, subdivide and then add the point to whichever node will accept it
            if (northWest == null){
                subdivide();
            }

            boolean inserted = false;
            if (northWest.insert(bounds)) inserted = true;
            if (northEast.insert(bounds)) inserted = true;
            if (southWest.insert(bounds)) inserted = true;
            if (southEast.insert(bounds)) inserted = true;

            return inserted;
        }

        /// Create four children that fully divide this quad into four quads of equal area.
        private void subdivide(){
            float midX = boundary.minX + (boundary.maxX - boundary.minX) / 2f;
            float midY = boundary.minY + (boundary.maxY - boundary.minY) / 2f;

            northWest = new QuadTree(new AABB(boundary.minX, boundary.minY, midX, midY));
            northEast = new QuadTree(new AABB(midX, boundary.minY, boundary.maxX, midY));
            southWest = new QuadTree(new AABB(boundary.minX, midY, midX, boundary.maxY));
            southEast = new QuadTree(new AABB(midX, midY, boundary.maxX, boundary.maxY));
        }

        /// Find all points that appear within a range.
        /// @param range the range searching in.
        public void queryRange(AABB range, List<EntityBounds> results){

            // Automatically abort if the range does not intersect this quad
            if (!boundary.intersects(range)){
                return;
            }
            
            // Check objects at this quad level
            for(EntityBounds obj : objects){
                if (range.intersects(obj)){
                    results.add(obj);
                }
            }
            
            // Terminate here if there are no children
            if (northWest == null) return;
            
            // Otherwise, add the points from the children
            northWest.queryRange(range, results);
            northEast.queryRange(range, results);
            southWest.queryRange(range, results);
            southEast.queryRange(range, results);

        }
    }
}
