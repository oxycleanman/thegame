import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsWorld;
import com.almasb.fxgl.settings.GameSettings;
import enums.EnemyType;
import factories.TowerFactory;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseButton;

public class Main extends GameApplication {
    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(800);
        gameSettings.setHeight(600);
        gameSettings.setTitle("Basic Game");
    }

    @Override
    protected void initInput() {
        final Input input = getInput();

        input.addAction(new UserAction("Shoot") {
            @Override
            protected void onActionBegin() {
                getGameWorld().spawn("Tower", input.getMouseXWorld(), input.getMouseYWorld());
            }
        }, MouseButton.PRIMARY);
    }

    @Override
    protected void initGame() {
        getGameWorld().setEntityFactory(new TowerFactory());

        getGameWorld().spawn("Tower", FXGLMath.randomPoint2D().getX(), FXGLMath.randomPoint2D().getY());
    }

    @Override
    protected void initPhysics() {
        PhysicsWorld physics = getPhysicsWorld();

        CollisionHandler bounce = new CollisionHandler(EnemyType.BASIC, EnemyType.BASIC) {
            @Override
            protected void onCollision(Entity enemyA, Entity enemyB) {
                System.out.println(enemyA.getRotationComponent().angleProperty().doubleValue());

                Vec2 directionVectorB = Vec2.fromAngle(enemyB.getRotation()).mulLocal(10d);
                Vec2 velocityB = new Vec2();
                velocityB.addLocal(directionVectorB).mulLocal((float)10);
                enemyB.translate(new Point2D(velocityB.x, velocityB.y));
            }
        };

        physics.addCollisionHandler(bounce);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
