package ru.maxol.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import ru.maxol.unit.PlayerV2;
import ru.maxol.utils.MyAtlasAnim;
import ru.maxol.utils.MyContactListener;
import ru.maxol.utils.MyInputProcessorV2;
import ru.maxol.utils.PhysX;

import java.util.ArrayList;
import java.util.List;

import static ru.maxol.utils.PhysX.PPM;

public class GameScreen implements Screen {
    Game game;
    SpriteBatch batch;
    MyAtlasAnim animRun, animStay, animUp, animJump, tmpA;
    MyInputProcessorV2 inputProcessor;
    PlayerV2 player;
    private Music music;
    private Sound runSound;
    Texture background;
    private PhysX physX;
    private Body body;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;
    public static List<Body> bodyToDelete;
    private Animation<TextureRegion> rings;
    int ringCount = 0;

    public GameScreen(Game game) {
        this.game = game;

        initObjects();
        configSound();
        Gdx.app.log("Game", "Start");
        map = new TmxMapLoader().load("map/d2.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        BodyDef def = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        TextureAtlas ringAtlas = new TextureAtlas("atlas/rings.atlas");
        rings = new Animation<>(1 / 7f, ringAtlas.findRegions("ring"));
        rings.setPlayMode(Animation.PlayMode.LOOP);

        initMap(def, fdef, shape);
        Gdx.graphics.setTitle("Sonic Loredan Edition");
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.CLEAR);

        camera.position.x = body.getPosition().x * PPM;
        camera.position.y = body.getPosition().y * PPM;
        camera.zoom = 0.5f;
        camera.update();
        mapRenderer.setView(camera);
        mapRenderer.render();

        player.setTime(delta);
        Vector2 vector = inputProcessor.getOut();
        if (MyContactListener.cnt < 1) {
            vector.set(vector.x, 0);
        }
        body.applyForceToCenter(vector, true);
        player.setFPS(body.getLinearVelocity(), true);

        Rectangle rect = player.getRectangle();
        ((PolygonShape) body.getFixtureList().get(0).getShape()).setAsBox(rect.width / 2, rect.height / 2);
        ((PolygonShape) body.getFixtureList().get(1).getShape()).setAsBox(
                rect.width / 3,
                rect.height / 10,
                new Vector2(0, -rect.height / 2), 0);


        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(player.getFrame(), rect.x, rect.y, rect.width * PPM, rect.height * PPM);

        Array<Body> ringsBody = physX.getBodies("ring");
        rings.setFrameDuration(Gdx.graphics.getDeltaTime());
        TextureRegion tr = rings.getKeyFrame(Gdx.graphics.getDeltaTime());
        float dScale = 3;
        for (Body bd: ringsBody) {
            float cx = bd.getPosition().x * PhysX.PPM - tr.getRegionWidth() / 2 / dScale;
            float cy = bd.getPosition().y * PhysX.PPM - tr.getRegionHeight() / 2 / dScale;
            float cW = tr.getRegionWidth() / PhysX.PPM / dScale;
            float cH = tr.getRegionHeight() / PhysX.PPM / dScale;
            ((PolygonShape)bd.getFixtureList().get(0).getShape()).setAsBox(cW/2, cH/2);
            batch.draw(tr, cx,cy, cW * PhysX.PPM, cH * PhysX.PPM);
        }

        batch.end();

        for (Body bd: bodyToDelete) {
            ringCount++;
            physX.destroyBody(bd);}
        bodyToDelete.clear();


        physX.step();
//        physX.debugDraw(camera); // Отобразить элементы карты
    }

    private void initMap(BodyDef def, FixtureDef fdef, PolygonShape shape) {
        def.type = BodyDef.BodyType.StaticBody;
        fdef.shape = shape;
        fdef.density = 1;
        fdef.friction = 0.2f;
        fdef.restitution = 0;
        MapLayer env = map.getLayers().get("env");
        Array<RectangleMapObject> rect = env.getObjects().getByType(RectangleMapObject.class);
        for (int i = 0; i < rect.size; i++) {
            physX.addObject(rect.get(i));
            physX.world.createBody(def).createFixture(fdef).setUserData("Block");
        }
        env = map.getLayers().get("rings");
        rect = env.getObjects().getByType(RectangleMapObject.class);
        for (int i = 0; i < rect.size; i++) {
            physX.addObject(rect.get(i));
            physX.world.createBody(def).createFixture(fdef).setUserData("ring");
        }




        RectangleMapObject hero = (RectangleMapObject) map.getLayers().get("Hero").getObjects().get("Hero");
        body = physX.addObject(hero);

        body.setFixedRotation(true); //запрет вращения кубика с героем
        player = new PlayerV2(body);
    }

    private void initObjects() {
        batch = new SpriteBatch();
        background = new Texture("background2.jpg");
        inputProcessor = new MyInputProcessorV2();
        Gdx.input.setInputProcessor(inputProcessor);
        physX = new PhysX();
        camera = new OrthographicCamera();
        bodyToDelete = new ArrayList<>();
    }

    private void configSound() {
        music = Gdx.audio.newMusic(Gdx.files.internal("Green Hill Zone MP3.mp3"));
        music.setVolume(0.05f);
        music.setLooping(true);
        music.play();
        runSound = Gdx.audio.newSound(Gdx.files.internal("19557.mp3"));
    }

    @Override
    public void dispose() {
        batch.dispose();
        animRun.dispose();
        animStay.dispose();
        animUp.dispose();
        animJump.dispose();
        music.dispose();
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {
        camera.viewportHeight = height;
        camera.viewportWidth = width;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }
}
