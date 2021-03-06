package unchartedborders.world;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.core.math.Vec3;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import unchartedborders.UnchartedBorders;

public class WorldObject {
    private Vec3 position;
    private UnchartedBorders main;
    private Vec2 size;
    private Entity entity;
    private Node shape;
    private Vec2 mousePosition;

    public WorldObject(Vec3 position, Node shape, UnchartedBorders main){
        Bounds globalBounds = shape.localToScene(shape.getBoundsInLocal());
        this.main = main;
        this.position = position;
        this.size = new Vec2(globalBounds.getWidth(), globalBounds.getHeight());
        this.shape = shape;
        this.entity = FXGL.entityBuilder()
                .at(position.x, position.y)
                .view(shape)
                .buildAndAttach();
        shape.getParent().setOnScroll((ScrollEvent event) -> {
            double deltaY = event.getDeltaY();
            double zoomFactor = 1.05;
            if (deltaY < 0){
                zoomFactor = 0.95;
            }
            main.zoom(zoomFactor, mousePosition);
        });
        shape.getParent().setOnMouseMoved((MouseEvent event) -> {
            mousePosition = new Vec2(event.getX(), event.getY());
        });
    }

    public Vec3 getPosition() { return position; }
    public void setPosition(Vec3 position) { this.position = position; }

    public Vec2 getSize() {
        return size;
    }

    public void setSize(Vec2 size) {
        this.size = size;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public Node getShape() {
        return shape;
    }

    public void setShape(Node shape) {
        this.shape = shape;
    }

    public void destroy(){ entity.removeFromWorld(); }
}
