package controls;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.entity.Control;
import com.almasb.fxgl.entity.Entity;
import javafx.geometry.Point2D;

public class EnemyControl extends Control {

    private int screenWidth, screenHeight;
    private Double angleAdjustRate = FXGLMath.random(0, 0.5);
    private double directionAngle = Math.toDegrees(FXGLMath.random(-1, 1) * FXGLMath.PI2);
    private int rotationSpeed = FXGLMath.random(-100, 100);
    private Vec2 velocity = new Vec2();
    private double moveSpeed = 100d;

    public EnemyControl() {
        screenHeight = FXGL.getAppHeight();
        screenWidth = FXGL.getAppWidth();
    }

    public void onUpdate(Entity entity, double v) {
        adjustAngle(v);
        move(entity, v);
        rotate(entity, v);
        checkScreenBounds(entity);
    }

    private void move(Entity entity, double v) {
        Vec2 directionVector = Vec2.fromAngle(directionAngle).mulLocal(moveSpeed);

        velocity.addLocal(directionVector).mulLocal((float)v);

        entity.translate(new Point2D(velocity.x, velocity.y));

    }

    private void checkScreenBounds(Entity entity) {
        if (entity.getX() < 0
                || entity.getY() < 0
                || entity.getRightX() >= screenWidth
                || entity.getBottomY() >= screenHeight) {

            Point2D newDirectionVector = new Point2D(screenWidth / 2, screenHeight / 2)
                    .subtract(entity.getCenter());

            double angle = Math.toDegrees(Math.atan(newDirectionVector.getY() / newDirectionVector.getX()));
            directionAngle = newDirectionVector.getX() > 0 ? angle : 180 + angle;
        }
    }

    private void adjustAngle(double v) {
        if (FXGLMath.randomBoolean(angleAdjustRate.floatValue())) {
            directionAngle += Math.toDegrees((FXGLMath.noise1D(v) - 0.5));
        }
    }

    private void rotate(Entity entity, double v) {
        entity.rotateBy(rotationSpeed * v);
    }
}
