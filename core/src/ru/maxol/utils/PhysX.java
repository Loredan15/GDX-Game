package ru.maxol.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

import java.util.Arrays;
import java.util.stream.Collectors;

public class PhysX {
    public final World world;
    private final Box2DDebugRenderer debugRenderer;

    /*В сколько раз уменьшаем физику от значений на карте*/
    public static final float PPM = 100;


    public PhysX() {
        /*Ускорение свободного падения*/
        world = new World(new Vector2(0, -9.81f), true); /*9,81 - Ускорение свободного падения*/
        debugRenderer = new Box2DDebugRenderer();
    }

    public void debugDraw(OrthographicCamera camera) {
        debugRenderer.render(world, camera.combined);
    }

    public void step() {
        world.step(1 / 60f, 3, 3);
    }

    public void destroyBody(Body body) {
        world.destroyBody(body);
    }

    public Array<Body> getBodies(String name) {
        Array<Body> temp = new Array<>();
        world.getBodies(temp);
        Arrays.stream(temp.toArray()).filter(p -> p.getUserData().equals("ring")).collect(Collectors.toList());
        return temp;
    }

    public Body addObject(RectangleMapObject object) {
        Rectangle rectangle = object.getRectangle();
        String bodyType = (String) object.getProperties().get("BodyType");
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        String name = "";
        Body body;

        if (object.getName() != null) {
            name = object.getName();
        }

        switch (bodyType) {
            case "Static" -> bodyDef.type = BodyDef.BodyType.StaticBody;
            case "Dynamic" -> bodyDef.type = BodyDef.BodyType.DynamicBody;
            default -> bodyDef.type = BodyDef.BodyType.StaticBody;
        }
        bodyDef.position.set(
                (rectangle.x + rectangle.width / 2) / PPM,
                (rectangle.y + rectangle.height / 2) / PPM);
        polygonShape.setAsBox(rectangle.width / 2 / PPM, rectangle.height / 2 / PPM);
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 1;
        fixtureDef.restitution = 0.25f;

        body = world.createBody(bodyDef);
        body.setUserData(name);
        body.createFixture(fixtureDef).setUserData(name);

        if (name.equals("Hero")) {
            polygonShape.setAsBox(
                    rectangle.width / 3 / PPM,
                    rectangle.height / 10 / PPM,
                    new Vector2(0, -rectangle.width / 2),
                    0);
        }
        polygonShape.dispose();
        return body;
    }

    public void dispose() {
        world.dispose();
        debugRenderer.dispose();
    }
}
