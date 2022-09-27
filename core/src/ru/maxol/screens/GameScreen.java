package ru.maxol.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import ru.maxol.utils.MyAtlasAnim;
import ru.maxol.utils.MyInputProcessor;
import ru.maxol.utils.MyInputProcessorV2;
import ru.maxol.utils.PhysX;
import ru.maxol.unit.Player;

public class GameScreen implements Screen {
    Game game;
    SpriteBatch batch;
    MyAtlasAnim animRun, animStay, animUp, animJump, tmpA;
    MyInputProcessor inputProcessor;
    Player player;
    private Music music;
    private Sound runSound;
    Texture background;
    private PhysX physX;
    private Body body;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;

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

        initMap(def, fdef, shape);
        Gdx.graphics.setTitle("Sonic Loredan Edition");
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
            float x = rect.get(i).getRectangle().x + rect.get(i).getRectangle().width / 2;
            float y = rect.get(i).getRectangle().y + rect.get(i).getRectangle().height / 2;
            float w = rect.get(i).getRectangle().width / 2;
            float h = rect.get(i).getRectangle().height / 2;

            def.position.set(x, y);
            shape.setAsBox(w, h);

            physX.world.createBody(def).createFixture(fdef).setUserData("Block");
        }

        env = map.getLayers().get("Hero");
        RectangleMapObject hero = (RectangleMapObject) env.getObjects().get("Hero");
        float x = hero.getRectangle().x + hero.getRectangle().width / 3;
        float y = hero.getRectangle().y + hero.getRectangle().height / 3;
        float w = hero.getRectangle().width / 3;
        float h = hero.getRectangle().height / 3;
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(x, y);
        def.gravityScale = 4f;
        shape.setAsBox(w, h);

        fdef.shape = shape;
        fdef.density = 0.5f;
        fdef.friction = 1;
        fdef.restitution = 0;
        body = physX.world.createBody(def);
        body.createFixture(fdef);
        body.resetMassData();
        Gdx.app.log("Mass", String.valueOf(body.getMass()));
        Gdx.app.log("Angle", String.valueOf(body.getAngle()));
        Gdx.app.log("GravityScale", String.valueOf(body.getGravityScale()));
        Gdx.app.log("Inertia", String.valueOf(body.getInertia()));
    }

    private void initObjects() {
        batch = new SpriteBatch();
        background = new Texture("background2.jpg");
        animRun = new MyAtlasAnim("atlas/sonic.atlas", "sonic-run", 7);
        animStay = new MyAtlasAnim("atlas/sonic.atlas", "sonic-stay", 7);
        animUp = new MyAtlasAnim("atlas/sonic.atlas", "sonic-up", 7);
        animJump = new MyAtlasAnim("atlas/sonic-jump.atlas", "sonic-jump", 7);
        player = new Player();
//        inputProcessor = new MyInputProcessor(player);
        inputProcessor = new MyInputProcessorV2();
        Gdx.input.setInputProcessor(inputProcessor);

        physX = new PhysX();
        camera = new OrthographicCamera();

    }

    private void movePlayer() {
        float y = body.getPosition().y - 23.5f;
        float x = body.getPosition().x - 22;
        tmpA = animStay;
        tmpA.setTime(Gdx.graphics.getDeltaTime());
        player.updateMotion(body);
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            runSound.play();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            runSound.play();
        }
        if (player.isLeftMove()) {
            tmpA = animRun;
            if (tmpA.getFrame().isFlipX()) tmpA.getFrame().flip(true, false);
        } else if (player.isRightMove()) {
            tmpA = animRun;
            if (!tmpA.getFrame().isFlipX()) tmpA.getFrame().flip(true, false);
        } else if (player.isUpMove()) {
            tmpA = animUp;
        } else if (player.isJump()) {
            tmpA = animJump;
        } else {
            tmpA = animStay;
            runSound.stop();
        }
        batch.draw(tmpA.getFrame(), x, y);

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
    public void render(float delta) {
        ScreenUtils.clear(Color.CLEAR);

        camera.position.x = body.getPosition().x;
        camera.position.y = body.getPosition().y;
        camera.zoom = 0.5f;
        camera.update();
        mapRenderer.setView(camera);
        mapRenderer.render();


        batch.setProjectionMatrix(camera.combined);
        batch.begin();
//        batch.draw(background, 0, 0);
        movePlayer();
        camera.update();

        batch.end();

        physX.step();
//        physX.debugDraw(camera);
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
