package factories;

import com.almasb.fxgl.entity.*;
import com.almasb.fxgl.entity.component.CollidableComponent;
import controls.EnemyControl;
import enums.EnemyType;
import enums.TowerType;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Sphere;

@SetEntityFactory
public class TowerFactory implements EntityFactory {

    @Spawns("Tower")
    public Entity newEnemy(SpawnData data) {
        return Entities.builder()
                .from(data)
                .type(EnemyType.BASIC)
                .viewFromNodeWithBBox(new Circle(10, 10, 10, Color.BLUE))
                .with(new CollidableComponent(true))
                .with(new EnemyControl())
                .build();
    }
}